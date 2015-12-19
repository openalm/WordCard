package geekyfry.wordcard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Set;

import geekyfry.wordcard.tools.AppData;

/**
 * Created by anangaur on 8/21/2015.
 */
public class AplhabetSelectorRVAdaptor extends RecyclerView.Adapter<AplhabetSelectorRVAdaptor.ViewHolder>{

    private static final String TAG = ">> ";
    private final Set<String> alphabetsSet;
    private final Context context;
    private final LayoutInflater layoutInflator;

    public AplhabetSelectorRVAdaptor(Context context, Set<String> alphabetsSet) {
        this.context = context;
        layoutInflator = LayoutInflater.from(context);
        this.alphabetsSet = alphabetsSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder called.");
        View view = layoutInflator.inflate(R.layout.item_alphabet, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position < 0 || position > 25) return;

        final String alphabetToShow = String.valueOf((char) ('A' + position));

        boolean isPresent = alphabetsSet.contains(alphabetToShow);

        holder.alphabetItemTV.setText(alphabetToShow);

        if(!isPresent) {
            holder.alphabetContainerVG.setBackgroundColor(context.getResources().getColor(R.color.cGrey500));
        } else {
            holder.alphabetContainerVG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v.findViewById(R.id.alphabetItemTV);
                    AppData.getInstance().setAlphabet(tv.getText().toString());
                    ((AlphabetSelectorActivity)context).done();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 26;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alphabetItemTV;
        ViewGroup alphabetContainerVG;
        public ViewHolder(View itemView) {
            super(itemView);

            alphabetContainerVG = (ViewGroup) itemView.findViewById(R.id.alphabetContainerVG);
            alphabetItemTV = (TextView) itemView.findViewById(R.id.alphabetItemTV);
        }
    }
}
