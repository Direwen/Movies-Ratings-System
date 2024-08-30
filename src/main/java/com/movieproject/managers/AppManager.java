package com.movieproject.managers;

import com.movieproject.contexts.FileHandler;
import com.movieproject.facades.MovieRatingFacade;
import java.util.Scanner;

/**
 * The AppManager class is responsible for managing the flow of the application.
 * It interacts with the user, displays the menu, and processes user choices.
 * This class follows the Singleton design pattern to ensure a single instance.
 */
public class AppManager {
    private static AppManager instance;
    private MovieRatingFacade service;
    private Scanner scanner;

    private AppManager(FileHandler fileHandler)
    {
        this.scanner = new Scanner(System.in);
        this.service = MovieRatingFacade.getInstance(fileHandler, this.scanner);
    }

    /**
     * Returns the singleton instance of the AppManager.
     * If no instance exists, it creates one using the provided FileHandler.
     *
     * @param fileHandler the FileHandler used to manage file operations.
     * @return the singleton instance of AppManager.
     */
    public static AppManager getInstance(FileHandler fileHandler)
    {
        if (instance == null)
            instance = new AppManager(fileHandler);
        return instance;
    }

    /**
     * Displays the main menu of the application and prompts the user to enter their choice.
     * Ensures that the input is valid (between 1 and 11).
     *
     * @return the user's choice as a string.
     */
    private String displayMenu() {
        String userInput = null;
        boolean validOption = false;

        while (!validOption) {
            System.out.println("==================================================");
            System.out.println("               MOVIE RATING SYSTEM                ");
            System.out.println("==================================================");
            System.out.println("1. Insert new movie rating record");
            System.out.println("2. Update existing movie rating record");
            System.out.println("3. Delete an existing movie rating record");
            System.out.println("4. Display all movie records");
            System.out.println("5. Search for a movie record by User ID");
            System.out.println("6. Search for a movie record by Movie Name");
            System.out.println("7. Count the number of ratings by a specific user");
            System.out.println("8. Show All Sorted Movie Rating Records of a Specific User");
            System.out.println("9. List all movies grouped by their genre");
            System.out.println("10. List the total number of movies rated by each user sorted by movie counts");
            System.out.println("11. Exit");
            System.out.println("==================================================");
            System.out.print("Enter your choice (1-11): ");
            userInput = this.scanner.nextLine();
            if (userInput.matches("[1-9]|10|11")) {
                validOption = true;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
            }
        }

        // Clear the console for a fresh look (for IDE consoles that support it)
        System.out.print("\033[H\033[2J");
        System.out.flush();

        return userInput;
    }

    /**
     * Processes the user's choice by calling the appropriate method in the MovieRatingFacade.
     *
     * @param choice the user's choice as a string.
     */
    private void processUserChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                this.service.createRecord();
                break;
            case "2":
                this.service.updateRecord();
                break;
            case "3":
                this.service.deleteRecord();
                break;
            case "4":
                this.service.showAllRecords();
                break;
            case "5":
                this.service.searchRecordsByUserId();
                break;
            case "6":
                this.service.searchRecordsByMovieName();
                break;
            case "7":
                this.service.countMovieRatingRecordsByUser();
                break;
            case "8":
                this.service.listMoviesRatingRecordsByUser();
                break;
            case "9":
                this.service.listMoviesByGenres();
                break;
            case "10":
                this.service.countMovieRatingRecordsByAllUsers();
                break;
            case "11":
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    /**
     * Starts the application by continuously displaying the menu and processing user choices.
     */
    public void run()
    {
        while (true)
        {
            String userChoice = this.displayMenu();
            this.processUserChoice(userChoice);
        }
    }
}
