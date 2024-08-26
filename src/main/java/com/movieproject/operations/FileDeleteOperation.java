package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileDeleteOperation implements FileOperation {
    private int recordId;
    private String tempFilePath;

    public FileDeleteOperation(int recordId, String tempFilePath) {
        this.recordId = recordId;
        this.tempFilePath = tempFilePath;
    }

    @Override
    public void execute(File file) throws IOException {
        File tempFile = new File(this.tempFilePath);

        try (
                CSVReader reader = new CSVReader(new FileReader(file));
                CSVWriter writer = new CSVWriter(new FileWriter(tempFile))
        ) {
            String[] header = reader.readNext(); // Read and write header
            writer.writeNext(header);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (!nextLine[0].equals(Integer.toString(this.recordId))) {
                    writer.writeNext(nextLine);
                }
            }
        } catch (CsvValidationException | IOException err) {
            err.printStackTrace();
        }
    }
}
