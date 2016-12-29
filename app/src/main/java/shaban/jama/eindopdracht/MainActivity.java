package shaban.jama.eindopdracht;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("Status");
        mChart.setTouchEnabled(true);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(2000, Easing.EasingOption.EaseInQuart);

        setdata(); // tijdelijke voorbeeld data.
    }

    private void setdata() {
        int aantal = 80;
        int currentEcts = aantal;
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal, 0));
        xValues.add(getResources().getString(R.string.Gedaan));

        yValues.add(new Entry(100 - currentEcts, 1));
        xValues.add(getResources().getString(R.string.Tedoen));

        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.rgb(244,81,30));
        } else if (currentEcts < 40){
            colors.add(Color.rgb(235,0,0));
        } else if  (currentEcts < 50) {
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

    public void Status(View view){
        startActivity(new Intent(getApplicationContext(),Status.class));
    }

    public void Leren(View view){
        startActivity(new Intent(getApplicationContext(),Leren.class));
    }

    public void Toevoegen(View view){
        startActivity(new Intent(getApplicationContext(),Toevoegen.class));
    }

    public void Instellingen(View view){
        startActivity(new Intent(getApplicationContext(),Toevoegen.class)); // View moet nog gemaakt worden.
    }

}
