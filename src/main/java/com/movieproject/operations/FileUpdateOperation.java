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
        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            try {
                CSVWriter writer = new CSVWriter(new FileWriter(this.tempFile));

                try {
                    String[] header = reader.readNext();
                    writer.writeNext(header);

                    String[] nextLine;
                    while((nextLine = reader.readNext()) != null) {
                        this.updater.update(nextLine);
                        writer.writeNext(nextLine);
                    }

                    if (file.delete()) {
                        this.tempFile.renameTo(file);
                    }
                } catch (Throwable var8) {
                    try {
                        writer.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }

                    throw var8;
                }

                writer.close();
            } catch (Throwable var9) {
                try {
                    reader.close();
                } catch (Throwable var6) {
                    var9.addSuppressed(var6);
                }

                throw var9;
            }

            reader.close();
        } catch (CsvValidationException | IOException var10) {
            Exception e = var10;
            e.printStackTrace();
        }

    }
}
