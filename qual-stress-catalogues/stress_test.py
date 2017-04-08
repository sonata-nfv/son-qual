#!/usr/bin/python
# -*- coding: utf-8 -*-
"""stress tests"""

TARGET = 'http://sp.int3.sonata-nfv.eu'

class StressTest(object):
    """stress test class"""

    def __init__(self, ntests=1, target=TARGET):
        self._ntests = ntests
        self._target = target

    def run(self):
        for i in range(0,self._ntests):
            self.change()
            self.send()

if __name__ == '__main__':

    print 'Stress Test module'
