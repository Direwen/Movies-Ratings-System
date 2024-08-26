package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListMoviesByGenreOperation implements ReportStrategy {

    private HashMap<String, ArrayList<String>> genresHashMap = new HashMap<>();

    @Override
    public void generateReport(FileHandler fileHandler) {
        fileHandler.performOperation(new FileReadOperation( (record) -> {
            String[] genres = record[4].split("\\|");
            for (String genre : genres)
            {
                genresHashMap.computeIfAbsent(genre, k -> new ArrayList<>()).add(record[2]);
            }
        }));

        this.printResults();
    }

    private void printResults()
    {
        System.out.println("Movies by Genre:");

        for (Map.Entry<String, ArrayList<String>> entry : genresHashMap.entrySet())
        {
            String genre = entry.getKey();
            ArrayList<String> movies = entry.getValue();

            System.out.println("\nGenre: " + genre);
            if (movies.isEmpty()) {
                System.out.println("  No movies listed.");
            } else {
                System.out.println("  Movies:");
                for (String movie : movies) {
                    System.out.println("    - " + movie);
                }
            }
        }
    }

}
