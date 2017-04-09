#!/usr/bin/python
# -*- coding: utf-8 -*-
"""stress tests"""

import threading
import copy

TARGET = 'http://sp.int3.sonata-nfv.eu'

class StressTest(object):
    """stress test class"""

    def __init__(self, ntests=1, target=TARGET):
        self._ntests = ntests
        self._target = target
        self._threads = []
        self._entries = []

    def run(self):
        print 'Populating entries'
        self.populate()
        print 'Creating Threads'
        for i in range(0, self._ntests):
            t = threading.Thread(target=self.send)
            self._threads.append(t)
            t.start()
        [worker.join() for worker in self._threads]

if __name__ == '__main__':

    print 'Stress Test module'
