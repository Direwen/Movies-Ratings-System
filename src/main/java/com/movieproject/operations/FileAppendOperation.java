package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
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
            if (this.isRecordIdValid(file, Integer.parseInt(this.data[0]))) {
                writer.writeNext(this.data);
                return true;
            } else {
                throw new Exception("Record ID must be Unique.");
            }
        } catch (Exception err) {
            System.out.println("Error occured during the creation of a new record : " + err.getMessage());
            return false;
        }
    }

    private boolean isRecordIdValid(File file, int recordId)
    {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            // Skip the header by reading
            reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) if (Integer.parseInt(nextLine[0]) == recordId) return false;
            return true;
        } catch (Exception err) {
            System.out.println("Error occured during the process of reading records : " + err.getMessage());
            return false;
        }
    }
}