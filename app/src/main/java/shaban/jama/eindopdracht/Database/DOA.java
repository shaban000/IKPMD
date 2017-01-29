package shaban.jama.eindopdracht.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shaban.jama.eindopdracht.Dataverwerking;
import shaban.jama.eindopdracht.Instellingen;

/**
 * Created by Shaban on 27-01-17.
 */

public class DOA {

    private String ip = Instellingen.getIp();
    private String url ="http://"+ip+"/IKPMD/";
    private DatabaseHelper dbHelper;
    private Cursor rs ;
    private RequestQueue requestQueue;
    private String klantID;
    private String subdoelNaam;
    private String leerdoelID;
    private String weeknummer;
    private ArrayList<String> subdoelIDs;


    public DOA(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        dbHelper = DatabaseHelper.getHelper(context);
    }

    public void getLeerdoelen(){
        String getUrl = url+"showLeerdoelen.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    vulLeerdoeleninDatabase(response.getJSONArray("Leerdoelen"));
                }catch(Exception e)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("----error---",String.valueOf(error));
            }
        }); requestQueue.add(jsonObjectRequest);
    }

    private void vulLeerdoeleninDatabase(JSONArray leerdoelen){
        int huidige = dbHelper.countTabel(DatabaseInfo.databaseTabels.leerdoel);
        if(huidige < leerdoelen.length()) {
            for (int i = 0; i < leerdoelen.length(); i++) {
                try {
                    JSONObject leerdoel = leerdoelen.getJSONObject(i);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseInfo.Columns.LEERDOEL_NAME, leerdoel.getString("naam"));
                    dbHelper.insert(DatabaseInfo.databaseTabels.leerdoel, null, values);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getSubdoelen(){
        String getUrl = url+"showSubdoelen.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    vulsubdoeleninDatabase(response.getJSONArray("Subdoelen"));

                }catch(Exception e)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("----error---",String.valueOf(error));
            }
        }); requestQueue.add(jsonObjectRequest);
    }

    private void vulsubdoeleninDatabase(JSONArray subdoelen){
        subdoelIDs = new ArrayList<>();
        int huidige = dbHelper.countTabel(DatabaseInfo.databaseTabels.subdoel);

        rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{BaseColumns._ID},null, null, null, null, null);
        while (rs.moveToNext()) {
            subdoelIDs.add(rs.getString(rs.getColumnIndex(BaseColumns._ID)));
        }

        if(huidige < subdoelen.length()) {
            for (int i = 0; i < subdoelen.length(); i++) {
                try {
                    JSONObject subdoel = subdoelen.getJSONObject(i);
                    if (!subdoelIDs.contains(subdoel.getString("id"))){
                        ContentValues values = new ContentValues();
                        values.put(BaseColumns._ID, subdoel.getString("id"));
                        values.put(DatabaseInfo.Columns.SUBDOEL_NAME, subdoel.getString("naam"));
                        values.put(DatabaseInfo.Columns.WEEK, subdoel.getString("week"));
                        values.put(DatabaseInfo.Columns.FK_ID_LEERDOEL, subdoel.getString("leerdoel_id"));
                        values.put(DatabaseInfo.Columns.VOLDAAN, 0);
                        dbHelper.insert(DatabaseInfo.databaseTabels.subdoel, null, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public void opslaan(String klantID) {
        this.klantID = klantID;
        subdoelIDs = new ArrayList<>();
        rs = dbHelper.query(DatabaseInfo.databaseTabels.subdoel, new String[]{BaseColumns._ID}, "Voldaan = 1", null, null, null, null);
        while (rs.moveToNext()) {
            subdoelIDs.add(rs.getString(rs.getColumnIndex(BaseColumns._ID)));
        }
        opslaanOpServer();
    }

    public void opslaanOpServer(){
        String getUrl = url+"insertKlantSubdoel.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("----error array----",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klantID", getKlantID());
                ArrayList<String> idArray = getSubdoelIDs();
                for (int i = 0; i <idArray.size(); i++) {
                    params.put("ID"+(i),idArray.get(i));
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ophalen() {
        String getUrl = url+"getKlantSubdoelen.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    activeerSubdoelen(response.getJSONArray("KlantSubdoel"));
                }catch(Exception e)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("----error---",String.valueOf(error));
            }
        }); requestQueue.add(jsonObjectRequest);
    }

    public void activeerSubdoelen(JSONArray subdoelen){
        for (int i = 0; i < subdoelen.length(); i++) {
            try {
                JSONObject subdoel = subdoelen.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.Columns.VOLDAAN, Boolean.TRUE);
                String where = "_id = ?";
                String whereArgs [] = new String [] {subdoel.getString("subdoel_id")};
                dbHelper.update(DatabaseInfo.databaseTabels.subdoel,values, where, whereArgs );
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void addSubdoel(String subdoelNaam, String leerdoelID, String weeknummer){
        this.subdoelNaam = subdoelNaam;
        this.leerdoelID = leerdoelID;
        this.weeknummer = weeknummer;

        String getUrl = url+"insertSubdoel.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("--subdoel toevoegen",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("----error array----",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("subdoelNaam", getSubdoelNaam());
                params.put("leerdoelID", getLeerdoelID());
                params.put("week", getWeeknummer());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String getKlantID() {
        return klantID;
    }

    public String getSubdoelNaam() {
        return subdoelNaam;
    }

    public String getLeerdoelID() {
        return leerdoelID;
    }

    public String getWeeknummer() {
        return weeknummer;
    }

    public ArrayList<String> getSubdoelIDs() {
        return subdoelIDs;
    }

}
