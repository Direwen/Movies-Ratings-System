package com.movieproject.validators;

import com.movieproject.interfaces.Validator;
import com.movieproject.models.MovieRatingRecord;

import java.util.ArrayList;

public class RecordValidator implements Validator<MovieRatingRecord> {

    @Override
    public boolean isRecordUnique(String[] record, String[] userRecord)
    {
        return !record[0].equals(userRecord[0]);
    }

    @Override
    public boolean isUserRatingAllowed(ArrayList<MovieRatingRecord> records, String[] data)
    {
        MovieRatingRecord userRecord = MovieRatingRecord.convertToObj(data);
        for (var record : records) if (userRecord.userId == record.userId && userRecord.movieName.equalsIgnoreCase(record.movieName)) return false;
        return true;
    }
}
