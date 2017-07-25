#!/bin/bash

n=$1
c=$2
url=$3

#Stress MonMan API Level 
#-n requests     Number of requests to perform
#-c concurrency  Number of multiple requests to make at a time
pwd

echo "stress_metric 1.00" > data
ab -p data -n $n -c $c -g $4.log -q $url >>results.log
printf "%s" "$(<results.log)"

