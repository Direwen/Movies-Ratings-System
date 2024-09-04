package com.movieproject.managers;

import com.movieproject.interfaces.Createable;
import com.movieproject.interfaces.Deleteable;
import com.movieproject.interfaces.Readable;
import com.movieproject.interfaces.Updateable;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.FileAppendOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.operations.FileReadOperation;
import com.movieproject.operations.FileUpdateOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The CrudManager class is responsible for handling CRUD operations
 * (Create, Read, Update, Delete) for MovieRatingRecord objects.
 * It uses a FileHandler to perform file-based operations.
 */
public class CrudManager implements Createable<String[]>, Readable, Updateable<String[]>, Deleteable {
    private FileOperationHandler fileOperationHandler;

    /**
     * Constructor for CrudManager.
     *
     * @param fileOperationHandler the FileHandler used to manage file operations.
     */
    public CrudManager(FileOperationHandler fileOperationHandler)
    {
        this.fileOperationHandler = fileOperationHandler;
    }

    /**
     * Creates a new MovieRatingRecord by appending it to the file.
     *
     * @param newRecord the MovieRatingRecord to be created.
     * @return true if the record was successfully created, false otherwise.
     */
    @Override
    public boolean create(String[] newRecord)
    {
        ArrayList<MovieRatingRecord> list = new ArrayList<>();
        AtomicBoolean isRecordIdUnique = new AtomicBoolean(true);

        this.fileOperationHandler.performOperation(new FileReadOperation((record) -> {
            if (!this.fileOperationHandler.getValidator().isRecordUnique(record, newRecord)) {
                isRecordIdUnique.set(false);
            } else if (record[1].equals(newRecord[1])) {
                list.add(MovieRatingRecord.convertToObj(record));
            }
        }));

        if (isRecordIdUnique.get()) {
            if (this.fileOperationHandler.getValidator().isUserRatingAllowed(list, newRecord)) {
                boolean result = this.fileOperationHandler.performOperation(new FileAppendOperation(newRecord));
                if (result) {
                    System.out.println("Record successfully created.");
                    return true;
                } else System.out.println("Failed to create record.");
            } else System.out.println("User can't rate the same movie twice.");
        } else System.out.println("Record ID is not unique.");
        return false;
    }

    /**
     * Reads all records from the file and prints them to the console.
     * @return true if records were successfully read, false otherwise.
     */
    @Override
    public boolean read()
    {
        AtomicBoolean isReadSuccessful = new AtomicBoolean(false);
        boolean success = this.fileOperationHandler.performOperation(new FileReadOperation((record) -> {
            System.out.println(String.join(" , ", record));
            isReadSuccessful.set(true);
        }));
        if (success) {
            if (isReadSuccessful.get()) System.out.println("Records successfully read.");
            else System.out.println("No records found.");
            return true;
        } else return false;
    }

    /**
     * Updates an existing MovieRatingRecord in the file.
     *
     * @param recordToUpdate the MovieRatingRecord with updated information.
     * @return true if the record was successfully updated, false otherwise.
     */
    @Override
    public boolean update(String[] recordToUpdate)
    {
        boolean result = this.fileOperationHandler.performOperation(new FileUpdateOperation(this.fileOperationHandler.getTempFilePath(), (record) -> {
            if (record[0].equals(recordToUpdate[0]) && record[3].equals(recordToUpdate[3]) && record[4].equals(recordToUpdate[4])) {
                System.out.println("Update ignored - No changes detected or attempting to rate the same movie twice.");
            } else if (record[0].equals(recordToUpdate[0])) {
                record[3] = recordToUpdate[3];
                record[4] = recordToUpdate[4];
            }
        }));

        if (result) {
            System.out.println("Record successfully updated.");
            return true;
        }
        System.out.println("Failed to update record.");
        return false;
    }

    /**
     * Deletes a MovieRatingRecord from the file based on its record ID.
     *
     * @param recordId the ID of the record to be deleted.
     * @return true if the record was successfully deleted, false otherwise.
     */
    @Override
    public boolean delete(int recordId)
    {
        boolean result = this.fileOperationHandler.performOperation(new FileDeleteOperation(recordId, this.fileOperationHandler.getTempFilePath()));
        if (result) {
            System.out.println("Record successfully deleted.");
            return true;
        }
        System.out.println("Failed to delete record.");
        return false;
    }
}
