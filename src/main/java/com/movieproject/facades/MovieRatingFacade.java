package com.movieproject.facades;

import com.movieproject.managers.CsvManager;
import com.movieproject.managers.UserInteractionManager;
import com.movieproject.models.MovieRatingRecord;

public class MovieRatingFacade {
    private CsvManager csvManager;
    private UserInteractionManager userInteractionManager;

    public MovieRatingFacade(String filepath) {
        this.csvManager = new CsvManager(filepath);
        this.userInteractionManager = new UserInteractionManager();
    }

    public void createRecord() {
        MovieRatingRecord newRecord = this.userInteractionManager.createRecord();
        this.csvManager.create(newRecord);
    }

    public void showAllRecords() {
        this.csvManager.read();
    }

    public void updateRecord() {
        MovieRatingRecord updatedRecord = this.userInteractionManager.updateRecord();
        this.csvManager.update(updatedRecord);
    }

    public void deleteRecord() {
        this.csvManager.delete(this.userInteractionManager.deleteRecord());
    }
}
