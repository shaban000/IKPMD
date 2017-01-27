package shaban.jama.eindopdracht;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button registeerButton;
    Button inlogButton;
    TextView inlognaam;
    TextView wachtwoord;
    TextView alleusers;
    RequestQueue requestQueue;
    String getUrl = "http://145.101.74.227/IKPMD/getUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registeerButton = (Button) findViewById(R.id.registreerbutton);
        inlogButton = (Button) findViewById(R.id.inlogbutton);
        inlognaam = (TextView) findViewById(R.id.inlog_inlognaam);
        wachtwoord = (TextView) findViewById(R.id.inlog_wachtwoord);
        alleusers = (TextView) findViewById(R.id.inlog_users);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void login(View view){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray users = jsonObject.getJSONArray("gebruiker");

                    JSONObject user = users.getJSONObject(0);

                    Intent i = new Intent(getApplicationContext(),Dataverwerking.class);
                    i.putExtra("id",user.getString("id"));
                    i.putExtra("naam",user.getString("naam"));
                    i.putExtra("inlognaam",user.getString("inlognaam"));
                    i.putExtra("wachtwoord",user.getString("wachtwoord"));
                    startActivity(i);

                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Verkeerde inlognaam of wachtwoord",Toast.LENGTH_SHORT).show();
                    alleusers.setText("");
                    alleusers.append("De combinatie van inlognaam en wachtwoord bestaat niet on onze database. \n\n");
                    alleusers.append("Als u nog geen account heeft, kunt u zich registreren door op de knop 'registreren' te drukken");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alleusers.setText("Error: \n\n" + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("inlognaam",inlognaam.getText().toString());
                params.put("wachtwoord",wachtwoord.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void registreer(View view){
        startActivity(new Intent(getApplicationContext(),registeer.class));
    }
}