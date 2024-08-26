package com.movieproject.models;

public class MovieRatingRecord {
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
}
