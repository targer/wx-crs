package org.open.crs.service.fswatch;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/11/12.
 */

public class EventInst {

    private static volatile EventInst eventInst = new EventInst();

    private EventBus eventBus;
    private AsyncEventBus asyncEventBus;

    private FsWatchService fs = new FsWatchService();

    private EventInst() {
        eventBus = new EventBus();

        asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(50));
        asyncEventBus.register(fs);
    }

    public static EventInst getInstance() {

        return eventInst;
    }

    /**
     * @return the eventBus
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * @return the eventBus
     */
    public AsyncEventBus getAsyncEventBus() {
        return asyncEventBus;
    }


}
