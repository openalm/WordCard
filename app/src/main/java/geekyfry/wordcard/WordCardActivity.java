package geekyfry.wordcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import geekyfry.wordcard.exceptions.ContextNullException;
import geekyfry.wordcard.model.Word;
import geekyfry.wordcard.service.WordService;
import geekyfry.wordcard.tools.AppData;


public class WordCardActivity extends ActionBarActivity {


    private static final float MIN_DISTANCE = (float) 150.0;
    private static final String PREF_RANDOM = "PREF_RANDOM";
    private static final String PREF_APP = "com.geekyfry.wordcard";
    Random r = new Random();
    private boolean showNextWord = true;
    private Word currentWord;
    private boolean showRandom = true;
    SharedPreferences sharedPreferences;
    WordService wordService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_card);

        sharedPreferences = getSharedPreferences(PREF_APP, MODE_PRIVATE);
        showRandom = sharedPreferences.getBoolean(PREF_RANDOM, true);

        AppData.getInstance().setContext(getApplicationContext());
        AppData.getInstance().setRandomModeOnOrOff(showRandom);

        try {
            wordService = AppData.getInstance().getWordService();
        } catch (ContextNullException e) {
            e.printStackTrace();
        }


        setSettingRandomImg(showRandom);

        setupRandomSelectorBtn();
        setupListViewSelectorBtn();

        ViewGroup ll;

        ll = (ViewGroup) findViewById(R.id.headerVG);

        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        ViewGroup vg = (ViewGroup) findViewById(R.id.alphabetVG);
        vg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alphaIntent = new Intent(getApplicationContext(), AlphabetSelectorActivity.class);
                startActivityForResult(alphaIntent, 0);
            }
        });
        setNextWord();
    }

    private void setupListViewSelectorBtn() {
        ViewGroup settingRandom = (ViewGroup) findViewById(R.id.listVG);
        settingRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRandomSelectorBtn() {
        ViewGroup settingRandom = (ViewGroup) findViewById(R.id.randomVG);
        settingRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSettingRandomToggle();
            }
        });


        ViewGroup ll = (ViewGroup) findViewById(R.id.wordCardVG);

        ll.setOnTouchListener(new View.OnTouchListener() {

            float x1 = 0;
            float x2 = 0;
            float y1 = 0;
            float y2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        float deltaX = x2 - x1;
                        float deltaY = y2 - y1;

                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            if (x2 > x1) {
//                                Toast.makeText(getApplication(), "Right", Toast.LENGTH_SHORT).show();
                                handleL2RTouch();
                            } else {
//                                Toast.makeText(getApplication(), "Left", Toast.LENGTH_SHORT).show();
                                handleR2LTouch();
                            }
                        } else if (Math.abs(deltaY) > MIN_DISTANCE) {
                            if (y2 > y1) {
//                                Toast.makeText(getApplication(), "Down", Toast.LENGTH_SHORT).show();
                                handleT2DTouch();
                            } else {
//                                Toast.makeText(getApplication(), "Up", Toast.LENGTH_SHORT).show();
                                handleD2TTouch();
                            }
                        } else {
//                            Toast.makeText(getApplication(), "Tap", Toast.LENGTH_SHORT).show();
                            setWordDetails();
                        }

                        break;
                }
                return true;
            }

        });
    }

    private void setSettingRandomToggle() {

        showRandom = !showRandom;
        wordService.setShowRandom(showRandom);

        showAlphabetTitle(showRandom);
        setSettingRandomImg(showRandom);
        sharedPreferences.edit().putBoolean(PREF_RANDOM, showRandom).commit();

        if(showRandom == false) {
            Intent alphaIntent = new Intent(getApplicationContext(), AlphabetSelectorActivity.class);
            startActivityForResult(alphaIntent, 0);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resetWordCard();
    }

    private void resetWordCard() {
        wordService.resetIndexForAlphabetWiseWords();
        currentWord = wordService.getNextWord();
        showWord();
        showAlphabetTitle(showRandom);
    }

    private void setSettingRandomImg(boolean enableRandom) {
        ImageView ivEnabledImg = (ImageView) findViewById(R.id.settingRandomEnabledImg);
        ImageView ivDisabledImg = (ImageView) findViewById(R.id.settingRandomDisabledImg);
        if (enableRandom) {
            ivEnabledImg.setVisibility(View.VISIBLE);
            ivDisabledImg.setVisibility(View.GONE);
            Toast.makeText(getApplication(), "Random mode is ON", Toast.LENGTH_SHORT).show();

        } else {
            ivEnabledImg.setVisibility(View.GONE);
            ivDisabledImg.setVisibility(View.VISIBLE);
            Toast.makeText(getApplication(), "Random mode is OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleT2DTouch() {
        if(showRandom) {
            showPreviousWord();
        } else {
            wordService.setPreviousAplhabet();
            wordService.setCurrentAlphabetWiseWord();
            currentWord = wordService.getCurrentWord();
            showWord();
            showAlphabetTitle(showRandom);
        }
    }

    private void handleL2RTouch() {
        showPreviousWord();
    }

    private void handleD2TTouch() {
        if(showRandom) {
            handleR2LTouch();
        } else {
            wordService.setNextAplhabet();
            wordService.setCurrentAlphabetWiseWord();
            currentWord = wordService.getCurrentWord();
            showWord();
            showAlphabetTitle(showRandom);
        }
    }

    private void showPreviousWord() {
        currentWord = wordService.getPreviousWord();
        showWord();
        setWordDetails();
    }

    private void handleR2LTouch() {
        if (showNextWord) {
            setNextWord();
        } else {
            setWordDetails();
        }
    }

    public void setWordDetails() {
        if(currentWord == null) return;
        TextView tv = (TextView) findViewById(R.id.wordMeaningTV);
        tv.setText(currentWord.getMeaning());
        tv.setVisibility(View.VISIBLE);

        if (currentWord.getUsage() != null && !currentWord.getUsage().isEmpty()) {
            tv = (TextView) findViewById(R.id.wordUsageTV);
            tv.setText("Usage: " + currentWord.getUsage());
            tv.setVisibility(View.VISIBLE);
        }
        showNextWord = true;
    }

//    private void getPreviousWord() {
//        currentWord = wordService.getPreviousWord();
//        showWord();
//    }

    private void setNextWord() {
        currentWord = wordService.getNextWord();
        showWord();
    }

    private void showWord() {

        if(currentWord == null) {
            Toast.makeText(getApplication(), "All done!", Toast.LENGTH_SHORT).show();
            return;
        }

        showAlphabetTitle(showRandom);

        TextView tv = (TextView) findViewById(R.id.wordTV);
        tv.setText(currentWord.getWord());

        tv = (TextView) findViewById(R.id.wordPosTV);
        if (currentWord.getPos() != null && !currentWord.getPos().isEmpty()) {
            String pos = "(" + currentWord.getPos();
            if (currentWord.getPos2() != null && !currentWord.getPos2().isEmpty())
                pos = pos + ", " + currentWord.getPos2();

            pos = pos + ")";
            tv.setText(pos);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
        showNextWord = false;

        tv = (TextView) findViewById(R.id.wordMeaningTV);
        tv.setVisibility(View.GONE);

        tv = (TextView) findViewById(R.id.wordUsageTV);
        tv.setVisibility(View.GONE);
    }

    private void showAlphabetTitle(boolean showRandom) {
        ViewGroup vg = (ViewGroup) findViewById(R.id.alphabetVG);
        if(showRandom) {
            vg.setVisibility(View.GONE);
        } else  {
            TextView tv = (TextView) findViewById(R.id.alphabetTV);
            tv.setText(currentWord.getWord().substring(0,1).toUpperCase());
            vg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_card, menu);
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


}
