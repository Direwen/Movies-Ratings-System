package com.movieproject.operations;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;
import de.vandermeer.asciitable.AsciiTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class ListMoviesByGenreOperation implements ReportStrategy, ReportPrinter<AsciiTable> {

    private final TableDecorator tableDecorator;
    private HashMap<String, HashSet<String>> genresHashMap = new HashMap<>();
    private final String genre;

    public ListMoviesByGenreOperation(String genre, TableDecorator tableDecorator)
    {
        this.genre = genre;
        this.tableDecorator = tableDecorator;
    }

    @Override
    public boolean generateReport(FileOperationHandler fileOperationHandler)
    {
        var table = tableDecorator.createTable();
        AtomicBoolean isFound = new AtomicBoolean(false);
        boolean success = fileOperationHandler.performOperation(new FileReadOperation( (record) -> {
            for (String genre : record[4].split("\\|"))
                if (genre.equalsIgnoreCase(this.genre)) {
                    isFound.set(true);
                    if (genresHashMap.computeIfAbsent(this.genre, key -> new HashSet<>()).add(record[2])) tableDecorator.add(table, genre, record[2]);
                }

        }));

        if (success) {
            if (!isFound.get()) tableDecorator.add(table, "No Movie Found");
            this.printReportResult(table);
        }

        return success;
    }

    @Override
    public void printReportResult(AsciiTable table)
    {
        tableDecorator.render(table);
    }
}
