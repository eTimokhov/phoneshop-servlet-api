package com.es.phoneshop.model.dos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DosServiceImpl implements DosService {

    private static DosServiceImpl instance;

    private static final int MAX_REQUEST_COUNT = 20;
    private static final long TIME_BETWEEN_RESETS_MILLIS = 60 * 1000;
    private Map<String, AtomicInteger> ipCallCount;
    private volatile Date lastResetDate;

    public static DosService getInstance() {
        if (instance == null) {
            synchronized (DosServiceImpl.class) {
                if (instance == null) {
                    instance = new DosServiceImpl();
                }
            }
        }
        return instance;
    }

    private DosServiceImpl() {
        ipCallCount = new HashMap<>();
        lastResetDate = new Date();
    }

    @Override
    public boolean isAllowed(String ip) {
        tryToReset();
        AtomicInteger atomicIntegerCount = ipCallCount.get(ip);
        if (atomicIntegerCount == null) {
            atomicIntegerCount = new AtomicInteger(0);
            ipCallCount.put(ip, atomicIntegerCount);
        }
        int count = atomicIntegerCount.incrementAndGet();
        return count <= MAX_REQUEST_COUNT;
    }

    private void tryToReset() {
        if (System.currentTimeMillis() - lastResetDate.getTime() > TIME_BETWEEN_RESETS_MILLIS) {
            lastResetDate = new Date();
            ipCallCount.clear();
        }
    }

}
