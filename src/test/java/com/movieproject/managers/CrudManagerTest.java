package com.movieproject.managers;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrudManagerTest {

    private static CrudManager crudManager;

    @BeforeAll
    static void init()
    {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        FileHandler fileHandler = new FileHandler("./data/Test_Movie_Dataset.csv", recordValidator);
        crudManager = new CrudManager(fileHandler);
    }

    @Test
    void create()
    {
        boolean result = crudManager.create(new String[]{"1", "1", "Unknown Movie", "3.2", "Action|Drama"});
        assertTrue(result, "The record should be successfully created.");
    }

    @Test
    void read()
    {
        boolean result = crudManager.read();
        assertTrue(result, "The records should be successfully read.");
    }

    @Test
    void update()
    {
        boolean result = crudManager.update(new String[]{"1", "1", "Unknown Movie", "4.0", "Action|Drama"});
        assertTrue(result, "The record should be successfully updated.");
    }

    @Test
    void delete()
    {
        boolean result = crudManager.delete(2);  // Trying to delete a non-existing record
        assertFalse(result, "The deletion should fail because the record does not exist.");
    }
}
