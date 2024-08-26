package com.movieproject.managers;

import com.movieproject.facades.MovieRatingFacade;
import java.util.Scanner;

public class AppManager {
    private MovieRatingFacade service;
    private Scanner scanner;

    public AppManager(String filepath)
    {
        this.scanner = new Scanner(System.in);
        this.service = new MovieRatingFacade(filepath);
    }

    private String displayMenu()
    {
        String userInput = null;
        boolean validOption = false;

        while (!validOption)
        {
            System.out.println("Please select an option:");
            System.out.println("1. Insert new movie data");
            System.out.println("2. Update existing movie rating data");
            System.out.println("3. Delete existing movie record data");
            System.out.println("4. Read all movie records");
            System.out.println("5. Count the number of times each rating was given");
            System.out.println("6. Count the number of times a specific user gave ratings");
            System.out.println("7. List all movies rated by a user, sorted by rating");
            System.out.println("8. List movies grouped by their genre");
            System.out.println("9. Calculate the total number of movies rated by each user and sort by the most movies rated");
            System.out.println("10. Exit");
            System.out.print("Enter your choice (1-10): ");
            userInput = this.scanner.nextLine();
            if (userInput.matches("[1-9]|10")) {
                validOption = true;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 10.");
            }
        }

        return userInput;
    }

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
                this.service.countRatings();
                break;
            case "6":
                this.service.countUserRatings();
                break;
            case "7":
                this.service.listMoviesRatedByUser();
                break;
            case "8":
                this.service.listMoviesByGenres();
                break;
            case "9":
                this.service.countUsersRatings();
                break;
            case "10":
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    public void run()
    {
        while (true)
        {
            String userChoice = this.displayMenu();
            this.processUserChoice(userChoice);
        }
    }
}
