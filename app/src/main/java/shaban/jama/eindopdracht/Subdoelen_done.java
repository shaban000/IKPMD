package shaban.jama.eindopdracht;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.mRecycler.RecycleViewAdapter;
import shaban.jama.eindopdracht.mSwiper.SwiperHelper;
import shaban.jama.eindopdracht.model.Subdoel;

public class Subdoelen_done extends AppCompatActivity {

    RecyclerView rv;
    RecycleViewAdapter adapter;

    ArrayList<Subdoel> subdoelen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subdoelen);
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);


        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"*"}, "Voldaan = 1", null, null, null, null);

        while (rs.moveToNext()) {
            String naam = (String) rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.SUBDOEL_NAME));
            Subdoel subdoel = new Subdoel();
            subdoel.setNaam(naam);
            subdoel.setId(rs.getInt(rs.getColumnIndex("_id")));
            subdoelen.add(subdoel);
        }

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecycleViewAdapter(this, subdoelen);
        rv.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwiperHelper(adapter, Boolean.FALSE);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }
}
