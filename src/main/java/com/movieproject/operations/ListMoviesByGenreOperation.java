package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class ListMoviesByGenreOperation implements ReportStrategy, ReportPrinter<HashMap<String, ArrayList<String>>> {

    private HashMap<String, ArrayList<String>> genresHashMap = new HashMap<>();

    @Override
    public boolean generateReport(FileHandler fileHandler)
    {
        boolean success = fileHandler.performOperation(new FileReadOperation( (record) -> {
            String[] genres = record[4].split("\\|");
            for (String genre : genres) genresHashMap.computeIfAbsent(genre, k -> new ArrayList<>()).add(record[2]);
        }));

        if (success) printReportResult(genresHashMap);
        return success;
    }

    @Override
    public void printReportResult(HashMap<String, ArrayList<String>> genresHashMap)
    {
        System.out.println("Movies by Genre:");
        genresHashMap.forEach((genre, movies) -> {
            System.out.printf("Genre: %s%n", genre);
            movies.forEach(movie -> System.out.printf("  - %s%n", movie));
        });
    }
}
