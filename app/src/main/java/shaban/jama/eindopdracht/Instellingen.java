package shaban.jama.eindopdracht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Instellingen extends AppCompatActivity {
    private static String ip = "192.168.0.106";
    private EditText textIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instellingen);
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


    private void setIp(String ip) {
        Instellingen.ip = ip;
    }

    public static String getIp() {
        return ip;
    }
}

