import os
import time
import json
from flask import Flask, render_template, request, redirect, url_for, send_from_directory, session
from flask_session import Session
from PIL import Image
from fastai.vision.all import *
import numpy as np
import uuid
import operator


# RUTA DONDE SE GUARDARÁN LAS IMÁGENES QUE SUBAN LOS USUARIOS
UPLOAD_FOLDER = 'images/'
ALLOWED_EXTENSION = ['jpg', 'jpeg']

app = Flask(__name__, static_url_path='/static')
app.secret_key = 'super secret key'
app.config['SESSION_TYPE'] = 'filesystem'

sess = Session()
sess.init_app(app)


app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024

# TODO: CAMBIAR PESO DEL MODELO CON EL NOMBRE 
learn = load_learner('/myapp/models/products-dataaug-resnet50-fepochs1-uepochs10')

def get_prediction(path, num=None):
    try:
        dl = learn.dls.test_dl([path], num_workers=0)
        inp, preds, _, dec_preds = get_preds(learn, dl=dl, with_input=True, with_decoded=True)
        datas = list(zip(learn.dls.vocab, dec_preds[0]))
        results_with_probs = sorted(datas, key=operator.itemgetter(1), reverse=True)[:num]
        results = [result[0] for result in results_with_probs]
        return results
    except Exception as e:
        sentry.captureException()


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSION


@app.route('/classes', methods=['GET'])
def classes():
    return json.dumps(list(learn.dls.vocab))


@app.route('/', methods=['GET'])
def predict():
    return render_template('predict.html')


@app.route('/uploads/<filename>')
def send_file(filename):
    return send_from_directory(UPLOAD_FOLDER, filename)


@app.route('/api/predict', methods=['POST'])
def api_predict():
    predicted, image_id = None, None
    # check if the post request has the file part
    if 'file' not in request.files:
        return redirect(request.url)
    image_id = str(uuid.uuid1())
    file = request.files['file']
    # if user does not select file, browser also
    # submit a empty part without filename
    if file.filename == '':
        return redirect(request.url)
    if file and allowed_file(file.filename):
        file_path = os.path.join(UPLOAD_FOLDER, image_id+'.jpg')
        file.save(file_path)
        predicted = get_prediction(file_path)
        # os.remove(file_path)
    return json.dumps({'result': predicted})


if __name__ == "__main__":
    app.run(host="0.0.0.0")

