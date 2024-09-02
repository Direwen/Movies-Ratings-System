package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppendOperation implements FileOperation {
    private String[] data;

    public FileAppendOperation(String[] data)
    {
        this.data = data;
    }

    @Override
    public boolean execute(File file) throws IOException
    {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            writer.writeNext(this.data);
            return true;
        } catch (Exception err) {
            System.out.println("Error occured during the creation of a new record : " + err.getMessage());
            return false;
        }
    }


}