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

    public void execute(File file) throws IOException {
        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            try {
                reader.readNext();

                String[] nextLine;
                while((nextLine = reader.readNext()) != null) {
                    this.processor.process(nextLine);
                }
            } catch (Throwable var6) {
                try {
                    reader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            reader.close();
        } catch (CsvValidationException var7) {
            CsvValidationException e = var7;
            e.printStackTrace();
        }

    }
}
