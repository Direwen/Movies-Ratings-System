package com.movieproject;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.contexts.ReportHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.ListMovieRatingRecordsByUserOperation;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ListMovieRatingRecordsByUserOperationIT {

    private static ReportHandler reportHandler;
    private static TableDecorator tableDecorator;

    @BeforeAll
    static void init()
    {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        tableDecorator = TableDecorator.getInstance();
        FileOperationHandler fileOperationHandler = new FileOperationHandler("./data/Sample_Movie_Dataset.csv", recordValidator);
        reportHandler = new ReportHandler(fileOperationHandler);
    }

    @Test
    void generateReport()
    {
        reportHandler.execute(new ListMovieRatingRecordsByUserOperation(2, tableDecorator));
    }
}