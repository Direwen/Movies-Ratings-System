import com.movieproject.contexts.FileHandler;
import com.movieproject.managers.AppManager;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
//        AppManager appManager = new AppManager("./data/movies_dataset.csv");
        FileHandler fileHandler = new FileHandler("./data/Sample_Movie_Dataset.csv");
        AppManager appManager = AppManager.getInstance(fileHandler);
        appManager.run();
    }
}