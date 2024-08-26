package com.movieproject.interfaces;

import com.movieproject.models.MovieRatingRecord;

public interface Crud {
    void create(MovieRatingRecord var1);

    void read();

    void update(MovieRatingRecord var1);

    void delete(int var1);
}