#!/usr/bin/env bash

gradle clean bootJar

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    build/libs/sweater-0.1.0.jar
    phpusr@192.168.1.100:/home/phpusr/

echo 'Restart server...'

ssh phpusr@192.168.1.100 << EOF

pgrep java | xargs kill -9
nohup java -jar sweater-0.1.0.jar > log.txt &

EOF

echo 'Bye'