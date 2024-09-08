package com.movieproject;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.contexts.ReportHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.CountMovieRatingRecordsOperation;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CountMovieRatingRecordsOperationIT {

    private static ReportHandler reportHandler;
    private static TableDecorator tableDecorator;

    @BeforeAll
    static void init() {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        tableDecorator = TableDecorator.getInstance();
        FileOperationHandler fileOperationHandler = new FileOperationHandler("./data/Sample_Movie_Dataset.csv", recordValidator);
        reportHandler = new ReportHandler(fileOperationHandler);
    }

    @Test
    void countMovieRatingRecordsByUserId()
    {
        assertTrue(reportHandler.execute(new CountMovieRatingRecordsOperation(1, tableDecorator)), "Successfully Counted the Records for One User");
    }

    @Test
    void countMovieRatingRecordsForAllUsers()
    {
        assertTrue(reportHandler.execute(new CountMovieRatingRecordsOperation(tableDecorator)), "Successfully Counted the Records for All Users");
    }
}