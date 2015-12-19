package geekyfry.wordcard.model;

/**
 * Created by anangaur on 8/20/2015.
 */
public class WordCardPrefs {

    private static WordCardPrefs wordCardPrefs = new WordCardPrefs();

    private boolean randomModeEnabled = false;


    public static WordCardPrefs getInstance() {
        return wordCardPrefs;
    }

}
