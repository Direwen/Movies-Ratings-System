import com.movieproject.contexts.FileOperationHandler;
import com.movieproject.interfaces.Validator;
import com.movieproject.managers.AppManager;
import com.movieproject.models.MovieRatingRecord;
import com.movieproject.validators.RecordValidator;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Validator<MovieRatingRecord> recordValidator = new RecordValidator();
//        FileOperationHandler fileOperationHandler = new FileOperationHandler("./data/movies_dataset.csv", recordValidator);
        FileOperationHandler fileOperationHandler = new FileOperationHandler("./data/Sample_Movie_Dataset.csv", recordValidator);
        AppManager appManager = AppManager.getInstance(fileOperationHandler);
        appManager.run();
    }
}