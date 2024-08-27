package com.movieproject.managers;

import com.movieproject.interfaces.Createable;
import com.movieproject.interfaces.Deleteable;
import com.movieproject.interfaces.Readable;
import com.movieproject.interfaces.Updateable;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.FileAppendOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.contexts.FileHandler;
import com.movieproject.operations.FileReadOperation;
import com.movieproject.operations.FileUpdateOperation;
import java.io.File;
import java.util.Arrays;

public class CrudManager implements Createable<MovieRatingRecord>, Readable, Updateable<MovieRatingRecord>, Deleteable {
    private FileHandler fileHandler;

    public CrudManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void create(MovieRatingRecord newRecord)
    {
        if  (this.fileHandler.performOperation(new FileAppendOperation(new String[]{
                Integer.toString(newRecord.recordId),
                Integer.toString(newRecord.userId),
                newRecord.movieName,
                Float.toString(newRecord.rating),
                String.join("|", newRecord.genres)
        }))) {
            System.out.println("Created a record");
        } else {
            System.out.println("Failed to Create a record");
        }
    }

    public void read()
    {
        this.fileHandler.performOperation(new FileReadOperation((record) -> {
            System.out.println(Arrays.toString(record));
        }));
    }

    public void update(MovieRatingRecord recordToUpdate)
    {
        if (this.fileHandler.performOperation(new FileUpdateOperation(new File(this.fileHandler.getTempFilePath()), (record) -> {
            if (record[0].equals(Integer.toString(recordToUpdate.recordId))) {
                record[1] = Integer.toString(recordToUpdate.userId);
                record[2] = recordToUpdate.movieName;
                record[3] = Float.toString(recordToUpdate.rating);
                record[4] = String.join("|", recordToUpdate.genres);
            }
        }))) {
            System.out.println("Updated the record");
        } else {
            System.out.println("Failed to update the record");
        }
    }

    public void delete(int recordId)
    {
        if (this.fileHandler.performOperation(new FileDeleteOperation(recordId, this.fileHandler.getTempFilePath()))) {
            System.out.println("Deleted the record");
        } else {
            System.out.println("Failed to delete the record");
        }
    }
}
