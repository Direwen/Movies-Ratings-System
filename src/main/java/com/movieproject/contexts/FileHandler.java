package com.movieproject.contexts;

import com.movieproject.interfaces.FileOperation;
import com.movieproject.operations.FileDeleteOperation;
import com.movieproject.operations.FileUpdateOperation;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    private String filePath;
    private String tempFilePath;
    private String tempDirPath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
        this.tempFilePath = "./data/temp_data.csv";
        this.tempDirPath = "./data/temp";
    }

    public String getTempFilePath() {
        return this.tempFilePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void performOperation(FileOperation operation) {
        File mainFile = new File(this.filePath);
        new File(this.tempFilePath);

        try {
            operation.execute(mainFile);
            if (operation instanceof FileUpdateOperation || operation instanceof FileDeleteOperation)
                this.replaceMainFile();
        } catch (IOException var5) {
            IOException e = var5;
            e.printStackTrace();
        }

    }

    public void replaceMainFile() {
        File tempFile = new File(this.tempFilePath);
        File mainFile = new File(this.filePath);
        if (mainFile.delete()) {
            tempFile.renameTo(mainFile);
        }

    }
}
