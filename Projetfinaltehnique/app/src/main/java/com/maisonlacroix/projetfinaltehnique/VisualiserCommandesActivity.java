package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.maisonlacroix.projetfinaltehnique.Classes.Commande;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;


public class VisualiserCommandesActivity extends Activity {


    //service API
    ApiService service;
    Call<Commande[]> Token;
    private String ID_USER;

    Map<Integer, String> Liste_Commandes = new HashMap<>();

    private Spinner Choix;
    private RequestQueue queue;
    private ListView TableauDeCommande;
    private TextView ErreurText_VisualiserCommande;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser_commandes);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");

        ErreurText_VisualiserCommande = (TextView) findViewById(R.id.ErreurText_VisualiserCommande);
        Choix = (Spinner) findViewById(R.id.spinner_type_commade);
        Choix.setSelection(0);
        TableauDeCommande = findViewById(R.id.Liste_commandes);
        service = RetrofitBuilder.createService(ApiService.class);



        Choix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                GetCommandes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void GetCommandes() {
        Token = service.GetCommandeDistributeur(ID_USER);
        //requete de login
        Token.enqueue(new Callback<Commande[]>() {
            @Override
            public void onResponse(Call<Commande[]> call, retrofit2.Response<Commande[]> response) {
                if(response.isSuccessful())
                {
                    //todo

                    Liste_Commandes.clear();
                    Liste_Commandes.put(1,
                            "id                       " +
                            "dateCreation                     " +
                            "Fournisseur          ");
                    for (int i = 0;i<response.body().length;i++)
                    {
                        if (response.body()[i].getComplete() == Choix.getSelectedItemPosition())
                        {
                            Liste_Commandes.put(response.body()[i].getIdCommande(),response.body()[i].getIdCommande()+ "                   " +  response.body()[i].getDateCreation() + "      " + response.body()[i].getNomFournisseur()+ "      ");
                        }
                    }
                    ArrayList<String> list = new ArrayList<>(Liste_Commandes.values());
                    ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(VisualiserCommandesActivity.this, android.R.layout.simple_spinner_item, list);

                    TableauDeCommande.setAdapter(ArrayAdapter);

                } else {
                    Log.e("request error : ", response.errorBody().toString());

                    if(response.code() == 400)
                    {
                        Log.e("request error : ", "username invalid");
                    }
                }
            }
            @Override
            public void onFailure(Call<Commande[]> call, Throwable t) {
                Log.e("request error : ",t.getMessage());
            }
        });
    }

    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }
}
