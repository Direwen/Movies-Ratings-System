package com.movieproject.operations;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;
import com.movieproject.models.MovieRatingRecord;

import java.util.*;

public class ListMovieRatingRecordsByUserOperation implements ReportStrategy, ReportPrinter<ArrayList<MovieRatingRecord>> {

    private final TableDecorator tableDecorator;
    private ArrayList<MovieRatingRecord> list = new ArrayList<>();
    private int userId;

    public ListMovieRatingRecordsByUserOperation(int userId, TableDecorator tableDecorator)
    {
        this.userId = userId;
        this.tableDecorator = tableDecorator;
    }

    @Override
    public boolean generateReport(FileOperationHandler fileOperationHandler)
    {
        boolean success = fileOperationHandler.performOperation(new FileReadOperation( (record) -> {
            if (record[1].equals(Integer.toString(userId)))
                list.add(new MovieRatingRecord(
                        Integer.parseInt(record[0]),
                        Integer.parseInt(record[1]),
                        record[2],
                        Float.parseFloat(record[3]),
                        record[4]
                ));
        }));
        if (success) {
            Collections.sort(list, (a, b) -> Float.compare(b.rating, a.rating));
            printReportResult(list);
        }
        return success;
    }

    @Override
    public void printReportResult(ArrayList<MovieRatingRecord> list)
    {
        var table = tableDecorator.createTable();
        for (MovieRatingRecord record : list) {
            tableDecorator.add(table,
                    "Record " + record.recordId,
                            "User ID " + record.userId,
                            record.movieName,
                            record.rating + " ratings",
                            String.join(", ", record.genres)
                    );
        }
        tableDecorator.render(table);
    }

}
