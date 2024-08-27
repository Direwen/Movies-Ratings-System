package com.movieproject.facades;

import com.movieproject.contexts.FileHandler;
import com.movieproject.contexts.ReportHandler;
import com.movieproject.managers.CrudManager;
import com.movieproject.managers.UserInteractionManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.*;

import java.util.Scanner;

public class MovieRatingFacade {
    private static MovieRatingFacade instance;
    private CrudManager crudManager;
    private UserInteractionManager userInteractionManager;
    private ReportHandler reportHandler;

    private MovieRatingFacade(FileHandler fileHandler, Scanner scanner)
    {
        this.crudManager = new CrudManager(fileHandler);
        this.reportHandler = new ReportHandler(fileHandler);
        this.userInteractionManager = new UserInteractionManager(scanner);
    }

    public static MovieRatingFacade getInstance(FileHandler fileHandler, Scanner scanner)
    {
        if (instance == null)
            instance = new MovieRatingFacade(fileHandler, scanner);
        return instance;
    }

    public void createRecord()
    {
        MovieRatingRecord newRecord = this.userInteractionManager.createRecord();
        this.crudManager.create(newRecord);
    }

    public void showAllRecords() {
        this.crudManager.read();
    }

    public void updateRecord() {
        MovieRatingRecord updatedRecord = this.userInteractionManager.updateRecord();
        this.crudManager.update(updatedRecord);
    }

    public void deleteRecord() {
        this.crudManager.delete(this.userInteractionManager.deleteRecord());
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
        boolean isFound = reportHandler.execute(new SearchRecordsOperation(this.userInteractionManager.getMovieName()));
        if (!isFound) System.out.println("No records found");
    }
}
