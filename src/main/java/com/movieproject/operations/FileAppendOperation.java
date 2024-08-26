package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppendOperation implements FileOperation {
    private String[] data;

    public FileAppendOperation(String[] data) {
        this.data = data;
    }

    public void execute(File file) throws IOException {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file, true));

            try {
                writer.writeNext(this.data);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            IOException e = var7;
            e.printStackTrace();
        }

    }
}