package shaban.jama.eindopdracht;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.mRecycler.RecycleViewAdapter;
import shaban.jama.eindopdracht.mSwiper.SwiperHelper;
import shaban.jama.eindopdracht.model.Subdoel;

public class Subdoelen extends AppCompatActivity {

    RecyclerView rv;
    RecycleViewAdapter adapter;

    ArrayList<Subdoel> subdoelen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();

    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData(){

        subdoelen = new ArrayList<>();
        setContentView(R.layout.activity_subdoelen);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Subdoelen_done.class));
            }
        });

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);


//        ContentValues l_value = new ContentValues();
//        l_value.put(DatabaseInfo.Columns.LEERDOEL_NAME,"test");
//
//        dbHelper.insert(DatabaseInfo.databaseTabels.leerdoel, null, l_value);
//
//        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.leerdoel,new String[]{"*"}, null, null, null, null, null);
//        rs.moveToLast();
//        Log.d("laaatse", "leerdoel :" +rs.getString(rs.getColumnIndex("Naam")));
//        Log.d("laaatse", "leerdoel :" +rs.getString(rs.getColumnIndex("_id")));
//
//        ContentValues values = new ContentValues();
//        values.put(DatabaseInfo.Columns.SUBDOEL_NAME,"Shaban");
//        values.put(DatabaseInfo.Columns.WEEK,"WEEK2");
//        values.put(DatabaseInfo.Columns.FK_ID_LEERDOEL,1);
//        values.put(DatabaseInfo.Columns.VOLDAAN,Boolean.FALSE);
//
//        dbHelper.insert(DatabaseInfo.databaseTabels.subdoel, null, values);

         Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"*"}, "Voldaan = 0", null, null, null, null);

        while (rs.moveToNext()) {
            String naam = (String) rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.SUBDOEL_NAME));
            Subdoel subdoel = new Subdoel();
            subdoel.setSubdoel_naam(naam);
            subdoel.setSubdoel_id(rs.getInt(rs.getColumnIndex("_id")));
            subdoelen.add(subdoel);
        }

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecycleViewAdapter(this, subdoelen);

        rv.setAdapter(adapter);

        ItemTouchHelper.Callback callback=new SwiperHelper(adapter, Boolean.TRUE);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }


}

