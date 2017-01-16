package shaban.jama.eindopdracht.mRecycler;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.R;
import shaban.jama.eindopdracht.model.Subdoel;

/**
 * Created by sangam on 10/01/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<sHolder> {
    Context c;
    DatabaseHelper databaseHelper = DatabaseHelper.getHelper(c);
    ArrayList<Subdoel> subdoels;

    public RecycleViewAdapter(Context c, ArrayList<Subdoel> subdoels){
        this.c = c;
        this.subdoels = subdoels;
    }

    @Override
    public sHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        sHolder holder=new sHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(sHolder holder, int position) {
        holder.nametxt.setText(subdoels.get(position).getSubdoel_naam());

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
        String whereArgs [] = new String [] {String.valueOf(subdoels.get(pos).getSubdoel_id())};

        databaseHelper.update(DatabaseInfo.databaseTabels.subdoel,values, where, whereArgs );
        subdoels.remove(pos);
        this.notifyItemRemoved(pos);


    }
}
