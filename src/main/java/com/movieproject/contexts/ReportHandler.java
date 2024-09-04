package com.movieproject.contexts;

import com.movieproject.interfaces.ReportStrategy;

public class ReportHandler {

    private FileOperationHandler fileOperationHandler;

    public ReportHandler(FileOperationHandler fileOperationHandler)
    {
        this.fileOperationHandler = fileOperationHandler;
    }

    public boolean execute(ReportStrategy strategy)
    {
        return strategy.generateReport(this.fileOperationHandler);
    }
}
