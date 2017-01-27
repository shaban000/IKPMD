package shaban.jama.eindopdracht;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Comparator;

import shaban.jama.eindopdracht.Adapter.LeerdoelAdapter;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.Model.Leerdoel;

public class Status extends AppCompatActivity {
    private CombinedChart mChart;
    private ListView listView;
    private ArrayList<Leerdoel> leerdoelen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        listView = (ListView) findViewById(R.id.listLeerdoelen);
        listView.setVisibility(View.GONE);
        listView.setDivider(null);

        mChart = (CombinedChart) findViewById(R.id.chart);


        setVoorbeeldData();

        LeerdoelAdapter adapter = new LeerdoelAdapter(this,0, leerdoelen);
        adapter.sort(new Comparator<Leerdoel>() {
            public int compare(Leerdoel arg0, Leerdoel arg1) {
                return arg1.getPercentage() - arg0.getPercentage();
            }
        });

        listView.setAdapter(adapter);


        mChart.setDescription("Status per week");           // Deze text moet naar de strings.xml
        mChart.setDescriptionPosition(630,25);
        mChart.animateY(1400);
        mChart.setDrawingCacheBackgroundColor(Color.BLACK);
        mChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart.getRendererXAxis().getPaintGrid().setColor(Color.YELLOW);
        mChart.getXAxis().setDrawGridLines(false);

        setBarData();
    }

    private void setBarData() {
        BarData bar = new BarData();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0;i<7;i++) {
            barEntries.add(new BarEntry((float) Math.random()*100, i));
        }
        BarDataSet set = new BarDataSet(barEntries, "Percentage compleet");  // Deze text moet naar de strings.xml
        set.setColor(Color.BLUE);

        bar.addDataSet(set);

        // ADD data to the chart
        String[] xValues = {"1","2","3","4","5","6","7"};
        CombinedData data = new CombinedData(xValues);
//        data.setData(line);
        data.setData(bar);
        mChart.setData(data);
        mChart.invalidate();

    }

    public void showlist(View view){
        if (listView.getVisibility() == View.GONE){
            listView.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.GONE);
        }
    }

//    public void setVoorbeeldData(){
//
//        String[] values = new String[] { "Een gegevensverzameling te beschrijven.",
//       "Een uitgebreid ERD te maken.", "Functional dependencies in de relaties te bepalen.",
//       "Primary keys en foreign keys te bepalen in de relaties.", "Een generic data model te maken.",
//       "De relaties te normaliseren van nulde (0NF) tot derde (3NF) normaalvorm of Boyce-Codd normaalvorm.",
//       "Referentiediagrammen te maken voor de foreign keys.", "Het uiteindelijke model te implementeren in SQL d.m.v. tables, primary en foreing keys, indices, constraints en views."
//        };
//        leerdoelen = new ArrayList<>();
//        for (String s: values){
//            leerdoelen.add(new Leerdoel(s, (int)(Math.random()*100))) ;
//        }
//    }

    public void setVoorbeeldData(){
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.leerdoel, new String[]{"*"}, null, null, null, null, null);
        leerdoelen = new ArrayList<>();

        while(rs.moveToNext()){
            Log.d("------test----",rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.LEERDOEL_NAME)));
            leerdoelen.add(new Leerdoel(rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.LEERDOEL_NAME)), (int)(Math.random()*100)));
        }
    }
}
