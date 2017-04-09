#!/bin/bash
###WORK IN PROGRESS
#DEPLOYMENT
# export DOCKER_HOST="tcp://sp.int3.sonata-nfv.eu:2375"
# target="http://sp.int3.sonata-nfv.eu"
target="localhost"

function wait_for_web() {
    until [ $(curl --connect-timeout 15 --max-time 15 -k -s -o /dev/null -w "%{http_code}" $1) -eq $2 ]; do
	sleep 2 && 	echo -n .;
	let retries="$retries+1"
	if [ $retries -eq 24 ]; then
	    echo "Timeout waiting for $2 status on $1"
	    exit 1
	fi
    done
    echo
}

containers=(son-catalogue-repos
    son-catalogue-repos-doc
    mongodata mongo
    mongoexpress)

for i in ${containers[@]}; do
    echo "Stopping $i"
    docker stop $i > /dev/null 2>&1
    echo "Removing $i"
    docker rm $i > /dev/null 2>&1
done

echo "Building"
docker-compose -f ./qual-stress-catalogues/docker-compose.yml build

echo "Starting containers"
docker-compose -f ./qual-stress-catalogues/docker-compose.yml up -d

echo "Waiting for $target:27017"
wait_for_web $target:27017/ 200
echo "Waiting for $target:4002"
wait_for_web $target:4002/ 200

echo

export DOCKER_HOST="unix:///var/run/docker.sock"
