package shaban.jama.eindopdracht;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;

public class Instellingen extends AppCompatActivity {
    private static String ip = "192.168.0.106";
    private EditText textIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Instellingen");
        textIP = (EditText) findViewById(R.id.textIP);
    }
    public void loginKnop(View view){
        startActivity(new Intent(getApplicationContext(),Login.class));
    }

    public void changeIP(View view){
        setIp(textIP.getText().toString());
        textIP.setText("");
        Toast.makeText(getApplicationContext(),"ip is veranderd",Toast.LENGTH_SHORT).show();
    }
    public void verwijderData(View view){
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());
        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{BaseColumns._ID}, null, null, null, null, null);
        while (rs.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put(DatabaseInfo.Columns.VOLDAAN, Boolean.FALSE);
            String where = "_id = ?";
            String whereArgs [] = new String [] {rs.getString(rs.getColumnIndex(BaseColumns._ID))};
            dbHelper.update(DatabaseInfo.databaseTabels.subdoel,values, where, whereArgs );
        }
        Toast.makeText(getApplicationContext(),"U voortgang is verwijdert",Toast.LENGTH_SHORT).show();
    }


    private void setIp(String ip) {
        Instellingen.ip = ip;
    }

    public static String getIp() {
        return ip;
    }
}

