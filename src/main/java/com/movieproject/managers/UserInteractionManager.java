package com.movieproject.managers;

import com.movieproject.models.MovieRatingRecord;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInteractionManager {
    private static final float RATING_MIN = 0.0F;
    private static final float RATING_MAX = 5.0F;
    private Scanner scanner;

    public UserInteractionManager() {
        this.scanner = new Scanner(System.in);
    }

    public MovieRatingRecord createRecord() {
        return new MovieRatingRecord(this.readInteger("Enter Record ID: "), this.readInteger("Enter User ID: "), this.readString("Enter Movie Name: "), this.readFloat("Enter Rating (0.0 - 5.0): ", 0.0F, 5.0F), this.readGenres("Enter Genres (separated by '|'): "));
    }

    public MovieRatingRecord updateRecord() {
        return new MovieRatingRecord(this.readInteger("Enter ID of the record to Update: "), this.readInteger("Enter new User ID: "), this.readString("Enter new Movie Name: "), this.readFloat("Enter new Rating (0.0 - 5.0): ", 0.0F, 5.0F), this.readGenres("Enter new Genres (separated by '|'): "));
    }

    public int deleteRecord() {
        return this.readInteger("Enter ID of the record to delete: ");
    }

    public int getUserId()
    {
        return this.readInteger("Enter User ID");
    }

    public String getMovieName()
    {
        return this.readString("Enter The Movie Name");
    }

    private int readInteger(String promptMsg) {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                return Integer.parseInt(this.scanner.nextLine());
            } catch (Exception var3) {
                System.out.println("Invalid input. Please Enter a valid number.");
            }
        }
    }

    private float readFloat(String promptMsg, float min, float max) {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                float value = Float.parseFloat(this.scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }

                throw new Exception();
            } catch (Exception var5) {
                System.out.println("Invalid input. Please Enter a valid number between " + min + " and " + max);
            }
        }
    }

    private String readString(String promptMsg) {
        while(true) {
            try {
                System.out.print(promptMsg + " >>> ");
                String value = this.scanner.nextLine().trim();
                if (value.isEmpty()) {
                    throw new Exception();
                }

                return value;
            } catch (Exception var3) {
                System.out.println("Invalid input. Please enter a valid text.");
            }
        }
    }

    private String readGenres(String promptMsg) {
        while(true) {
            try {
                String value = this.readString(promptMsg);
                if (!this.isGenresInputValid(value)) {
                    throw new Exception();
                }

                return this.formatGenres(value);
            } catch (Exception var3) {
                System.out.println("Invalid Format. Please enter Genres like this example: Comedy|Horror|Action");
            }
        }
    }

    private boolean isGenresInputValid(String input) {
        return input.matches("^[\\w\\s]+(\\|[\\w\\s]+)*$");
    }

    private String formatGenres(String input) {
        String[] genresArray = input.split("\\|");

        for(int i = 0; i < genresArray.length; ++i) {
            genresArray[i] = this.capitalizeFirstLetter(genresArray[i].trim());
        }

        return (String)(new HashSet(Arrays.asList(genresArray))).stream().collect(Collectors.joining("|"));
    }

    private String capitalizeFirstLetter(String genre) {
        if (genre != null && !genre.isEmpty()) {
            String var10000 = genre.substring(0, 1).toUpperCase();
            return var10000 + genre.substring(1).toLowerCase();
        } else {
            return genre;
        }
    }
}
