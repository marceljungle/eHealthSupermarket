#!/bin/bash
  
NAME=fastai-api

echo "Eliminando contenedores antiguos"
ContainerId2=`docker ps -qa --filter "name=$NAME"`
if [ -n "$ContainerId2" ]
then
        echo "Stopping and removing existing $NAME container"
        docker stop $ContainerId2
        docker rm -v $ContainerId2
fi

# TODO: CAMBIAR RUTA DEL PROYECTO, RUTA DE LOS PESOS, DOMINIO Y CORREO.
docker run -d --name $NAME \
        -v "/home/api/api":/myapp \
        -v "/home/api/models":/myapp/models \
        -w /myapp \
    --expose=5000 \
    -e VIRTUAL_HOST="ehealtsupermarket.duckdns.org, www.ehealtsupermarket.duckdns.org" \
    -e VIRTUAL_PORT=5000 \
    -e "LETSENCRYPT_HOST=ehealtsupermarket.duckdns.org, www.ehealtsupermarket.duckdns.org" \
    -e "LETSENCRYPT_EMAIL=gigi.dan2011@gmail.com@gmail.com" \
    floydhub/pytorch:1.6.0-py3.56 \
    bash -c "pip install -r requirements.txt && python3 app.py"

echo "Aplicación desplegada"
