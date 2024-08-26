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

    @Override
    public void execute(File file) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {

            writer.writeNext(this.data);

        } catch (IOException err) {

            IOException e = err;
            e.printStackTrace();

        }

    }
}