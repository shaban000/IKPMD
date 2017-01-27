package shaban.jama.eindopdracht.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import shaban.jama.eindopdracht.R;
import shaban.jama.eindopdracht.model.Subdoel;

/**
 * Created by sangam on 20/01/2017.
 */

public class Week_lijst_adapter extends ArrayAdapter<Subdoel> {

    public Week_lijst_adapter(Context context, int resource, List<Subdoel> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.week, parent, false);
            vh.week = (TextView) convertView.findViewById(R.id.week);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Subdoel sd = getItem(position);
        vh.week.setText((CharSequence) sd.getWeek());
        return convertView;
    }

    private static class ViewHolder {
        TextView week;
    }
}
