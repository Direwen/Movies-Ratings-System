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

    public void execute(File file) throws IOException {
        File tempFile = new File(this.tempFilePath);

        try {
            CSVReader reader = new CSVReader(new FileReader(file));

            try {
                CSVWriter writer = new CSVWriter(new FileWriter(tempFile));

                try {
                    String[] header = reader.readNext();
                    writer.writeNext(header);

                    String[] nextLine;
                    while((nextLine = reader.readNext()) != null) {
                        if (!nextLine[0].equals(Integer.toString(this.recordId))) {
                            writer.writeNext(nextLine);
                        }
                    }
                } catch (Throwable var9) {
                    try {
                        writer.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }

                    throw var9;
                }

                writer.close();
            } catch (Throwable var10) {
                try {
                    reader.close();
                } catch (Throwable var7) {
                    var10.addSuppressed(var7);
                }

                throw var10;
            }

            reader.close();
        } catch (CsvValidationException var11) {
            CsvValidationException e = var11;
            e.printStackTrace();
        }

    }
}
