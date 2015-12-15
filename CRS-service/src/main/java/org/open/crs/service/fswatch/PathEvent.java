package org.open.crs.service.fswatch;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * Created by Administrator on 2015/11/12.
 */
public class PathEvent {
    private final Path eventTarget;
    private final WatchEvent.Kind type;

    PathEvent(Path eventTarget, WatchEvent.Kind type) {
        this.eventTarget = eventTarget;
        this.type = type;
    }

    public Path getEventTarget() {
        return eventTarget;
    }

    public WatchEvent.Kind getType() {
        return type;
    }
}
