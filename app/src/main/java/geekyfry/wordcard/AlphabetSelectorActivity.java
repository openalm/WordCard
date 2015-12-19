package geekyfry.wordcard;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

import geekyfry.wordcard.exceptions.ContextNullException;
import geekyfry.wordcard.service.WordService;
import geekyfry.wordcard.tools.AppData;


public class AlphabetSelectorActivity extends ActionBarActivity {

    private RecyclerView mAlphabetSelectorRV;
    private AplhabetSelectorRVAdaptor mAlphabetSelectorRVAdapter;
    private WordService wordService;
    private GridLayoutManager mAlphabetSelectorRVLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_selector);

        mAlphabetSelectorRV = (RecyclerView) findViewById(R.id.alphabetSelectorRV);
        mAlphabetSelectorRV.setHasFixedSize(true);


        try {
            wordService = AppData.getInstance().getWordService();
        } catch (ContextNullException e) {
            e.printStackTrace();
        }

        Set<String> alphaBetsSet = wordService.getAlphabetsForWords();
        

        mAlphabetSelectorRVAdapter = new AplhabetSelectorRVAdaptor(this, alphaBetsSet);

        mAlphabetSelectorRV.setAdapter(mAlphabetSelectorRVAdapter);

        mAlphabetSelectorRVLayoutManager = new GridLayoutManager(this,4);
        mAlphabetSelectorRV.setLayoutManager(mAlphabetSelectorRVLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alphabet_selector, menu);
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

    public void done() {
        finish();
    }
}
