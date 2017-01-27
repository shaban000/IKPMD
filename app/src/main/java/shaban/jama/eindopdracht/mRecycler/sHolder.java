package shaban.jama.eindopdracht.mRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import shaban.jama.eindopdracht.R;

/**
 * Created by sangam on 10/01/2017.
 */

public class sHolder extends RecyclerView.ViewHolder {
    TextView nametxt;

    public sHolder(View itemView) {
        super(itemView);

        this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
    }
}
