package shaban.jama.eindopdracht;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.Adapter.SubdoelAdapter;
import shaban.jama.eindopdracht.Adapter.WeekAdapter;
import shaban.jama.eindopdracht.mSwiper.SwiperHelper;
import shaban.jama.eindopdracht.Model.Subdoel;

import static shaban.jama.eindopdracht.Leren.weeknr;


public class Subdoelen extends AppCompatActivity {

    private RecyclerView rv;
    private SubdoelAdapter adapter;
    private WeekAdapter weekAdapter;
    private Subdoel subdoel = new Subdoel();
    private LinearLayout ll;


    Button button;

    ArrayList<Subdoel> subdoelen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("Subdoelen ToDo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll = (LinearLayout) findViewById(R.id.activity_subdoelen);

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
        button = (Button) findViewById(R.id.toDone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Subdoelen_done.class));
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Subdoelen_done.class));
//            }
//        });

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);


        Bundle b = getIntent().getExtras();
        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"*"}, "Voldaan = 0 AND Week ="+weeknr , null, null, null, null);
        while (rs.moveToNext()) {
            String naam = rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.SUBDOEL_NAME));
            Subdoel subdoel = new Subdoel();
            subdoel.setNaam(naam);
            subdoel.setId(rs.getInt(rs.getColumnIndex("_id")));
            subdoelen.add(subdoel);
        }

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        View v = getWindow().getDecorView().getRootView();

        adapter = new SubdoelAdapter(getApplicationContext(),v,subdoelen);

        rv.setAdapter(adapter);

        ItemTouchHelper.Callback callback=new SwiperHelper(adapter,getApplicationContext(), Boolean.TRUE);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }


}