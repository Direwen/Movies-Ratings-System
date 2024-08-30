package com.movieproject.models;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MovieRatingRecord implements Comparator<MovieRatingRecord> {
    public int recordId;
    public int userId;
    public String movieName;
    public float rating;
    public String[] genres;

    public MovieRatingRecord(int recordId, int userId, String movieName, float rating, String genres) {
        this.recordId = recordId;
        this.userId = userId;
        this.movieName = movieName;
        this.rating = rating;
        this.genres = this.sanitize(genres);
    }

    private String[] sanitize(String text) {
        return text.split("\\|");
    }

    public String toString() {
        int var10000 = this.recordId;
        return "com.movieproject.models.MovieRatingRecord{recordId=" + var10000 + ", userId=" + this.userId + ", movieName='" + this.movieName + "', rating=" + this.rating + ", genres=" + String.valueOf(this.genres) + "}";
    }

    public static MovieRatingRecord convertToObj(String[] record)
    {
        return new MovieRatingRecord(
                Integer.parseInt(record[0]),
                Integer.parseInt(record[1]),
                record[2],
                Float.parseFloat(record[3]),
                record[4]
        );
    }

    public static String[] convertToStringArray(MovieRatingRecord record) {
        return new String[] {
                Integer.toString(record.recordId),  // convert ID to String
                Integer.toString(record.userId),
                record.movieName,
                Float.toString(record.rating),
                String.join("|", record.genres)
        };
    }

    @Override
    public int compare(MovieRatingRecord o1, MovieRatingRecord o2)
    {
        return Float.compare(o1.rating, o2.rating);
    }

}
