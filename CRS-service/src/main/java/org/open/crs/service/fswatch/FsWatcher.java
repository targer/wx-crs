package org.open.crs.service.fswatch;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Administrator on 2015/11/12.
 */
public class FsWatcher {
    private FutureTask<Integer> watchTask;
    private EventBus eventBus;
    private WatchService watchService;
    private volatile boolean keepWatching = true;
    private Path startPath;

    public FsWatcher(EventBus eventBus, Path startPath) {
        this.eventBus = Objects.requireNonNull(eventBus);
        this.startPath = Objects.requireNonNull(startPath);
    }

    public void start() throws IOException {
        initWatchService();// 1
        registerDirectories();// 2
        createWatchTask();// 3
        startWatching();// 4
    }

    public boolean isRunning() {
        return watchTask != null && !watchTask.isDone();
    }

    public void stop() {
        keepWatching = false;
    }

    public void close() {
        try {
            this.stop();
            this.watchService.close();
        } catch (IOException ex) {
            Logger.getLogger(FsWatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Used for testing purposes
    Integer getEventCount() {
        try {
            return watchTask.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

    // 3
    private void createWatchTask() {
        watchTask = new FutureTask<Integer>(new Callable<Integer>() {
            private int totalEventCount;

            @Override
            public Integer call() throws Exception {
                while (keepWatching) {
                    WatchKey watchKey = watchService.poll(10, TimeUnit.SECONDS);
                    if (watchKey != null) {
                        List<WatchEvent<?>> events = watchKey.pollEvents();
//                        Path watched = (Path) watchKey.watchable();
//                        PathEvents pathEvents = new PathEvents(watchKey.isValid(), watched);
//                        for (WatchEvent event : events) {
//                            pathEvents.add(new PathEvent((Path) event.context(), event.kind()));
//                            totalEventCount++;
//                        }
//                        watchKey.reset();

                        for (WatchEvent event : events) {
                            PathEvent pathEvent = new PathEvent((Path) event.context(), event.kind());
                            eventBus.post(pathEvent);
                        }
                        watchKey.reset();
                    }
                }
                return totalEventCount;
            }
        });
    }

    // 4
    private void startWatching() {
        new Thread(watchTask).start();
    }

    // 2
    private void registerDirectories() throws IOException {
        Files.walkFileTree(startPath, new WatchServiceRegisteringVisitor());
    }

    // 1
    private WatchService initWatchService() throws IOException {
        if (watchService == null) {
            watchService = FileSystems.getDefault().newWatchService();
        }
        return watchService;
    }

    private class WatchServiceRegisteringVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            //dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            return FileVisitResult.CONTINUE;
        }
    }
}