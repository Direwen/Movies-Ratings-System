package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportStrategy;

public class SearchRecordsOperation implements ReportStrategy {

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
    public void generateReport(FileHandler fileHandler)
    {
        fileHandler.performOperation(new FileReadOperation( (record) -> {
            try {
                if (
                        (this.value instanceof String && this.matchMovieName(record[2])) ||
                        (this.value instanceof Integer && Integer.parseInt(record[1]) == (int) this.value)
                ) {
                    this.printResult(record);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing integer value: " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error accessing record field: " + e.getMessage());
            }
        }));
    }

    private boolean matchMovieName(String movieName)
    {
        String searchTerm = (String) this.value;
        return movieName.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private void printResult(String[] record)
    {
        System.out.printf("Record ID: %s | User ID: %s | Movie Name: %s | Rating: %s | Genre: %s%n",
                record[0], record[1], record[2], record[3], record[4]);
    }
}
