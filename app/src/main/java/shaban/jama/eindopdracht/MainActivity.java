package shaban.jama.eindopdracht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton leren;
    ImageButton status;
    ImageButton toevoegen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leren = (ImageButton)findViewById(R.id.leren_btn);
        status = (ImageButton)findViewById(R.id.status_btn);
        toevoegen = (ImageButton)findViewById(R.id.toevoegen_btn);

        leren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Leren.class);
                startActivity(intent);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Status.class);
                startActivity(intent);
            }
        });
        toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Toevoegen.class);
                startActivity(intent);
            }
        });
    }
}
