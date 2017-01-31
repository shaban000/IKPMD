package shaban.jama.eindopdracht;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DOA;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;


public class Toevoegen extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Cursor rs;
    private TextView toevoegen;
    private Button btn_toevoegen;
    private Spinner leerdoel;
    private Spinner week;
    private ArrayList<String> leerdoelen;
    private ArrayList<String> weken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toevoegen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Toevoegen");
        toevoegen= (EditText) findViewById(R.id.subdoel_toevoegen);
        btn_toevoegen = (Button)findViewById(R.id.btn_toevoegen);
        leerdoel = (Spinner) findViewById(R.id.leerdoelSpinner);
        week = (Spinner) findViewById(R.id.weekSpinner);

        btn_toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toevoegenSubdoel();
            }
        });

        setVoorbeeldData();

        ArrayAdapter<String> leerdoelAdapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_layout,leerdoelen);
        leerdoel.setAdapter(leerdoelAdapter);

        ArrayAdapter<String> weekAdapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_layout,weken);
        week.setAdapter(weekAdapter);



    }

    public void setVoorbeeldData() {
        dbHelper = DatabaseHelper.getHelper(this);
        rs = dbHelper.query(DatabaseInfo.databaseTabels.leerdoel, new String[]{"*"}, null, null, null, null, null);
        leerdoelen = new ArrayList<>();
        weken = new ArrayList<>();

        while (rs.moveToNext()) {
            int id = rs.getInt(rs.getColumnIndex(BaseColumns._ID));
            String naam = rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.LEERDOEL_NAME));
            leerdoelen.add(naam);
        }

        rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"DISTINCT("+DatabaseInfo.Columns.WEEK+")"}, null, null, null, null, null);
        while (rs.moveToNext()) {
            weken.add(rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.WEEK)));
        }
    }

    public void toevoegenSubdoel(){
        rs = dbHelper.query(DatabaseInfo.databaseTabels.leerdoel, new String[]{BaseColumns._ID},
                DatabaseInfo.Columns.LEERDOEL_NAME+" = '"+leerdoel.getSelectedItem().toString()+"'", null, null, null, null);
        rs.moveToFirst();
        int id = rs.getInt(0);
        DOA doa = new DOA(getApplicationContext());
        doa.addSubdoel(toevoegen.getText().toString(),String.valueOf(id),week.getSelectedItem().toString());
        Toast.makeText(getApplicationContext(),"subdoel is toegevoegd",Toast.LENGTH_LONG).show();
        finish();
    }
}
