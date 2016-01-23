package geekyfry.wordcard.service;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import geekyfry.wordcard.R;
import geekyfry.wordcard.model.Word;

/**
 * Created by anangaur on 8/1/2015.
 */
public class WordService {

    private int completedWordIndex = -1;
    private int alphabetWiseIndex = -1;

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public boolean isShowRandom() {
        return showRandom;
    }

    public void setShowRandom(boolean showRandom) {
        this.showRandom = showRandom;
    }

    private Word currentWord;

    private boolean showRandom;
    private int index;

    private String alphabet;

    private Context context;
    private Random randomGenerator = new Random();

    private List<Word> wordList = Collections.EMPTY_LIST;
    private List<Word> pendingWordList = Collections.EMPTY_LIST;
    private List<Word> completedWordList = new ArrayList<>();
    private Map<String, List<Word>> alphabetWiseWordList = Collections.EMPTY_MAP;

    public Word getNextWord() {

        // Check if traversing completed words list


        // Check Random vs. Alphabetwise
        if (showRandom) {
            if (completedWordIndex > -1 && completedWordIndex < completedWordList.size() - 1) {
                Word word = completedWordList.get(completedWordIndex);
                completedWordIndex++;
                return word;
            }
            setCurrentRandomWord();
        } else {
            alphabetWiseIndex++;
            setCurrentAlphabetWiseWord();
        }
        return currentWord;
    }

    public void setCurrentAlphabetWiseWord() {
        if (alphabet == null) {
            setAlphabet("A");
            alphabetWiseIndex = 0;
        }
        if (alphabetWiseIndex < 0) {
           alphabetWiseIndex = 0;
        }

        List<Word> alphaWiseList = alphabetWiseWordList.get(alphabet);
        if (alphabetWiseIndex >= alphaWiseList.size()) {
            setNextAplhabet();
            alphaWiseList = alphabetWiseWordList.get(alphabet);
        }

        Word word = alphaWiseList.get(alphabetWiseIndex);

        currentWord = word;
//        completedWordList.add(word);
//        completedWordIndex = completedWordList.size();
    }


    public void setNextAplhabet() {
        ArrayList<String> alphabets = new ArrayList<String>(getAlphabetsForWords());
        Collections.sort(alphabets);

        alphabet = alphabets.get((alphabets.indexOf(alphabet) + 1) % alphabets.size());

        alphabetWiseIndex = 0;
    }

    public void setPreviousAplhabet() {
        ArrayList<String> alphabets = new ArrayList<String>(getAlphabetsForWords());
        Collections.sort(alphabets);

        int index = alphabets.indexOf(alphabet);

        if(index < 1) {
            alphabet = alphabets.get(alphabets.size() - 1);
        } else {
            alphabet = alphabets.get(index - 1);
        }

        alphabetWiseIndex = 0;
    }

    private void setCurrentRandomWord() {
        int randomInt = randomGenerator.nextInt(pendingWordList.size());
        currentWord = pendingWordList.remove(randomInt);
        completedWordList.add(currentWord);
        completedWordIndex = completedWordList.size();
    }

    public WordService(boolean b, Context applicationContext) {
        showRandom = b;
        alphabet = "A";
        context = applicationContext;
        wordList = getWordsFromFile();

        Collections.sort(wordList, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return lhs.getWord().compareToIgnoreCase(rhs.getWord());
            }
        });

        pendingWordList = new ArrayList<>(wordList);


        setAlphabetWiseWordList();

    }

    private void setAlphabetWiseWordList() {
        if (alphabetWiseWordList.isEmpty())
            alphabetWiseWordList = new HashMap<String, List<Word>>();

        for (Word w : wordList) {
            String startAlpha = w.getWord().substring(0, 1).toUpperCase();
            if (!alphabetWiseWordList.containsKey(startAlpha)) {
                alphabetWiseWordList.put(startAlpha, new ArrayList<Word>());
            }
            alphabetWiseWordList.get(startAlpha).add(w);
        }
    }


    private List<Word> getWordsFromFile() {

        InputStream in = context.getResources().openRawResource(R.raw.wl);
        if (wordList.isEmpty()) {
            wordList = new ArrayList<Word>(1500);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                Word word = getWordFromLine(line);
                wordList.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }

    private Word getWordFromLine(String line) {

        String[] words = line.split("@");

        //0=ID,1=LETTER,2=WORD,3=POS1,4=POS2,5=DEFINITION,6=LEVEL,7=USAGE,8=Synonym,9=Antonym
        Word word = new Word(words);

        return word;
    }

    public Word getPreviousWord() {
        if (showRandom) {
            if (completedWordList.isEmpty() || completedWordIndex == 0) {
                return null;
            }

            if (completedWordIndex == -1) {
                completedWordIndex = completedWordList.size();
            }
            return completedWordList.get(--completedWordIndex);
        }

        if(alphabetWiseIndex > 0) {
            --alphabetWiseIndex;
        } else {
            setPreviousAplhabet();
            alphabetWiseIndex = alphabetWiseWordList.get(alphabet).size() - 1;
        }

        setCurrentAlphabetWiseWord();

        return currentWord;
    }

    public Set<String> getAlphabetsForWords() {
        return alphabetWiseWordList.keySet();
    }

    public void resetIndexForAlphabetWiseWords() {
        alphabetWiseIndex = -1;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public List<Word> getWordList() {
        return wordList;
    }
}
