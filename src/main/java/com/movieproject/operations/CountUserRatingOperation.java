package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportStrategy;
import java.util.HashMap;

public class CountUserRatingOperation implements ReportStrategy {

    private int userId;
    private int count;
    private boolean isForAllUsers = true;
    private HashMap<String, Integer> countsHashMap;

    // Constructor for counting all users' ratings
    public CountUserRatingOperation() {
        this.countsHashMap = new HashMap<>();
    }

    // Constructor for counting ratings for a specific user
    public CountUserRatingOperation(int userId) {
        this.userId = userId;
        this.isForAllUsers = false;
    }

    @Override
    public void generateReport(FileHandler fileHandler) {
        if (isForAllUsers) {
            // Counting ratings for all users
            fileHandler.performOperation(new FileReadOperation((record) -> {
                String userId = record[1];
                countsHashMap.put(userId, countsHashMap.getOrDefault(userId, 0) + 1);
            }));
        } else {
            // Counting ratings for a specific user
            fileHandler.performOperation(new FileReadOperation((record) -> {
                if (record[1].equals(Integer.toString(this.userId))) {
                    count++;
                }
            }));
        }

        this.printResults();
    }

    private void printResults() {
        if (isForAllUsers) {
            System.out.println("Ratings count for all users:");
            for (HashMap.Entry<String, Integer> entry : countsHashMap.entrySet())
                System.out.println("User ID " + entry.getKey() + " rated " + entry.getValue() + " movies.");

        } else {
            System.out.println("The number of times User ID " + this.userId + " rated: " + this.count);
        }
    }
}
