package geekyfry.wordcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import geekyfry.wordcard.model.Word;

/**
 * Created by anangaur on 8/21/2015.
 */
public class WordListRVAdaptor extends  RecyclerView.Adapter<WordListRVAdaptor.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflator;
    private final List<DisplayWord> displayWordList;
    private String alphabet = "A";
    private int extraSize = 0;

    public WordListRVAdaptor(Context context, List<DisplayWord> wordList) {
        this.context = context;
        layoutInflator = LayoutInflater.from(context);
//        this.wordList = wordList;
        this.displayWordList = wordList;
        this.extraSize = 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(displayWordList.get(position).isAlphabet()) {
            return 1;
        }
        else
            return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i(TAG, "onCreateViewHolder called.");
        if(viewType == 1) {
            View view = layoutInflator.inflate(R.layout.item_plain_alphabet, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((WordListActivity)context).createDialogIntent();
                }
            });
            return new ViewHolder(view);
        }

        View view = layoutInflator.inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DisplayWord dw = displayWordList.get(position);
        Word word = dw.getWord();

        if(dw.isAlphabet()) {

            holder.alphabetItemTV.setText(word.getWord());

        } else {

            holder.wordTV.setText(word.getWord());
            if (word.getPos() != null && !word.getPos().isEmpty()) {
                holder.posTV.setText("(" + word.getPos() + ")");
                holder.posTV.setVisibility(View.VISIBLE);
            } else {
                holder.posTV.setVisibility(View.GONE);
            }

            holder.meaningTV.setText(word.getMeaning());

        }
    }

    @Override
    public int getItemCount() {
        return displayWordList.size();
    }

//    public void highlight(int alphabet) {
//        int position = displayWordList.indexOf(new DisplayWord(new Word(alphabet), true));
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView wordTV;
        TextView posTV;
        TextView meaningTV;

        ViewGroup alphabetContainerVG;
        TextView alphabetItemTV;

        public ViewHolder(View itemView) {
            super(itemView);

            alphabetContainerVG = (ViewGroup) itemView.findViewById(R.id.alphabetContainerVG);
            alphabetItemTV = (TextView) itemView.findViewById(R.id.alphabetItemTV);

            wordTV = (TextView) itemView.findViewById(R.id.wLWordTV);
            posTV = (TextView) itemView.findViewById(R.id.wLPosTV);
            meaningTV = (TextView) itemView.findViewById(R.id.wLMeaningTV);
        }
    }
}
