#!/bin/bash

n=$1
c=$2
logf=$3.log
url=$4

#Stress MonMan API Level 
#-n requests     Number of requests to perform
#-c concurrency  Number of multiple requests to make at a time


ab -n $n -c $c -g $logf -q $url >>results.log

printf "%s" "$(<results.log)"
