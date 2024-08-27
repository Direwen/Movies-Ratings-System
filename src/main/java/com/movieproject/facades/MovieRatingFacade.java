package com.movieproject.facades;

import com.movieproject.contexts.ReportHandler;
import com.movieproject.managers.CsvManager;
import com.movieproject.managers.UserInteractionManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.*;

public class MovieRatingFacade {
    private CsvManager csvManager;
    private UserInteractionManager userInteractionManager;
    private ReportHandler reportHandler;

    public MovieRatingFacade(String filepath) {
        this.csvManager = new CsvManager(filepath);
        this.userInteractionManager = new UserInteractionManager();
        this.reportHandler = new ReportHandler(filepath);
    }

    public void createRecord() {
        MovieRatingRecord newRecord = this.userInteractionManager.createRecord();
        this.csvManager.create(newRecord);
    }

    public void showAllRecords() {
        this.csvManager.read();
    }

    public void updateRecord() {
        MovieRatingRecord updatedRecord = this.userInteractionManager.updateRecord();
        this.csvManager.update(updatedRecord);
    }

    public void deleteRecord() {
        this.csvManager.delete(this.userInteractionManager.deleteRecord());
    }

    public void countUserRatings()
    {
        reportHandler.execute(new CountUserRatingOperation(this.userInteractionManager.getUserId()));
    }

    public void countUsersRatings()
    {
        reportHandler.execute(new CountUserRatingOperation());
    }

    public void listMoviesByGenres()
    {
        reportHandler.execute(new ListMoviesByGenreOperation());
    }

    public void listMoviesRatedByUser()
    {
        reportHandler.execute(new ListMoviesByUserIdOperation(this.userInteractionManager.getUserId()));
    }

    public void searchRecordsByUserId()
    {
        reportHandler.execute(new SearchRecordsOperation(this.userInteractionManager.getUserId()));
    }

    public void searchRecordsByMovieName()
    {
        reportHandler.execute(new SearchRecordsOperation(this.userInteractionManager.getMovieName()));
    }
}
