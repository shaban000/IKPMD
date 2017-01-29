package shaban.jama.eindopdracht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registeer extends AppCompatActivity {
    Button registeerButton;
    TextView naam;
    TextView inlognaam;
    TextView wachtwoord;
    TextView errorfield;
    RequestQueue requestQueue;
    String insertUrl = "http://192.168.0.106/IKPMD/insertUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);


        registeerButton = (Button) findViewById(R.id.registeer_button);
        naam = (TextView) findViewById(R.id.registeer_naam);
        inlognaam = (TextView) findViewById(R.id.registeer_inlognaam);
        wachtwoord = (TextView) findViewById(R.id.registeer_wachtwoord);
        errorfield = (TextView) findViewById(R.id.registeer_errorfield);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        registeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        if (response.contains("Duplicate")){
                            Toast.makeText(getApplicationContext(),"Gebruikernaam bestaat al in onze database",Toast.LENGTH_LONG).show();
                            errorfield.setText("");
                            errorfield.append("Kies een andere gebruikersnaam!!!\n\n");
                            errorfield.append("Error beschrijving\n");
                            errorfield.append("-------------\n");
                            errorfield.append(response.toString().replace(naam.getText().toString()+inlognaam.getText().toString()+wachtwoord.getText().toString(),""));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Gebruiker: "+ inlognaam.getText()+" is aangemaakt",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", String.valueOf(error));
                        Log.d("testerror","error");
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("naam",naam.getText().toString());
                        Log.d("naam",naam.getText().toString());
                        params.put("inlognaam",inlognaam.getText().toString());
                        Log.d("inlognaam",inlognaam.getText().toString());
                        params.put("wachtwoord",wachtwoord.getText().toString());
                        Log.d("wachtwoord",wachtwoord.getText().toString());
                        return params;
                    }
                }; requestQueue.add(stringRequest);
            }
        });
    }
}
