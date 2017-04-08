#!/usr/bin/python
# -*- coding: utf-8 -*-
"""son-package catalogues stress tests"""

# Work in progress...

import requests
import uuid
import yaml
import time
import sys
from stress_test import StressTest

TARGET = 'http://sp.int3.sonata-nfv.eu'
SON_PACKAGE_SAMPLE = './qual-stress-catalogues/resources/sonata-demo.son'

class TestSonPackage(StressTest):
    """son-package class"""

    def __init__(self, ntests, target, sample=SON_PACKAGE_SAMPLE):
        super(TestSonPackage, self).__init__(ntests, target)
        with open(sample, 'rb') as fhandle:
            self._data = fhandle.read()

    def change(self):
        pass

    def send(self):
        url = '{0}:4002/catalogues/api/v2/son-packages'.format(self._target)
        headers = {
            'content-type': 'application/zip',
            'content-disposition':
            'attachment; filename={0}'.format(uuid.uuid4())
        }
        resp = requests.post(url, data=self._data, headers=headers)
        print resp.status_code

if __name__ == '__main__':

    if len(sys.argv) > 1:
        TARGET = sys.argv[1]
    sp = TestSonPackage(10, TARGET)
    sp.run()
