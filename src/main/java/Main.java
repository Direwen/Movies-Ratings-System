import com.movieproject.contexts.FileHandler;
import com.movieproject.interfaces.Validator;
import com.movieproject.managers.AppManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.validators.RecordValidator;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
//        FileHandler fileHandler = new FileHandler("./data/movies_dataset.csv", recordValidator);
        FileHandler fileHandler = new FileHandler("./data/Sample_Movie_Dataset.csv", recordValidator);
        AppManager appManager = AppManager.getInstance(fileHandler);
        appManager.run();
    }
}