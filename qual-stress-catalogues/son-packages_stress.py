#!/usr/bin/python
# -*- coding: utf-8 -*-
"""son-package catalogues stress tests"""

# Work in progress...

import requests
import uuid
import yaml
import time
import sys

TARGET = 'http://sp.int3.sonata-nfv.eu'

def send_file(package_filename):
    """Sends package filename"""
    url = '{0}:4002/catalogues/api/v2/son-packages'.format(TARGET)
    with open(package_filename, 'rb') as fhandle:
        data = fhandle.read()
        headers = {
            'content-type': 'application/zip',
            'content-disposition':
            'attachment; filename={0}'.format(uuid.uuid4())
        }
        resp = requests.post(url, data=data, headers=headers)
    print resp.status_code

if __name__ == '__main__':

    if len(sys.argv) > 1:
        TARGET = sys.argv[1]
    try:
        send_file('./qual-stress-catalogues/resources/sonata-demo.son')
    except Exception as sende:
        print sende
