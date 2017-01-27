package shaban.jama.eindopdracht.Database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shaban on 27-01-17.
 */

public class DOA {

    private String ip = "145.101.74.227";
    private String url ="http://"+ip+"/IKPMD/";
    private DatabaseHelper dbHelper;
    RequestQueue requestQueue;


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
                    Log.d("----leerdoelen---","onresponse");
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
        int huidige = dbHelper.CountTabel(DatabaseInfo.databaseTabels.leerdoel);
        Log.d("----leerdoelen---","aantal in tabel: "+huidige);
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
                    Log.d("----subdoelen---","onresponse");
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
        int huidige = dbHelper.CountTabel(DatabaseInfo.databaseTabels.subdoel);
        Log.d("----subdoelen---","aantal in tabel: "+huidige);
        if(huidige < subdoelen.length()) {
            for (int i = 0; i < subdoelen.length(); i++) {
                try {
                    JSONObject subdoel = subdoelen.getJSONObject(i);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseInfo.Columns.SUBDOEL_NAME, subdoel.getString("naam"));
                    values.put(DatabaseInfo.Columns.WEEK, subdoel.getString("week"));
                    values.put(DatabaseInfo.Columns.FK_ID_LEERDOEL, subdoel.getString("leerdoel_id"));
                    dbHelper.insert(DatabaseInfo.databaseTabels.subdoel, null, values);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
