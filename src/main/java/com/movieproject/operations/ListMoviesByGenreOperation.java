package com.movieproject.operations;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;

import java.util.ArrayList;
import java.util.HashMap;

public class ListMoviesByGenreOperation implements ReportStrategy, ReportPrinter<HashMap<String, ArrayList<String>>> {

    private final TableDecorator tableDecorator;
    private HashMap<String, ArrayList<String>> genresHashMap = new HashMap<>();

    public ListMoviesByGenreOperation(TableDecorator tableDecorator)
    {
        this.tableDecorator = tableDecorator;
    }

    @Override
    public boolean generateReport(FileOperationHandler fileOperationHandler)
    {
        boolean success = fileOperationHandler.performOperation(new FileReadOperation( (record) -> {
            String[] genres = record[4].split("\\|");
            for (String genre : genres) genresHashMap.computeIfAbsent(genre, k -> new ArrayList<>()).add(record[2]);
        }));

        if (success) printReportResult(genresHashMap);
        return success;
    }

    @Override
    public void printReportResult(HashMap<String, ArrayList<String>> genresHashMap)
    {
        var table = tableDecorator.createTable();
        genresHashMap.forEach((genre, movies) -> {
            movies.forEach(movie -> tableDecorator.add(table, genre, movie));
        });
        tableDecorator.render(table);
    }
}
