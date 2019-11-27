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
import java.util.List;
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
    private ListView liste_id;
    private ListView liste_datecreation;
    private ListView liste_fournisseur;

    private List<String> Liste_id  = new ArrayList<>();
    private List<String> Liste_datecreation  = new ArrayList<>();
    private List<String> Liste_Fournisseurs  = new ArrayList<>();

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
        liste_id = findViewById(R.id.Liste_id_commande);
        liste_datecreation = findViewById(R.id.Liste_dateCreation_commandes);
        liste_fournisseur = findViewById(R.id.Liste_Fournisseur_commandes);


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
                    Liste_id.clear();
                    Liste_datecreation.clear();
                    Liste_Fournisseurs.clear();
                    Liste_id.add("id");
                    Liste_datecreation.add("date création");
                    Liste_Fournisseurs.add("Fournisseur");

                    for (int i = 0;i<response.body().length;i++)
                    {
                        if (response.body()[i].getComplete() == Choix.getSelectedItemPosition())
                        {
                            Liste_id.add(response.body()[i].getIdCommande().toString());
                            Liste_datecreation.add(response.body()[i].getDateCreation());
                            Liste_Fournisseurs.add(response.body()[i].getNomFournisseur());
                        }
                    }
                    ArrayList<String> list1 = new ArrayList<>(Liste_id);
                    ArrayList<String> list2 = new ArrayList<>(Liste_datecreation);
                    ArrayList<String> list3 = new ArrayList<>(Liste_Fournisseurs);
                    ArrayAdapter<String> ArrayAdapter1 = new ArrayAdapter<String>(VisualiserCommandesActivity.this, android.R.layout.simple_spinner_item, list1);
                    ArrayAdapter<String> ArrayAdapter2 = new ArrayAdapter<String>(VisualiserCommandesActivity.this, android.R.layout.simple_spinner_item, list2);
                    ArrayAdapter<String> ArrayAdapter3 = new ArrayAdapter<String>(VisualiserCommandesActivity.this, android.R.layout.simple_spinner_item, list3);

                    liste_id.setAdapter(ArrayAdapter1);
                    liste_datecreation.setAdapter(ArrayAdapter2);
                    liste_fournisseur.setAdapter(ArrayAdapter3);

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


}
