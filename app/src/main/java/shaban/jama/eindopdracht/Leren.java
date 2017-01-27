package shaban.jama.eindopdracht;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Adapter.Week_lijst_adapter;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.model.Subdoel;


public class Leren extends AppCompatActivity {
    private TextView week1;
    private ListView weken_lijst;
    private Week_lijst_adapter weekAdapter;
    ArrayList<Subdoel> subdoelen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leren);

        week1= (TextView)findViewById(R.id.week1);

        week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Subdoelen.class));
            }
        });
    }
}
