package com.movieproject.managers;

import com.movieproject.interfaces.Createable;
import com.movieproject.interfaces.Deleteable;
import com.movieproject.interfaces.Readable;
import com.movieproject.interfaces.Updateable;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.FileAppendOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.operations.FileHandler;
import com.movieproject.operations.FileReadOperation;
import com.movieproject.operations.FileUpdateOperation;
import java.io.File;
import java.util.Arrays;

public class CsvManager implements Createable<MovieRatingRecord>, Readable, Updateable<MovieRatingRecord>, Deleteable {
    private FileHandler fileHandler;

    public CsvManager(String filePath) {
        this.fileHandler = new FileHandler(filePath);
    }

    public void create(MovieRatingRecord newRecord) {
        this.fileHandler.performOperation(new FileAppendOperation(new String[]{
                Integer.toString(newRecord.recordId),
                Integer.toString(newRecord.userId),
                newRecord.movieName,
                Float.toString(newRecord.rating),
                String.join("|", newRecord.genres)
        }));
    }

    public void read() {
        this.fileHandler.performOperation(new FileReadOperation((record) -> {
            System.out.println(Arrays.toString(record));
        }));
    }

    public void update(MovieRatingRecord recordToUpdate) {
        this.fileHandler.performOperation(new FileUpdateOperation(new File(this.fileHandler.getTempFilePath()), (record) -> {
            if (record[0].equals(Integer.toString(recordToUpdate.recordId))) {
                record[1] = Integer.toString(recordToUpdate.userId);
                record[2] = recordToUpdate.movieName;
                record[3] = Float.toString(recordToUpdate.rating);
                record[4] = String.join("|", recordToUpdate.genres);
            }
        }));
    }

    public void delete(int recordId) {
        this.fileHandler.performOperation(new FileDeleteOperation(recordId, this.fileHandler.getTempFilePath()));
    }
}
