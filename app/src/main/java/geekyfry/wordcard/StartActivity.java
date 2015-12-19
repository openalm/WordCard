package geekyfry.wordcard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geekyfry.wordcard.model.Word;
import geekyfry.wordcard.service.WordService;


public class StartActivity extends ActionBarActivity {

//    private List<Word> wordList = WordService.wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        WordService.wordList = getWordsFromFile();
        Intent intent = new Intent(this, WordCardActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public List<Word> getWordsFromFile() {
//
//        InputStream in = getResources().openRawResource(R.raw.words);
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
//
//    private Word getWordFromLine(String line) {
//
//        String [] words = line.split(",");
//
//        //0=ID,1=LETTER,2=WORD,3=POS1,4=POS2,5=DEFINITION,6=LEVEL,7=USAGE,8=Synonym,9=Antonym
//        Word word = new Word(words);
//
//        return word;
//    }
}
