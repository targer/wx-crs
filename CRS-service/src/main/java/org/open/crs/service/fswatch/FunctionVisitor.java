package org.open.crs.service.fswatch;

import com.google.common.base.Function;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Administrator on 2015/11/12.
 */
public class FunctionVisitor extends SimpleFileVisitor<Path> {

    Function<Path, FileVisitResult> pathFunction;

    public FunctionVisitor(Function<Path, FileVisitResult> pathFunction) {
        this.pathFunction = pathFunction;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        return pathFunction.apply(file);
    }
}