import os
import time
import json
from flask import Flask, render_template, request, redirect, url_for, send_from_directory, session
from flask_session import Session
from PIL import Image
from fastai.vision.all import *
from torchvision import transforms
from torch.autograd import Variable
import numpy as np
import uuid
import operator
import torchvision
from werkzeug.datastructures import ImmutableMultiDict
import base64

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
learn = load_learner('models/prod-definitivo-resnet50-fepochs1-uepochs10.pth')

def get_prediction(path, num=5):
    try:
        img = Image.open(path)

        normalize = torchvision.transforms.Normalize(
            mean=[0.485, 0.456, 0.406],
            std=[0.229, 0.224, 0.225]
        )
        preprocess = torchvision.transforms.Compose([
            torchvision.transforms.Scale(256),
            torchvision.transforms.CenterCrop(224),
            torchvision.transforms.ToTensor(),
            normalize
        ])

        img_tensor = preprocess(img).unsqueeze_(0)
        img_variable = Variable(img_tensor)

        log_probs = learn.model(img_variable)
        datas = list(zip(learn.dls.vocab, log_probs.cpu().data.numpy()[0]))
        results_with_probs = sorted(datas, key=operator.itemgetter(1), reverse=True)[:num]
        results = [result[0] for result in results_with_probs]
        return results
    except Exception as e:
        print(str(e))


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
    print(str(predicted))
    return json.dumps({'result': predicted})

@app.route('/api/predict2', methods=['POST'])
def api_predict2():
    try:
        predicted = None
        data = dict(request.form)
        img=data['myImage']
        imgdata = base64.b64decode(img)
        filename = 'img.jpg'  
        with open(filename, 'wb') as f:
            f.write(imgdata)
        predicted = get_prediction("img.jpg")
        print(predicted[0])
        #predicted = get_prediction(request.files['myImage'].stream)
        return(predicted[0])
    except Exception as err:
        print("Error ocurred")
        print(err)
        return("Error, image not received")


if __name__ == "__main__":
    app.run(host="0.0.0.0", port="80")


