package com.movieproject.contexts;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.interfaces.Validator;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.operations.FileUpdateOperation;

import java.io.File;
import java.io.IOException;

/**
 * The FileHandler class is responsible for handling file operations such as reading, writing,
 * updating, and deleting records in a CSV file. It also manages a temporary file during
 * operations that require replacing the original file.
 */
public class FileHandler {
    private String filePath;
    private String tempFilePath;
    private Validator validator;

    /**
     * Constructs a FileHandler with the specified file path.
     *
     * @param filePath The path of the main file to operate on.
     */
    public FileHandler(String filePath, Validator validator) {
        this.filePath = filePath;
        this.tempFilePath = "./data/temp_data.csv";
        this.validator = validator;
    }

    /**
     * Gets the path of the temporary file.
     *
     * @return The path of the temporary file used during operations.
     */
    public String getTempFilePath() {
        return this.tempFilePath;
    }

    /**
     * Gets the path of the main file.
     *
     * @return The path of the main file to operate on.
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Executes the specified file operation. If the operation requires replacing the
     * original file (e.g., update or delete operations), the method ensures the main file
     * is replaced with the temp file.
     *
     * @param operation The file operation to perform.
     * @return True if the operation is successful, false otherwise.
     */
    public boolean performOperation(FileOperation operation)
    {
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

    /**
     * Replaces the main file with the temporary file.
     *
     * @return True if the replacement is successful, false otherwise.
     */
    private boolean replaceMainFile()
    {
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

    /**
     * Deletes the temporary file if it exists.
     *
     */
    private void deleteTempFile() {
        File tempFile = new File(this.tempFilePath);
        if (tempFile.exists()) {
            if (!tempFile.delete()) {
                System.out.println("Failed to delete temp file.");
            }
        }
    }

    public Validator getValidator() {
        return validator;
    }
}
