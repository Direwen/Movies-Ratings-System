package com.movieproject.operations;

import com.movieproject.interfaces.ReportStrategy;

public class ReportHandler {

    private FileHandler fileHandler;

    public ReportHandler(String filepath)
    {
        this.fileHandler = new FileHandler(filepath);
    }

    public void execute(ReportStrategy strategy)
    {
        strategy.generateReport(this.fileHandler);
    }
}
