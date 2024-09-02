package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;

import java.util.*;

public class CountMovieRatingRecordsOperation implements ReportStrategy, ReportPrinter<HashMap<String, Integer>> {

    private final Integer userId;
    private final boolean isForAllUsers;
    private final HashMap<String, Integer> countsHashMap = new HashMap<>();

    public CountMovieRatingRecordsOperation()
    {
        this.userId = null;
        this.isForAllUsers = true;
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
        if (isForAllUsers) {
              List<Map.Entry<String, Integer>> list = new ArrayList<>(countsHashMap.entrySet());
              Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
              list.forEach(entry -> System.out.printf("The number of times User ID %s rated: %d%n", entry.getKey(), entry.getValue()));
        }
        else System.out.printf("The number of times User ID %s rated: %d%n", userId, countsHashMap.getOrDefault(userId.toString(), 0));
    }

    public CountMovieRatingRecordsOperation(int userId)
    {
        this.userId = userId;
        this.isForAllUsers = false;
    }
}

