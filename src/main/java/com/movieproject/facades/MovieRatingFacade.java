package com.movieproject.facades;

import com.movieproject.contexts.FileHandler;
import com.movieproject.contexts.ReportHandler;
import com.movieproject.managers.CrudManager;
import com.movieproject.managers.UserInteractionManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.operations.*;

import java.util.Scanner;

/**
 * The MovieRatingFacade class provides a simplified interface to interact with
 * the underlying system of managing movie ratings, user interactions, and report generation.
 * It follows the Singleton design pattern to ensure only one instance of this class is created.
 */
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

    /**
     * Returns the singleton instance of the MovieRatingFacade.
     * If no instance exists, it creates one using the provided FileHandler and Scanner.
     *
     * @param fileHandler the FileHandler used to manage file operations.
     * @param scanner the Scanner used for user input.
     * @return the singleton instance of MovieRatingFacade.
     */
    public static MovieRatingFacade getInstance(FileHandler fileHandler, Scanner scanner)
    {
        if (instance == null)
            instance = new MovieRatingFacade(fileHandler, scanner);
        return instance;
    }

    /**
     * Creates a new movie rating record by interacting with the user and
     * then passes the created record to the CrudManager to store it.
     */
    public void createRecord()
    {
        this.crudManager.create(this.userInteractionManager.createRecord());
    }

    /**
     * Displays all movie rating records by delegating the task to the CrudManager.
     */
    public void showAllRecords() {
        this.crudManager.read();
    }

    /**
     * Updates an existing movie rating record by interacting with the user
     * and passing the updated record to the CrudManager.
     */
    public void updateRecord() {
        this.crudManager.update(this.userInteractionManager.updateRecord());
    }

    /**
     * Deletes a movie rating record by interacting with the user to get the necessary details
     * and then passing the record to be deleted to the CrudManager.
     */
    public void deleteRecord() {
        this.crudManager.delete(this.userInteractionManager.deleteRecord());
    }

    /**
     * Generates a report that counts how many ratings a specific user has given.
     * The user ID is obtained from the UserInteractionManager.
     */
    public void countMovieRatingRecordsByUser()
    {
        reportHandler.execute(new CountMovieRatingRecordsOperation(this.userInteractionManager.getUserId()));
    }

    /**
     * Generates a report that counts how many ratings each user has given.
     */
    public void countMovieRatingRecordsByAllUsers()
    {
        reportHandler.execute(new CountMovieRatingRecordsOperation());
    }

    /**
     * Generates a report that lists all movies grouped by their genre.
     */
    public void listMoviesByGenres()
    {
        reportHandler.execute(new ListMoviesByGenreOperation());
    }

    /**
     * Generates a report that lists all movies rated by a specific user, sorted by rating.
     * The user ID is obtained from the UserInteractionManager.
     */
    public void listMoviesRatingRecordsByUser()
    {
        reportHandler.execute(new ListMovieRatingRecordsByUser(this.userInteractionManager.getUserId()));
    }

    /**
     * Searches for movie rating records by a specific user ID.
     * The user ID is obtained from the UserInteractionManager.
     */
    public void searchRecordsByUserId()
    {
        reportHandler.execute(new SearchRecordsOperation(this.userInteractionManager.getUserId()));
    }

    /**
     * Searches for movie rating records by a specific movie name.
     * The movie name is obtained from the UserInteractionManager.
     * If no records are found, a message is displayed.
     */
    public void searchRecordsByMovieName()
    {
        boolean isFound = reportHandler.execute(new SearchRecordsOperation(this.userInteractionManager.getMovieName()));
        if (!isFound) System.out.println("No records found");
    }
}
