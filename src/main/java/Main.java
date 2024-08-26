import com.movieproject.managers.AppManager;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        AppManager appManager = new AppManager("./data/movies_dataset.csv");
        appManager.run();
    }
}