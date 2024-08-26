package com.movieproject.operations;

import com.movieproject.interfaces.ReportStrategy;
import com.movieproject.processors.RecordProcessor;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RatingCountOperation implements ReportStrategy {

    //Hashmap to store the rating number and its count
    private HashMap<Float, Integer> hashMap = new HashMap<>();

    @Override
    public void generateReport(FileHandler fileHandler) {
        //Will utilize FileReadOperation
        fileHandler.performOperation(new FileReadOperation( (record) -> {
            try {
                float rating = Float.parseFloat(record[3]); // Convert rating to float
                hashMap.put(rating, hashMap.getOrDefault(rating, 0) + 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }));

        this.printResults();
    }

    private void printResults() {
        System.out.println("Rating Count Report:");
        for (Map.Entry<Float, Integer> entry : hashMap.entrySet()) {
            System.out.println("Rating: " + entry.getKey() + " - Count: " + entry.getValue());
        }
    }
}
