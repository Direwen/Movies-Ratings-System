package com.movieproject.contexts;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.operations.FileUpdateOperation;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    private String filePath;
    private String tempFilePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
        this.tempFilePath = "./data/temp_data.csv";
    }

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public boolean performOperation(FileOperation operation) {
        File mainFile = new File(this.filePath);
        new File(this.tempFilePath);

        try {
            boolean success = operation.execute(mainFile);
            if (success && (operation instanceof FileUpdateOperation || operation instanceof FileDeleteOperation)) {
                return replaceMainFile();
            }
            return success;
        } catch (IOException e) {
            System.out.println("Error occurred during file operation: " + e.getMessage());
            deleteTempFile(); // Ensure temp file is deleted on error
            return false;
        }

    }

    private boolean replaceMainFile() {
        File tempFile = new File(this.tempFilePath);
        File mainFile = new File(this.filePath);

        if (!tempFile.exists()) {
            System.out.println("Temp file does not exist.");
            return false;
        }

        if (mainFile.delete()) {
            if (tempFile.renameTo(mainFile)) {
                return true;
            } else {
                System.out.println("Failed to rename temp file to main file.");
                return false;
            }
        } else {
            System.out.println("Failed to delete the original file.");
            return false;
        }
    }

    private void deleteTempFile() {
        File tempFile = new File(this.tempFilePath);
        if (tempFile.exists()) {
            if (!tempFile.delete()) {
                System.out.println("Failed to delete temp file.");
            }
        }
    }
}
