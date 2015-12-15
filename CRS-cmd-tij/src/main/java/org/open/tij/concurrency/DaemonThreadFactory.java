package org.open.tij.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * Created by user2 on 2015/12/15.
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
}
