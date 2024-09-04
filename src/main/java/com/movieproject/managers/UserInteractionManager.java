package com.movieproject.managers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * UserInteractionManager is responsible for interacting with the user.
 * It handles input collection and validation for creating, updating, and deleting records.
 */
public class UserInteractionManager {
    private static final float RATING_MIN = 0.0F;
    private static final float RATING_MAX = 5.0F;
    private Scanner scanner;

    /**
     * Constructor for UserInteractionManager.
     *
     * @param scanner the Scanner object used to read user inputs.
     */
    public UserInteractionManager(Scanner scanner)
    {
        this.scanner = scanner;
    }

    /**
     * Prompts the user to enter details for creating a new MovieRatingRecord.
     *
     * @return the created MovieRatingRecord object.
     */
    public String[] createRecord()
    {
        return new String[]{
                Integer.toString(this.readInteger("Enter Record ID: ")),
                Integer.toString(this.readInteger("Enter User ID: ")),
                this.readString("Enter Movie Name: "),
                Float.toString(this.readFloat("Enter Rating (0.0 - 5.0): ", 0.0F, 5.0F)),
                this.readGenres("Enter Genres (separated by '|'): ")
        };
    }

    /**
     * Prompts the user to enter details for updating an existing MovieRatingRecord.
     *
     * @return the updated MovieRatingRecord object.
     */
    public String[] updateRecord()
    {
        return new String[]{
                Integer.toString(this.readInteger("Enter ID of the record to Update: ")),
                "",
                "",
                Float.toString(this.readFloat("Enter Rating (0.0 - 5.0): ", 0.0F, 5.0F)),
                this.readGenres("Enter Genres (separated by '|'): ")
        };
    }

    /**
     * Prompts the user to enter the ID of the record they wish to delete.
     *
     * @return the ID of the record to delete.
     */
    public int deleteRecord()
    {
        return this.readInteger("Enter ID of the record to delete: ");
    }

    /**
     * Prompts the user to enter a User ID.
     *
     * @return the entered User ID.
     */
    public int getUserId()
    {
        return this.readInteger("Enter User ID");
    }

    /**
     * Prompts the user to enter a movie name.
     *
     * @return the entered movie name.
     */
    public String getMovieName()
    {
        return this.readString("Enter The Movie Name");
    }

    /**
     * Reads an integer from the user with validation.
     *
     * @param promptMsg the message to prompt the user.
     * @return the valid integer entered by the user.
     */
    protected int readInteger(String promptMsg)
    {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                int value = parseInteger(this.scanner.nextLine());
                if (value > 0) return value;
                else System.out.println("Invalid input. Please enter a positive number.");
            } catch (Exception err) {
                System.out.println("Invalid input. Please Enter a valid number.");
            }
        }
    }

    /**
     * Reads a float from the user with validation.
     *
     * @param promptMsg the message to prompt the user.
     * @param min the minimum valid value.
     * @param max the maximum valid value.
     * @return the valid float entered by the user.
     */
    protected float readFloat(String promptMsg, float min, float max)
    {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                float value = parseFloat(this.scanner.nextLine());
                if (value >= min && value <= max) return value;
                throw new Exception();
            } catch (Exception err) {
                System.out.println("Invalid input. Please Enter a valid number between " + min + " and " + max);
            }
        }
    }

    /**
     * Reads a non-empty string from the user with validation.
     *
     * @param promptMsg the message to prompt the user.
     * @return the valid string entered by the user.
     */
    protected String readString(String promptMsg)
    {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                String value = this.scanner.nextLine().trim();
                if (value.isEmpty()) throw new Exception();
                return value;
            } catch (Exception err) {
                System.out.println("Invalid input. Please enter a valid text.");
            }
        }
    }

    /**
     * Reads genres from the user and validates the format.
     *
     * @param promptMsg the message to prompt the user.
     * @return the valid genres string entered by the user.
     */
    protected String readGenres(String promptMsg)
    {
        while(true) {
            try {
                String value = this.readString(promptMsg);
                if (!this.isGenresInputValid(value)) throw new Exception();
                return this.formatGenres(value);
            } catch (Exception err) {
                System.out.println("Invalid Format. Please enter Genres like this example: Comedy|Horror|Action");
            }
        }
    }

    /**
     * Convert String to Integer
     *
     * @param input
     * @return
     */
    protected int parseInteger(String input)
    {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // Return an invalid number to signify failure
        }
    }

    /**
     * Convert String to Float
     * @param input
     * @return
     */
    protected float parseFloat(String input)
    {
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return -1; // Return NaN to signify failure
        }
    }

    /**
     * Validates the genres input string.
     *
     * @param input the input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    protected boolean isGenresInputValid(String input)
    {
        return input.matches("^[a-zA-Z\\s]+(\\|[a-zA-Z\\s]+)*$");
    }

    /**
     * Formats the genres string by capitalizing the first letter of each genre and removing duplicates.
     *
     * @param input the input string to format.
     * @return the formatted genres string.
     */
    protected String formatGenres(String input)
    {
        String[] genresArray = input.split("\\|");
        for(int i = 0; i < genresArray.length; ++i) genresArray[i] = this.capitalizeFirstLetter(genresArray[i].trim());
        return (String)(new HashSet(Arrays.asList(genresArray))).stream().collect(Collectors.joining("|"));
    }

    /**
     * Capitalizes the first letter of a genre string.
     *
     * @param genre the genre string to capitalize.
     * @return the genre string with the first letter capitalized.
     */
    private String capitalizeFirstLetter(String genre)
    {
        if (genre != null && !genre.isEmpty()) {
            String var10000 = genre.substring(0, 1).toUpperCase();
            return var10000 + genre.substring(1).toLowerCase();
        }
        return genre;
    }
}
