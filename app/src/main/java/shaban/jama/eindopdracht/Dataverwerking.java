package shaban.jama.eindopdracht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import shaban.jama.eindopdracht.Database.DOA;

public class Dataverwerking extends AppCompatActivity {

    private String gebruikerid;
    private DOA doa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataverwerking);

        doa = new DOA(getApplicationContext());
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

        gebruikerid = id;
    }

    public void opslaan(View view){
        doa.opslaan(gebruikerid);
//        doa.test();
    }

    public void ophalen(View view){
        doa.ophalen();
    }

}
