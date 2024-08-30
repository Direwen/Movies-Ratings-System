package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;
import com.movieproject.models.MovieRatingRecord;

import java.util.*;

public class ListMovieRatingRecordsByUser implements ReportStrategy, ReportPrinter<ArrayList<MovieRatingRecord>> {

    private ArrayList<MovieRatingRecord> list = new ArrayList<>();
    private int userId;

    public ListMovieRatingRecordsByUser(int userId)
    {
        this.userId = userId;
    }

    @Override
    public boolean generateReport(FileHandler fileHandler)
    {
        boolean success = fileHandler.performOperation(new FileReadOperation( (record) -> {
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
        System.out.println("Movie Rating Records for User ID " + userId + ":");
        for (MovieRatingRecord record : list) {
            System.out.println("Movie ID: " + record.recordId +
                    ", User ID: " + record.userId +
                    ", Movie Name: " + record.movieName +
                    ", Rating: " + record.rating +
                    ", Genres: " + String.join(", ", record.genres));
        }
    }

}
