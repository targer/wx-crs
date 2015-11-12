package org.open.crs.cmd.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/11/11.
 */
@Component
public class AsyncTask {
    @Async
    public void doAsyncTask(){
        System.out.println("do some async task");
    }
}
