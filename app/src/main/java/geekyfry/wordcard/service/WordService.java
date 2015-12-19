package geekyfry.wordcard.service;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import geekyfry.wordcard.model.Word;

/**
 * Created by anangaur on 8/1/2015.
 */
public class WordService {

    private static List<Word> wordList = Collections.EMPTY_LIST;

    public static List<Word> getWordsFromFile() {
        Uri filePath = Uri.parse("file:///android_asset/words");
        return getWordsFromFile(filePath.toString());
    }

    private static List<Word> getWordsFromFile(String filePath) {
        if(wordList.isEmpty()) {
            File file = new File(filePath);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    Word word = getWordFromLine(line);
                }
                br.close();
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }
        }

        return wordList;
    }

    private static Word getWordFromLine(String line) {

        String [] words = line.split(",");

        Word word = new Word(words[2], words[3], words[4], words[5], Integer.getInteger(words[6]));

        return word;
    }
}
