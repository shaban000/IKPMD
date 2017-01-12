package shaban.jama.eindopdracht;

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
        Log.d("test", String.valueOf(2));
        if (listView.getVisibility() == View.GONE){
            listView.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.GONE);
        }
    }

    public void setVoorbeeldData(){

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        leerdoelen = new ArrayList<>();
        for (String s: values){
            leerdoelen.add(new Leerdoel(s, (int)(Math.random()*100))) ;
        }



    }

}
