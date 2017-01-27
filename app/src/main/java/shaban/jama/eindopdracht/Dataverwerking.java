package shaban.jama.eindopdracht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Dataverwerking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataverwerking);
        TextView text = (TextView) findViewById(R.id.errorscherm);

        String id = (String) getIntent().getSerializableExtra("id");
        String naam = (String) getIntent().getSerializableExtra("naam");
        String inlognaam = (String) getIntent().getSerializableExtra("inlognaam");
        String wachtwoord = (String) getIntent().getSerializableExtra("wachtwoord");

        text.setText("");
        text.append("id: "+id +"\n");
        text.append("naam: "+naam +"\n");
        text.append("inlognaam: "+inlognaam +"\n");
        text.append("wachtwoord: "+wachtwoord +"\n");
    }
}
