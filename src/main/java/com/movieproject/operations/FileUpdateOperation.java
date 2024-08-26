package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.processors.RecordUpdater;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUpdateOperation implements FileOperation {
    private RecordUpdater updater;
    private File tempFile;

    public FileUpdateOperation(File tempFile, RecordUpdater updater) {
        this.tempFile = tempFile;
        this.updater = updater;
    }

    public void execute(File file) throws IOException {

        try (
                CSVReader reader = new CSVReader(new FileReader(file));
                CSVWriter writer = new CSVWriter(new FileWriter(this.tempFile))
        ) {
            String[] header = reader.readNext();
            writer.writeNext(header);
            String[] nextLine;
            while((nextLine = reader.readNext()) != null) {
                this.updater.update(nextLine);
                writer.writeNext(nextLine);
            }
        } catch (CsvValidationException | IOException err) {
            err.printStackTrace();
        }

    }
}
