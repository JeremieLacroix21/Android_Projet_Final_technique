package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.maisonlacroix.projetfinaltehnique.Classes.Fournisseur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListeFournisseur extends AppCompatActivity {

    private String ID_USER;

    private String url = "http://3.15.151.13/Laravel/api/GetAllSuppliers";
    private RequestQueue queue;
    private ArrayList<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
    public Spinner Sp_fournisseur;
    public TextView TV_nom;
    public TextView TV_etoile;
    public TextView TV_courriel;
    public TextView TV_adresse;
    public TextView TV_telephone;
    public TextView TV_description;
    public TextView TV_tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_fournisseur);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");

        Sp_fournisseur = (Spinner)findViewById(R.id.spinner_fournisseur);
        TV_nom = (TextView)findViewById(R.id.TV_nom);
        TV_etoile = (TextView)findViewById(R.id.TV_etoile);
        TV_courriel = (TextView)findViewById(R.id.TV_courriel);
        TV_adresse = (TextView)findViewById(R.id.TV_adresse);
        TV_telephone = (TextView)findViewById(R.id.TV_telephone);
        TV_description = (TextView)findViewById(R.id.TV_description);
        TV_tags = (TextView)findViewById(R.id.TV_tags);

        GetFournisseur();
    }

    public void GetFournisseur() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                fournisseurs = new Gson().fromJson(response.toString(), new TypeToken<List<Fournisseur>>(){}.getType());
                LoadSpinner(fournisseurs);
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ListeFournisseur.this,"error",Toast.LENGTH_LONG).show();
                }
            })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(jsonObjRequest);
    }

    void LoadSpinner(ArrayList<Fournisseur> fournisseur){
        final ArrayList<Fournisseur> fourni = fournisseur;
        List<String> list;
        list = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        for( Fournisseur oneItem : fournisseur ) {
            list.add(oneItem.nomutilisateur);
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp_fournisseur.setAdapter(adapter);

        Sp_fournisseur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListeFournisseur.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                for( Fournisseur oneItem : fourni ) {
                    if(oneItem.nomutilisateur == parent.getItemAtPosition(position).toString()){
                        TV_nom.setText("Nom: "+oneItem.nom);
                        TV_adresse.setText("Adresse: "+oneItem.adresse);
                        TV_courriel.setText("Courriel: "+oneItem.email);
                        TV_telephone.setText("Telephone: "+oneItem.Telephone);
                        TV_description.setText("description: "+oneItem.email);
                        TV_etoile.setText("Etoile: "+oneItem.NbEtoiles);
                        String str = "";
                        for (String tag:oneItem.tags) {
                            str += tag + ",";
                        }
                        TV_tags.setText("tags: " + str);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }
    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

}
