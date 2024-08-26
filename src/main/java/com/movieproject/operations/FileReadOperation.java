package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.processors.RecordProcessor;
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
    public void execute(File file) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.readNext(); // Skip the header

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                processor.process(nextLine);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
