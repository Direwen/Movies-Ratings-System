package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;

public class SearchRecordsOperation implements ReportStrategy, ReportPrinter<String[]> {

    private Object value; // To accept String or Integer

    public SearchRecordsOperation(String value)
    {
        this.value = value;
    }

    public SearchRecordsOperation(int value)
    {
        this.value = value;
    }

    @Override
    public boolean generateReport(FileHandler fileHandler)
    {
        final boolean[] isMatchFound = {false};  // Use an array to hold the flag, as lambdas require variables to be final or effectively final

        boolean success = fileHandler.performOperation(new FileReadOperation( (record) -> {
            try {
                if (isMatch(record)) {
                    printReportResult(record); // Using the default method from ReportStrategy
                    isMatchFound[0] = true;
                }
            } catch (Exception e) {
                System.out.println("Error processing record: " + e.getMessage());
            }
        }));

        return success && isMatchFound[0];  // Return true if operation was successful and a match was found

    }

    @Override
    public void printReportResult(String[] record)
    {
        System.out.printf("Record ID: %s | User ID: %s | Movie Name: %s | Rating: %s | Genre: %s%n",
                record[0], record[1], record[2], record[3], record[4]);
    }

    private boolean isMatch(String[] record)
    {
        return (this.value instanceof String && matchMovieName(record[2])) ||
                (this.value instanceof Integer && record[1].equals(this.value.toString()));
    }

    private boolean matchMovieName(String movieName)
    {
        String searchTerm = (String) this.value;
        return movieName.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
