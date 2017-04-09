#!/usr/bin/python
# -*- coding: utf-8 -*-
"""stress tests"""

import threading
import time

TARGET = 'http://sp.int3.sonata-nfv.eu'

class StressTest(object):
    """stress test class"""

    def __init__(self, ntests=1, target=TARGET):
        print 'Initialising stress test with {0} entries'.format(ntests)
        self._ntests = ntests
        self._target = target
        self._threads = []
        self._entries = []

    def run(self):
        print 'Populating entries'
        self.populate()
        print 'Creating Threads'
        start_time = time.time()
        for i in range(0, self._ntests):
            t = threading.Thread(target=self.send)
            self._threads.append(t)
            t.start()
        [worker.join() for worker in self._threads]
        elapsed_time = time.time() - start_time
        print 'Test finished'
        print 'Elapsed time = {0} s'.format(elapsed_time)
        rate = self._ntests/float(elapsed_time)
        print 'Rate = {0} posts/s'.format(rate)
        print
        print

if __name__ == '__main__':

    print 'Stress Test module'
