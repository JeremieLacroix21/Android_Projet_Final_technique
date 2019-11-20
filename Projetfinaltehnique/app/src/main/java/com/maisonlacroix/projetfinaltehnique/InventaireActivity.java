package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maisonlacroix.projetfinaltehnique.Classes.ProduitInventaire;
import com.maisonlacroix.projetfinaltehnique.Classes.User;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class InventaireActivity extends AppCompatActivity {
    private String ID_USER;
    //service API
    ApiService service;
    Call<ProduitInventaire[]> Token;

    private List<String> Liste_inventaire_nom = new ArrayList<>();
    private List<String> Liste_inventaire_prix = new ArrayList<>();
    private List<String> Liste_inventaire_qte = new ArrayList<>();
    private List<String> Liste_inventaire_desc = new ArrayList<>();
    private ListView Liste_nom;
    private ListView Liste_prix;
    private ListView Liste_qte;
    private ListView Liste_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");

        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);

        Liste_nom = (ListView) findViewById(R.id.Liste_nom_inventaire);
        Liste_prix = (ListView) findViewById(R.id.Liste_prix_inventaire);
        Liste_qte = (ListView) findViewById(R.id.Liste_qty_inventaire);
        Liste_desc = (ListView) findViewById(R.id.Liste_description_inventaire);
        GetAllItems();

    }

    private void GetAllItems()
    {
        Token = service.GetProductsByFournisseur("84");

        //requete de login
        Token.enqueue(new Callback<ProduitInventaire[]>() {
            @Override
            public void onResponse(Call<ProduitInventaire[]> call, retrofit2.Response<ProduitInventaire[]> response) {
                if(response.isSuccessful())
                {
                    Liste_inventaire_nom.add("Nom");
                    Liste_inventaire_prix.add("prix");
                    Liste_inventaire_qte.add("qty");
                    Liste_inventaire_desc.add("description");
                    for (int i = 1;i<response.body().length;i++)
                    {
                        Liste_inventaire_nom.add(response.body()[i].getNom());
                        Liste_inventaire_prix.add(response.body()[i].getPrix().toString());
                        Liste_inventaire_qte.add(response.body()[i].getEnStock().toString());
                        Liste_inventaire_desc.add(response.body()[i].getDescription());
                    }
                    ArrayList<String> list_nom = new ArrayList<>(Liste_inventaire_nom);
                    ArrayList<String> liste_prix = new ArrayList<>(Liste_inventaire_prix);
                    ArrayList<String> liste_qte = new ArrayList<>(Liste_inventaire_qte);
                    ArrayList<String> liste_desc = new ArrayList<>(Liste_inventaire_desc);
                    ArrayAdapter<String> ArrayAdapter1 = new ArrayAdapter<>(InventaireActivity.this, android.R.layout.simple_spinner_item, list_nom);
                    ArrayAdapter<String> ArrayAdapter2 = new ArrayAdapter<>(InventaireActivity.this, android.R.layout.simple_spinner_item, liste_prix);
                    ArrayAdapter<String> ArrayAdapter3 = new ArrayAdapter<>(InventaireActivity.this, android.R.layout.simple_spinner_item, liste_qte);
                    ArrayAdapter<String> ArrayAdapter4 = new ArrayAdapter<>(InventaireActivity.this, android.R.layout.simple_spinner_item, liste_desc);
                    Liste_nom.setAdapter(ArrayAdapter1);
                    Liste_prix.setAdapter(ArrayAdapter2);
                    Liste_qte.setAdapter(ArrayAdapter3);
                    Liste_desc.setAdapter(ArrayAdapter4);
                } else {
                    Log.e("request error : ", response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<ProduitInventaire[]> call, Throwable t) {
                Log.e("login error : ",t.getMessage());
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
