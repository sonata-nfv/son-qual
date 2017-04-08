#!/bin/bash
# Contact Catalogues-DB
# target="http://sp.int3.sonata-nfv.eu"
target="localhost"

status_code=$(curl -s -o /dev/null -w "%{http_code}" $target:27017/)

if [[ $status_code != 20* ]] ;
  then
    echo "Error: Response error $status_code"
    exit -1
fi
echo "Success: Catalogues-DB found"

# Contact Catalogue and Repositories
status_code=$(curl -s -o /dev/null -w "%{http_code}" $target:4002/)

if [[ $status_code != 20* ]] ;
  then
    echo "Error: Response error $status_code"
    exit -1
fi
echo "Success: Catalogue and Repositories found"
