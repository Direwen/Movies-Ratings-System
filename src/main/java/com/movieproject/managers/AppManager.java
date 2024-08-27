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
            System.out.println("8. Count the number of movies rated by each user");
            System.out.println("9. List all movies grouped by their genre");
            System.out.println("10. List all movies rated by a specific user");
            System.out.println("11. Exit");
            System.out.println("==================================================");
            System.out.print("Enter your choice (1-11): ");
            userInput = this.scanner.nextLine();
            if (userInput.matches("[1-9]|10|11")) {
                validOption = true;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 11.");
            }
        }

        // Clear the console for a fresh look (for IDE consoles that support it)
        System.out.print("\033[H\033[2J");
        System.out.flush();

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
                this.service.searchRecordsByUserId();
                break;
            case "6":
                this.service.searchRecordsByMovieName();
                break;
            case "7":
                this.service.countUserRatings();
                break;
            case "8":
                this.service.countUsersRatings();
                break;
            case "9":
                this.service.listMoviesByGenres();
                break;
            case "10":
                this.service.listMoviesRatedByUser();
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

    public void run()
    {
        while (true)
        {
            String userChoice = this.displayMenu();
            this.processUserChoice(userChoice);
        }
    }
}
