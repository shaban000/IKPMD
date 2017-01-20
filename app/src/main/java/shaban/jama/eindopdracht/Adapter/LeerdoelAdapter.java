package shaban.jama.eindopdracht.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import shaban.jama.eindopdracht.Model.Leerdoel;
import shaban.jama.eindopdracht.R;

/**
 * Created by Shaban on 09-01-17.
 */

public class LeerdoelAdapter extends ArrayAdapter<Leerdoel> {


    public LeerdoelAdapter(Context context, int resource, List<Leerdoel> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.list_status, parent, false);
            vh.naam = (TextView) convertView.findViewById(R.id.leerdoel_naam);
            vh.percentage = (TextView) convertView.findViewById(R.id.percentage_compleet);
            vh.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Leerdoel cm = getItem(position);
        vh.naam.setText(cm.getNaam());
        vh.percentage.setText(cm.getPercentage()+"%");
        vh.progressBar.setProgress(cm.getPercentage());

        if ( cm.getPercentage() >= 55 && cm.getPercentage() <= 80){
            vh.progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FFA500"), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (cm.getPercentage() < 55){
            vh.progressBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            vh.progressBar.getProgressDrawable().setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        Log.d("dit is postitie: "+position, " waarvan de percentage "+ cm.getPercentage() +"% is.");
        return convertView;
    }

    private static class ViewHolder {
        TextView naam;
        TextView percentage;
        ProgressBar progressBar;
    }
}
