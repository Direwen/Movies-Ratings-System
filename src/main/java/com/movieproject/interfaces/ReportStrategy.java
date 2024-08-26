package com.movieproject.interfaces;

import com.movieproject.operations.FileHandler;

import java.io.File;

public interface ReportStrategy {
    void generateReport(FileHandler fileHandler);
}
