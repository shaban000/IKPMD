package shaban.jama.eindopdracht.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.R;
import shaban.jama.eindopdracht.Model.Subdoel;


/**
 * Created by sangam on 10/01/2017.
 */

public class SubdoelAdapter extends RecyclerView.Adapter<SubdoelAdapter.ViewHolder> {
    Context c;
    DatabaseHelper databaseHelper = DatabaseHelper.getHelper(c);
    ArrayList<Subdoel> subdoels;

    public SubdoelAdapter(Context c, ArrayList<Subdoel> subdoels){
        this.c = c;
        this.subdoels = subdoels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.subdoel,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nametxt.setText(subdoels.get(position).getNaam());
    }

    @Override
    public int getItemCount() {
        return subdoels.size();
    }

    public void updateModel(int pos, Boolean voldaan)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.Columns.VOLDAAN, voldaan);
        String where = "_id = ?";
        String whereArgs [] = new String [] {String.valueOf(subdoels.get(pos).getId())};

        databaseHelper.update(DatabaseInfo.databaseTabels.subdoel,values, where, whereArgs );
        subdoels.remove(pos);
        this.notifyItemRemoved(pos);


    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nametxt;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
