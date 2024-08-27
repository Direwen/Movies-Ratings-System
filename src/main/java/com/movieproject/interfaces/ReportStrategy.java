package com.movieproject.interfaces;

import com.movieproject.contexts.FileHandler;

public interface ReportStrategy {
    boolean generateReport(FileHandler fileHandler);
}
