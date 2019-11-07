package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import static com.android.volley.Request.Method.POST;

public class VisualiserCommandesActivity extends Activity {

    private Spinner Choix;
    private String urlGetComandes = "http://3.15.151.13/Laravel/api/GetCommandeDistributeur";
    private RequestQueue queue;
    private TextView ErreurText_VisualiserCommande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser_commandes);
        ErreurText_VisualiserCommande = (TextView) findViewById(R.id.ErreurText_VisualiserCommande);
        Choix = (Spinner) findViewById(R.id.spinner_type_commade);
        Choix.setSelection(0);
    }
    public void GetCommandes() {

        //Connection
        JsonObjectRequest jsonRequest = new JsonObjectRequest(POST,  new Response.Listener<JSONObject>()
        {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            JSONObject jsonInnerObject;

                            for (int k = 0; k < jsonArray.length(); k++) {

                                jsonInnerObject = new JSONObject(jsonArray.get(k).toString());

                                String site = jsonInnerObject.getString("first_name"),
                                        network = jsonInnerObject.getString("last_name");
                                System.out.println("firstname: " + site + "\nLastname: " + network);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

}
