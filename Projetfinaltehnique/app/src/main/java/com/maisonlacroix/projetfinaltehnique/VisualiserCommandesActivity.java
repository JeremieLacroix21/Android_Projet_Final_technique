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
import com.maisonlacroix.projetfinaltehnique.Classes.Commandes;

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
    private Commandes Lescommandes[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser_commandes);
        ErreurText_VisualiserCommande = (TextView) findViewById(R.id.ErreurText_VisualiserCommande);
        Choix = (Spinner) findViewById(R.id.spinner_type_commade);
        Choix.setSelection(0);

    }


    public void GetCommandes(View view) {
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST, urlGetComandes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(VisualiserCommandesActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0;i<jsonResponse.length();i++)
                    {
                        //Lescommandes[i] = (Commandes) jsonResponse.getJSONArray(i).getJSONObject(i).getJSONObject();
                        //Toast.makeText(VisualiserCommandesActivity.this,Lescommandes.toString(),Toast.LENGTH_LONG).show();
                    }

                }catch (JSONException e) {
                    ErreurText_VisualiserCommande.setText(e.getMessage().toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErreurText_VisualiserCommande.setText(error.toString());
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idDistributeur", "88");
                return params;
            }
        };
        queue.add(jsonObjRequest);

    }

}
