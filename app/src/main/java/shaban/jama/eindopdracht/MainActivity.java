package shaban.jama.eindopdracht;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shaban.jama.eindopdracht.Database.DOA;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {
    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("\tIRDM Leren");

        int hoogte = 150;
        int duration = 1000;

        TextView leren = (TextView) findViewById(R.id.textView1);
        leren.setY(hoogte * 3);
        leren.animate().translationY(0);
        leren.animate().setDuration(duration * 2 );
        leren.animate();

        TextView toevoegen = (TextView) findViewById(R.id.textView2);
        toevoegen.setY(hoogte * 2);
        toevoegen.animate().translationY(0);
        toevoegen.animate().setDuration(duration + duration / 2);
        toevoegen.animate();

        TextView instellingen = (TextView) findViewById(R.id.textView3);
        instellingen.setY(hoogte);
        instellingen.animate().translationY(0);
        instellingen.animate().setDuration(duration);
        instellingen.animate();

        setupDatabase();


    }

    @Override
    public void onResume() {
        super.onResume();
        setupDatabase();
    }

    private void setData() {
        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("Voortgang");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);


        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(2000, Easing.EasingOption.EaseInQuart);

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());

        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"COUNT(*)"}, "Voldaan = 1", null, null, null, null);
        rs.moveToFirst();
        int currentEcts = rs.getInt(0);
        int totaal = dbHelper.countTabel(DatabaseInfo.databaseTabels.subdoel);

        Log.d("----aantal: "+totaal,"---currentECTS: "+currentEcts);
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(currentEcts, 0));
        xValues.add(getResources().getString(R.string.Gedaan));

        yValues.add(new Entry(totaal - currentEcts, 1));
        xValues.add(getResources().getString(R.string.Tedoen));

        int test = 0;
        if (currentEcts > 0) {
            Log.d("testwaarde", String.valueOf((currentEcts * 100) / totaal));
            test = ((currentEcts * 100) / totaal);
        }
        ArrayList<Integer> colors = new ArrayList<>();
        if (test < 30) {
            colors.add(Color.rgb(244,81,30));
        } else if (test < 50){
            colors.add(Color.rgb(235,0,0));
        } else if  (test < 70) {
            colors.add(Color.rgb(253,216,53));
        } else {
            colors.add(Color.rgb(67,160,71));
        }
        colors.add(Color.rgb(255,0,0));

        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data);

    }

    public void status(View view){
        startActivity(new Intent(getApplicationContext(),Status.class));
    }

    public void leren(View view){
        startActivity(new Intent(getApplicationContext(),Leren.class));
    }

    public void toevoegen(View view){
        startActivity(new Intent(getApplicationContext(),Toevoegen.class));
    }

    public void instellingen(View view){
        startActivity(new Intent(getApplicationContext(),Instellingen.class));
    }

    public void setupDatabase(){
        DOA doa = new DOA(getApplicationContext());
        doa.getLeerdoelen();
        doa.getSubdoelen();
        setData();
    }
}
