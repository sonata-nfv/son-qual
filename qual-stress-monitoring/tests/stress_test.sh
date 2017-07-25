#!/bin/bash

n=$1
c=$2
logf=$3.log
url=$4

#Stress MonMan API Level 
#-n requests     Number of requests to perform
#-c concurrency  Number of multiple requests to make at a time

<<<<<<< HEAD
ab -n $n -c $c -g $logf -q $url >>results.log
=======

ab -n $n -c $c -g $4.log -q $url >>results.log

>>>>>>> 92c5231d6209264d537357f3a143bc10170952d9
printf "%s" "$(<results.log)"
