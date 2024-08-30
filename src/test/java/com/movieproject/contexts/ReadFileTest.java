package com.movieproject.contexts;

import com.movieproject.operations.FileReadOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

    private static FileHandler fileHandler;

    @BeforeAll
    static void init()
    {
        fileHandler = new FileHandler("./data/Sample_Movie_Dataset.csv");
    }

    @Test
    void performOperation()
    {
        boolean success = fileHandler.performOperation(new FileReadOperation( (record) -> {
            System.out.println(Arrays.toString(record));
        }));

        assertTrue(success);
    }
}