package shaban.jama.eindopdracht;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

//import shaban.jama.eindopdracht.Adapter.Week_lijst_adapter;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.Model.Subdoel;
import shaban.jama.eindopdracht.Adapter.WeekAdapter;


public class Leren extends AppCompatActivity {
    private RecyclerView rv;
    private WeekAdapter adapter;
    ArrayList<Subdoel> subdoelen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leren);
        setData();
    }
    //public static ArrayList<String> weken = new ArrayList<String>();
    public static int weeknr;

    private void setData(){
        subdoelen = new ArrayList<>();
        setContentView(R.layout.activity_leren);

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);

        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"DISTINCT("+DatabaseInfo.Columns.WEEK+")"}, null, null, null, null, null);
        Intent intent = new Intent();


        while (rs.moveToNext()) {
            String week = rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.WEEK));
            //weken.add(week);
            Subdoel subdoel = new Subdoel();
            subdoel.setWeek(week);
            subdoelen.add(subdoel);
        }

        rv= (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WeekAdapter(this, subdoelen);

        rv.setAdapter(adapter);


    }
}
