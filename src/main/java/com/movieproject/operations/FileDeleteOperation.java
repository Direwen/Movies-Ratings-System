package com.movieproject.operations;

import com.movieproject.interfaces.FileOperation;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
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

    @Override
    public boolean execute(File file) throws IOException {
        File tempFile = new File(this.tempFilePath);

        try (
                CSVReader reader = new CSVReader(new FileReader(file));
                CSVWriter writer = new CSVWriter(new FileWriter(tempFile))
        ) {
            // Read and write header
            String[] header = reader.readNext();
            writer.writeNext(header);
            String[] nextLine;
            boolean isDeleted = false;
            while ((nextLine = reader.readNext()) != null)
            {
                if (!nextLine[0].equals(Integer.toString(this.recordId))) {
                    writer.writeNext(nextLine);
                } else {
                    isDeleted = true;
                }
            }

            //Checking if the file was actually deleted
            if (isDeleted) {
                return true;
            } else {
                System.out.println("A record with " + this.recordId + " ID wasn't found");
                return false;
            }

        } catch (Exception err) {
            System.out.println("Error occured during the process of reading records : " + err.getMessage());
            return false;
        }
    }
}
