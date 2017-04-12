#!/bin/bash

n=$1
c=$2
url=$3

#Stress MonMan API Level 
#-n requests     Number of requests to perform
#-c concurrency  Number of multiple requests to make at a time

ab -n $n -c $c -q $url >rresults.log
