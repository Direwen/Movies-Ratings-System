package com.movieproject.operations;

import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.ReportStrategy;

import java.util.*;

public class ListMoviesByUserIdOperation implements ReportStrategy {

    private int userId;
    private HashMap<String, Float> moviesHashMap;

    public ListMoviesByUserIdOperation(int userId)
    {
        this.userId = userId;
        this.moviesHashMap = new HashMap<>();
    }

    @Override
    public void generateReport(FileHandler fileHandler)
    {
        fileHandler.performOperation(new FileReadOperation( (record) -> {
            if (record[1].equals(Integer.toString(userId)))
                this.moviesHashMap.put(record[2], Float.parseFloat(record[3]));
        }));
        this.printResults();
    }

    private void printResults()
    {
        System.out.println("All Movies Rated by User ID " + this.userId);
        for(Map.Entry<String, Float> entry : bubbleSort())
        {
            System.out.println("Movie : " + entry.getKey() + " | Rating : " + entry.getValue());
        }
    }

    private ArrayList<Map.Entry<String, Float>> bubbleSort()
    {
        ArrayList<Map.Entry<String, Float>> entries = new ArrayList<>(moviesHashMap.entrySet());
        Map.Entry<String, Float> temp;
        int size = entries.size();
        boolean swapped;

        for (int count = 0; count < size - 2; count++)
        {
            swapped = false;
            for (int index = 0; index < size - count - 1; index++)
            {
                if (entries.get(index).getValue() < entries.get(index + 1).getValue())
                {
                    temp = entries.get(index);
                    entries.set(index, entries.get(index + 1));
                    entries.set(index + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        return entries;
    }
}
