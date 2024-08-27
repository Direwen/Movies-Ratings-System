package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;
import java.util.HashMap;

public class CountUserRatingOperation implements ReportStrategy, ReportPrinter<HashMap<String, Integer>> {

    private final Integer userId;
    private final boolean isForAllUsers;
    private final HashMap<String, Integer> countsHashMap = new HashMap<>();

    public CountUserRatingOperation()
    {
        this.userId = null;
        this.isForAllUsers = true;
    }

    public CountUserRatingOperation(int userId)
    {
        this.userId = userId;
        this.isForAllUsers = false;
    }

    @Override
    public boolean generateReport(FileHandler fileHandler)
    {
        boolean success = fileHandler.performOperation(new FileReadOperation((record) -> {
            if (isForAllUsers) countsHashMap.put(record[1], countsHashMap.getOrDefault(record[1], 0) + 1);
            else if (record[1].equals(userId.toString())) countsHashMap.put(record[1], countsHashMap.getOrDefault(record[1], 0) + 1);
        }));

        if (success) printReportResult(countsHashMap);
        return success;
    }

    @Override
    public void printReportResult(HashMap<String, Integer> countsHashMap)
    {
        if (isForAllUsers) countsHashMap.forEach((key, value) -> System.out.printf("User ID %s rated %d movies.%n", key, value));
        else System.out.printf("The number of times User ID %s rated: %d%n", userId, countsHashMap.getOrDefault(userId.toString(), 0));
    }
}

