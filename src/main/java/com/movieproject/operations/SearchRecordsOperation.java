package com.movieproject.operations;

import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.decorations.TableDecorator;
import com.movieproject.interfaces.ReportPrinter;
import com.movieproject.interfaces.ReportStrategy;
import de.vandermeer.asciitable.AsciiTable;

public class SearchRecordsOperation implements ReportStrategy, ReportPrinter<AsciiTable> {

    private final TableDecorator tableDecorator;
    private Object value; // To accept String or Integer

    public SearchRecordsOperation(String value, TableDecorator tableDecorator)
    {
        this.tableDecorator = tableDecorator;
        this.value = value;
    }

    public SearchRecordsOperation(int value, TableDecorator tableDecorator)
    {
        this.tableDecorator = tableDecorator;
        this.value = value;
    }

    @Override
    public boolean generateReport(FileOperationHandler fileOperationHandler)
    {
        final boolean[] isMatchFound = {false};  // Use an array to hold the flag, as lambdas require variables to be final or effectively final
        var table = tableDecorator.createTable();
        boolean success = fileOperationHandler.performOperation(new FileReadOperation( (record) -> {
            try {
                if (isMatch(record)) {
                    tableDecorator.add(
                            table,
                            "Record " + record[0],
                            "User ID " + record[1],
                            record[2],
                            record[3] + " ratings",
                            String.join(", ", record[4])
                    );
                    isMatchFound[0] = true;
                }
            } catch (Exception e) {
                System.out.println("Error processing record: " + e.getMessage());
            }
        }));

        if (!isMatchFound[0]) tableDecorator.add(table, "No Record Found");
        printReportResult(table);
        return success && isMatchFound[0];  // Return true if operation was successful and a match was found
    }

    @Override
    public void printReportResult(AsciiTable table)
    {
        tableDecorator.render(table);
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
