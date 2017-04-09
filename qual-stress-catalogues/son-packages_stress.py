#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""son-package catalogues stress tests"""

# Work in progress...

import requests
import uuid
import yaml
import time
import sys, os
from stress_test import StressTest

TARGET = 'http://sp.int3.sonata-nfv.eu'
SON_PACKAGE_SAMPLE = './qual-stress-catalogues/resources/sonata-demo.son'

class TestSonPackage(StressTest):
    """son-package class"""

    def __init__(self, ntests, target, sample=SON_PACKAGE_SAMPLE):
        super(TestSonPackage, self).__init__(ntests, target)
        self._target = target
        self._entries = []
        self._sample = sample
        with open(self._sample, 'rb') as fhandle:
            self._sample_entry = fhandle.read()

    def populate(self):
        for i in range(0,self._ntests):
            self._entries.append(self._sample_entry)

    def send(self):
        url = '{0}:4002/catalogues/api/v2/son-packages'.format(self._target)
        headers = {
            'content-type': 'application/zip',
            'content-disposition':
            'attachment; filename={0}'.format(uuid.uuid4())
        }
        try:
            resp = requests.post(url, data=self._entries.pop(), headers=headers, timeout=10)
            if not resp.status_code in (200, 201):
                print 'Error {0}'.format(resp.status_code)
                os._exit(1)
        except Exception as exc:
            print 'Error {0}'.format(exc)
            os._exit(1)


if __name__ == '__main__':

    if len(sys.argv) > 1:
        TARGET = sys.argv[1]
    print 'Son-packages stress test'
    limits = [10, 100, 1000]
    for limit in limits:
        test = TestSonPackage(limit, TARGET)
        test.run()
