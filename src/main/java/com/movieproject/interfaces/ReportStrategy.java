package com.movieproject.interfaces;

import com.movieproject.contexts.FileOperationHandler;

public interface ReportStrategy {
    boolean generateReport(FileOperationHandler fileOperationHandler);
}
