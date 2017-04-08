#!/usr/bin/python
# -*- coding: utf-8 -*-
"""nsds stress tests"""

# Work in progress...

import requests
import uuid
import yaml
import time
import sys
from stress_test import StressTest

TARGET = 'http://sp.int3.sonata-nfv.eu'
DESCRIPTOR_SAMPLE = 'qual-stress-catalogues/resources/nsd.yml'

class TestNsd(StressTest):
    """nsd class"""

    def __init__(self, ntests, target, sample=DESCRIPTOR_SAMPLE):
        super(TestNsd, self).__init__(ntests, target)
        self._target = target
        with open(sample, 'r') as stream:
            self._descriptor = yaml.load(stream)

    def change(self):
        self._descriptor['vendor'] = str(uuid.uuid4())
        self._descriptor['name'] = str(uuid.uuid4())
        self._descriptor['version'] = str(uuid.uuid4())

    def send(self):
        """Sends descriptor"""
        url = '{0}:4002/catalogues//api/v2/network-services'.format(self._target)
        headers = {'Content-Type': 'application/x-yaml'}
        r = requests.post(url, data=yaml.dump(self._descriptor), headers=headers)
        print r.status_code

if __name__ == '__main__':

    if len(sys.argv) > 1:
        TARGET = sys.argv[1]
    td = TestNsd(10, TARGET)
    td.run()
