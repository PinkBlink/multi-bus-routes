package org.multi.routes.action;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticManager {
    private static final Lock lock = new ReentrantLock();
    private static LogisticManager instance;

    private LogisticManager() {
    }

    public static LogisticManager getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new LogisticManager();
            }
            return instance;

        } finally {
            lock.unlock();
        }
    }
}
