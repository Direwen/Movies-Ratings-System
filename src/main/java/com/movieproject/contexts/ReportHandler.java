package com.movieproject.contexts;

import com.movieproject.interfaces.ReportStrategy;

public class ReportHandler {

    private FileHandler fileHandler;

    public ReportHandler(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

    public boolean execute(ReportStrategy strategy)
    {
        return strategy.generateReport(this.fileHandler);
    }
}
