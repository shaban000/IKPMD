package shaban.jama.eindopdracht;

import android.database.Cursor;
import android.graphics.Color;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;

import java.util.ArrayList;
import java.util.Comparator;

import shaban.jama.eindopdracht.Adapter.LeerdoelAdapter;
import shaban.jama.eindopdracht.Database.DatabaseHelper;
import shaban.jama.eindopdracht.Database.DatabaseInfo;
import shaban.jama.eindopdracht.Model.Leerdoel;

public class Status extends AppCompatActivity {
    private CombinedChart mChart;
    private ListView listView;
    private ArrayList<Leerdoel> sortleerdoelen;
    private ArrayList<Leerdoel> behaaldLeerdoelen;
    private ArrayList<Leerdoel> todoLeerdoelen;
    private LeerdoelAdapter adapter;
    private TextView textknop;
    private TextView todoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        textknop =  (TextView) findViewById(R.id.textviewknop);
        todoText =  (TextView) findViewById(R.id.todoview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Status");

        listView = (ListView) findViewById(R.id.listLeerdoelen);
        listView.setVisibility(View.GONE);
        listView.setDivider(null);

        mChart = (CombinedChart) findViewById(R.id.chart);

        setVoorbeeldData();
        textknop.setText("U beheert "+behaaldLeerdoelen.size()+" leerdoelen");
        todoText.setText("U moet nog "+todoLeerdoelen.size()+" leerdoelen doen");


        mChart.setDescription("Status per week");           // Deze text moet naar de strings.xml
        mChart.setDescriptionPosition(420,20);
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

        DatabaseHelper dbHelper = DatabaseHelper.getHelper(getApplicationContext());
        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"DISTINCT("+DatabaseInfo.Columns.WEEK+")"}, null, null, null, null, null);
        ArrayList<String> weken = new ArrayList<>();
        while (rs.moveToNext()) {
            weken.add(rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.WEEK)));
        }

        BarData bar = new BarData();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0;i<weken.size();i++) {
            rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"COUNT(*)"},
                    DatabaseInfo.Columns.WEEK+" = "+weken.get(i), null, null, null, null);
            rs.moveToFirst();
            int totaal = rs.getInt(0);

            rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"COUNT(*)"}, "Voldaan = 1 AND "+
                    DatabaseInfo.Columns.WEEK+" = "+weken.get(i), null, null, null, null);
            rs.moveToFirst();
            int aantal = rs.getInt(0);

            barEntries.add(new BarEntry((float) (aantal*100)/totaal, i));
        }
        BarDataSet set = new BarDataSet(barEntries, "Percentage compleet");  // Deze text moet naar de strings.xml
        set.setColor(Color.BLUE);

        bar.addDataSet(set);

        // ADD data to the chart
        String[] xValues = weken.toArray(new String[weken.size()]);
        CombinedData data = new CombinedData(xValues);

        data.setData(bar);
        mChart.setData(data);
        mChart.invalidate();

    }

    public void showlist(View view){
        if (listView.getVisibility() == View.GONE){
            if (behaaldLeerdoelen.size() > 0){
                listView.setVisibility(View.VISIBLE);
                adapter = new LeerdoelAdapter(this,0, behaaldLeerdoelen);
                listView.setAdapter(adapter);
            }
            else {
                Toast.makeText(getApplicationContext(),"U heeft nog geen leerdoelen behaald",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            listView.setVisibility(View.GONE);
            textknop.setText("U beheert "+behaaldLeerdoelen.size()+" leerdoelen");
        }
    }

    public void setVoorbeeldData(){
        DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.databaseTabels.leerdoel, new String[]{"*"}, null, null, null, null, null);
        sortleerdoelen = new ArrayList<>();
        behaaldLeerdoelen = new ArrayList<>();
        todoLeerdoelen = new ArrayList<>();

        while(rs.moveToNext()){
            int id = rs.getInt(rs.getColumnIndex(BaseColumns._ID));
            String naam = rs.getString(rs.getColumnIndex(DatabaseInfo.Columns.LEERDOEL_NAME));
            sortleerdoelen.add(new Leerdoel(id,naam));
        }

        for (Leerdoel leerdoel:sortleerdoelen) {
            rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"COUNT(*)"},
                    DatabaseInfo.Columns.FK_ID_LEERDOEL+" = "+leerdoel.getid(), null, null, null, null);
            rs.moveToFirst();
            int totaal = rs.getInt(0);
            rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{"COUNT(*)"}, "Voldaan = 1 AND "+
                    DatabaseInfo.Columns.FK_ID_LEERDOEL+" = "+leerdoel.getid(), null, null, null, null);
            rs.moveToFirst();
            int aantal = rs.getInt(0);
            leerdoel.setPercentage((aantal*100)/totaal);
            if(leerdoel.getPercentage() == 100){
                behaaldLeerdoelen.add(leerdoel);
            }else {
                todoLeerdoelen.add(leerdoel);
            }
        }

    }
    public void sortLaag(View view){
        adapter = new LeerdoelAdapter(this,0, sortleerdoelen);
        adapter.sort(new Comparator<Leerdoel>() {
            public int compare(Leerdoel arg1, Leerdoel arg0) {
                return arg1.getPercentage() - arg0.getPercentage();
            }
        });
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
        textknop.setText(R.string.Minst);
    }

    public void sortHoog(View view){
        adapter = new LeerdoelAdapter(this,0, sortleerdoelen);
        adapter.sort(new Comparator<Leerdoel>() {
            public int compare(Leerdoel arg0, Leerdoel arg1) {
                return arg1.getPercentage() - arg0.getPercentage();
            }
        });
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
        textknop.setText(R.string.Meest);
    }
    public void todoLeerdoelen(View view){
        if (todoLeerdoelen.size() >0){
            adapter = new LeerdoelAdapter(this,0, todoLeerdoelen);
            adapter.sort(new Comparator<Leerdoel>() {
                public int compare(Leerdoel arg0, Leerdoel arg1) {
                    return arg1.getPercentage() - arg0.getPercentage();
                }
            });
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
            textknop.setText("U moet nog "+todoLeerdoelen.size()+" leerdoelen doen");
        }else {
            Toast.makeText(getApplicationContext(),"U heeft nog geen leerdoelen behaald",Toast.LENGTH_SHORT).show();
        }


    }
}

