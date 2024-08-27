package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.interfaces.RecordProcessor;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReadOperation implements FileOperation {
    private RecordProcessor processor;

    public FileReadOperation(RecordProcessor processor) {
        this.processor = processor;
    }

    @Override
    public boolean execute(File file) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            // Skip the header by reading
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                processor.process(nextLine);
            }
            return true;
        } catch (Exception err) {
            System.out.println("Error occured during the process of reading records : " + err.getMessage());
            return false;
        }
    }
}
