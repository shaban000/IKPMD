package shaban.jama.eindopdracht.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Model.Subdoel;
import shaban.jama.eindopdracht.R;
import shaban.jama.eindopdracht.Subdoelen;
import shaban.jama.eindopdracht.Subdoelen_done;

import static shaban.jama.eindopdracht.Leren.weeknr;


/**
 * Created by sangam on 27/01/2017.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {


    Context c;
    ArrayList<Subdoel>subdoels;

    public static final String PREFS_NAME = "MyPrefsFile";

    public WeekAdapter(Context c, ArrayList<Subdoel> subdoels){
        this.c = c;
        this.subdoels = subdoels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.week,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.weektxt.setText("Week " + subdoels.get(position).getWeek());
    }

    @Override
    public int getItemCount() {
        return subdoels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView weektxt;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.weektxt = (TextView) itemView.findViewById(R.id.weekTxt);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent();
            switch (position) {
                case 0:
                    weeknr = 1;
                    intent = new Intent(c, Subdoelen.class);
                    c.startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 2;
                    c.startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 3;
                    c.startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 4;
                    c.startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 5;
                    c.startActivity(intent);
                    break;
                case 5:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 6;
                    c.startActivity(intent);
                    break;
                case 6:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 7;
                    c.startActivity(intent);
                    break;
                case 7:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 8;
                    c.startActivity(intent);
                    break;
                case 8:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 9;
                    c.startActivity(intent);
                    break;
                case 9:
                    intent = new Intent(c, Subdoelen.class);
                    weeknr = 10;
                    c.startActivity(intent);
                    break;
            }
        }
    }
}
