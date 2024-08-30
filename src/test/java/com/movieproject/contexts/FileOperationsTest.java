package com.movieproject.contexts;

import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.FileAppendOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.operations.FileReadOperation;
import com.movieproject.operations.FileUpdateOperation;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationsTest {

    private static FileHandler fileHandler;

    @BeforeAll
    static void init()
    {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        fileHandler = new FileHandler("./data/Test_Movie_Dataset.csv", recordValidator);
    }

    @Test
    void testReadFile()
    {
        boolean success = fileHandler.performOperation(new FileReadOperation( (record) -> {
            System.out.println(Arrays.toString(record));
        }));

        assertTrue(success);
    }

    @Test
    void testAddValidRecord()
    {
        boolean success = fileHandler.performOperation(new FileAppendOperation(new String[]{
                "1",
                "1",
                "1",
                "2.3",
                "Action|Drama"
        }));
        assertTrue(success);

    }

    @Test
    void testDeleteAvailableRecord()
    {
        boolean success = fileHandler.performOperation(new FileDeleteOperation(1, fileHandler.getTempFilePath()));
        assertTrue(success);
    }

    @Test
    void testDeleteMissingRecord()
    {
        boolean success = fileHandler.performOperation(new FileDeleteOperation(0, fileHandler.getTempFilePath()));
        assertFalse(success);
    }

}