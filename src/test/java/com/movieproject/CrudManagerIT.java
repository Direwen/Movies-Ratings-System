package com.movieproject;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.interfaces.Validator;
import com.movieproject.managers.CrudManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.validators.RecordValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrudManagerIT {

    private static CrudManager crudManager;

    @BeforeAll
    static void init() {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
        FileOperationHandler fileOperationHandler = new FileOperationHandler("./data/Test_Movie_Dataset.csv", recordValidator);
        crudManager = new CrudManager(fileOperationHandler);
    }

    @Test
    void createNewRecord() {
        boolean result = crudManager.create(new String[]{"1", "1", "Unknown Movie", "3.2", "Action|Drama"});
        assertTrue(result, "The record should be successfully created.");
    }

    @Test
    void readRecordsWhenEmpty() {
        boolean result = crudManager.read();
        assertTrue(result, "The operation should succeed even if there are no records to read.");
    }

    @Test
    void updateExistingRecord() {
        crudManager.create(new String[]{"1", "1", "Unknown Movie", "3.2", "Action|Drama"});
        boolean result = crudManager.update(new String[]{"1", "1", "Unknown Movie", "4.0", "Action|Drama"});
        assertTrue(result, "The record should be successfully updated.");
    }

    @Test
    void deleteNonExistentRecord() {
        boolean result = crudManager.delete(2);  // Trying to delete a non-existing record
        assertFalse(result, "The deletion should fail because the record does not exist.");
    }

    @Test
    void deleteExistingRecord() {
        crudManager.create(new String[]{"1", "1", "Unknown Movie", "3.2", "Action|Drama"});
        boolean result = crudManager.delete(1);  // Deleting the existing record
        assertTrue(result, "The record should be successfully deleted.");
    }

    @Test
    void updateNonExistentRecord() {
        boolean result = crudManager.update(new String[]{"2", "1", "Nonexistent Movie", "4.0", "Action|Drama"});
        assertFalse(result, "The update should fail because the record does not exist.");
    }

    @Test
    void createRecordWithDuplicateIdOrUserMovie() {
        // Attempt to create another record with the same ID
        boolean duplicateIdResult = crudManager.create(new String[]{"1", "2", "Another Movie", "4.5", "Comedy"});
        assertFalse(duplicateIdResult, "The record creation should fail due to duplicate ID.");

        // Attempt to create another record with the same user ID and movie name
        boolean duplicateUserMovieResult = crudManager.create(new String[]{"2", "1", "Unknown Movie", "4.0", "Action|Drama"});
        assertFalse(duplicateUserMovieResult, "The record creation should fail due to duplicate user ID and movie name combination.");
    }
}
