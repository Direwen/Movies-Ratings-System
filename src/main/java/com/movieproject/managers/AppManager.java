package com.movieproject.managers;

import com.movieproject.facades.MovieRatingFacade;
import java.util.Scanner;

public class AppManager {
    private MovieRatingFacade service;
    private Scanner scanner;

    public AppManager(String filepath) {
        this.scanner = new Scanner(System.in);
        this.service = new MovieRatingFacade(filepath);
    }

    private String displayMenu() {
        String userInput = null;
        boolean validOption = false;

        while(!validOption) {
            System.out.println("Please select an option:");
            System.out.println("1. Insert new movie data");
            System.out.println("2. Update existing movie rating data");
            System.out.println("3. Delete existing movie record data");
            System.out.println("4. Read all movie records");
            System.out.println("5. Analyze popular movies");
            System.out.println("6. Count the number of times a specific rating was given by a user");
            System.out.println("7. List all movie records sorted by rating from highest to lowest");
            System.out.println("8. List movies based on their genre type");
            System.out.println("9. Calculate the total number of movies viewed by each user and sort by the most movies watched");
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

    private void processUserChoice(String choice) {
        switch (choice) {
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
                this.service.showAllRecords(); // Add this method to the facade if it doesn't exist
                break;
            case "5":
                this.service.countRatings();
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                System.out.println("Exiting the application.");
                System.exit(0);
                break;
        }

    }

    public void run() {
        while(true) {
            String userChoice = this.displayMenu();
            this.processUserChoice(userChoice);
        }
    }
}
