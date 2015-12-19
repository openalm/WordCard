package geekyfry.wordcard.tools;

import android.content.Context;

import geekyfry.wordcard.exceptions.ContextNullException;
import geekyfry.wordcard.service.WordService;

/**
 * Created by anangaur on 8/1/2015.
 */
public class AppData {

    private WordService wordService;

    private static AppData instance = new AppData();
    private Context context;
    private boolean showRandom;
    private String selectedAlphabet = "A";

    private AppData() {}

    public static AppData getInstance() {
        return instance;
    }

    public void setContext(Context applicationContext) {
        this.context = applicationContext;
    }

    public void setRandomModeOnOrOff(boolean showRandom) {
        this.showRandom = showRandom;
    }

    public WordService getWordService() throws ContextNullException {
        if(wordService == null) {
            if(context == null) {
                throw new ContextNullException();
            }
            wordService = new WordService(showRandom, context);
        }
        return wordService;
    }

    public void setAlphabet(String alphabetToShow) {
        this.selectedAlphabet = alphabetToShow;
        wordService.setAlphabet(alphabetToShow);
    }
}
