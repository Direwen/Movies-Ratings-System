package com.movieproject.interfaces;

import com.movieproject.models.MovieRatingRecord;

import java.util.ArrayList;

public interface Validator<T> {

    public boolean isRecordUnique(String[] record, String[] data);
    public boolean isUserRatingAllowed(ArrayList<T> records, String[] data);
}
