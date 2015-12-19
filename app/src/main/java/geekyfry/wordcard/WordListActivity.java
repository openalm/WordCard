package geekyfry.wordcard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import geekyfry.wordcard.exceptions.ContextNullException;
import geekyfry.wordcard.model.Word;
import geekyfry.wordcard.service.WordService;
import geekyfry.wordcard.tools.AppData;


public class WordListActivity extends ActionBarActivity {

    private RecyclerView mWordListRV;
    private WordService wordService;
    private WordListRVAdaptor mWordListRVAdapter;
    private LinearLayoutManager mWordListRVLayoutManager;
    private List<DisplayWord> displayWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        mWordListRV = (RecyclerView) findViewById(R.id.wordListRV);
        mWordListRV.setHasFixedSize(true);


        try {
            wordService = AppData.getInstance().getWordService();
        } catch (ContextNullException e) {
            e.printStackTrace();
        }

        List<Word> wordList = wordService.getWordList();

        displayWordList = getDisplayWordList(wordList);
        mWordListRVAdapter = new WordListRVAdaptor(this, displayWordList);

        mWordListRV.setAdapter(mWordListRVAdapter);

        mWordListRVLayoutManager = new LinearLayoutManager(this);
        mWordListRV.setLayoutManager(mWordListRVLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_list, menu);
        return true;
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

    public void createDialogIntent() {
        Intent alphaIntent = new Intent(getApplicationContext(), AlphabetSelectorActivity.class);
        startActivityForResult(alphaIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position = displayWordList.indexOf(new DisplayWord(wordService.getAlphabet(),true));
        mWordListRVLayoutManager.scrollToPositionWithOffset(position,10);
    }

    private List<DisplayWord> getDisplayWordList(List<Word> wordList) {
        List<DisplayWord> displayWordList = new ArrayList<DisplayWord>();

        String alphabet = "A";
        displayWordList.add(new DisplayWord(new Word("A"), true));

        for(Word word:wordList) {
            if(!word.getStartingAlphabet().equalsIgnoreCase(alphabet)) {
                alphabet = word.getStartingAlphabet();
                displayWordList.add(new DisplayWord(new Word(alphabet), true));
            }
            displayWordList.add(new DisplayWord(word, false));
        }

        return displayWordList;
    }
}
