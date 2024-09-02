package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.interfaces.RecordUpdater;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileUpdateOperation implements FileOperation {
    private RecordUpdater updater;
    private String tempFilePath;

    public FileUpdateOperation(String tempFilePath, RecordUpdater updater) {
        this.tempFilePath = tempFilePath;
        this.updater = updater;
    }

    @Override
    public boolean execute(File file) throws IOException
    {
        File tempFile = new File(this.tempFilePath);
        boolean isUpdated = false;
        String[] clonedRecord;

        try (
                CSVReader reader = new CSVReader(new FileReader(file));
                CSVWriter writer = new CSVWriter(new FileWriter(tempFile))
        ) {
            // Read and write header
            String[] header = reader.readNext();
            writer.writeNext(header);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null)
            {
                // Clone the original record for comparison
                clonedRecord = nextLine.clone();
                this.updater.update(nextLine);
                // Check if the record was updated
                if (!Arrays.equals(clonedRecord, nextLine)) isUpdated = true;
                writer.writeNext(nextLine);
            }
            //If updated, return true, if not, throw an exception to return false
            return isUpdated;

        } catch (Exception err) {
            System.out.println("Error occurred during the process of updating a record: " + err.getMessage());
            return false;
        }
    }
}
