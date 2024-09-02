package com.movieproject;

import com.movieproject.contexts.FileHandler;
import com.movieproject.contexts.ReportHandler;
import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.CountMovieRatingRecordsOperation;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CountMovieRatingRecordsOperationIT {

    private static ReportHandler reportHandler;

    @BeforeAll
    static void init() {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        FileHandler fileHandler = new FileHandler("./data/Sample_Movie_Dataset.csv", recordValidator);
        reportHandler = new ReportHandler(fileHandler);
    }

    @Test
    void countMovieRatingRecordsByUserId()
    {
        reportHandler.execute(new CountMovieRatingRecordsOperation(1));
    }

    @Test
    void countMovieRatingRecordsForAllUsers()
    {
        reportHandler.execute(new CountMovieRatingRecordsOperation());
    }
}