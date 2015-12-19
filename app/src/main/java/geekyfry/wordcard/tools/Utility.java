package geekyfry.wordcard.tools;

import android.app.Application;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geekyfry.wordcard.R;
import geekyfry.wordcard.model.Word;

/**
 * Created by anangaur on 8/1/2015.
 */
public class Utility {
//    private static Word getWordFromLine(String line) {
//
//        String [] words = line.split(",");
//
//        //0=ID,1=LETTER,2=WORD,3=POS1,4=POS2,5=DEFINITION,6=LEVEL,7=USAGE,8=Synonym,9=Antonym
//        Word word = new Word(words);
//
//        return word;
//    }
//
//    public static List<Word> getWordsFromFile() {
//
//        List<Word> wordList = Collections.EMPTY_LIST;
//
//        InputStream in = Application. .getResources().openRawResource(R.raw.words);
//        if (wordList.isEmpty()) {
//            wordList = new ArrayList<Word>(1500);
//        }
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        StringBuilder out = new StringBuilder();
//        String line;
//        try {
//            while ((line = reader.readLine()) != null) {
//                Word word = getWordFromLine(line);
//                wordList.add(word);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return wordList;
//    }
}
