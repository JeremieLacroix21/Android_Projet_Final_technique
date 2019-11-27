package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maisonlacroix.projetfinaltehnique.Classes.User;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CompagnieInscriteActivity extends AppCompatActivity {


    private String ID_USER;
    //service API
    ApiService service;
    Call<User[]> Token;

    private List<String> Liste_Compagnie_nom = new ArrayList<>();
    private List<String> Liste_Compagnie_email = new ArrayList<>();
    private List<String> Liste_Compagnie_type = new ArrayList<>();
    private ListView Liste_nom;
    private ListView Liste_email;
    private ListView Liste_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compagnie_inscrite);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");
        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);
        Liste_nom = (ListView) findViewById(R.id.Liste_nom);
        Liste_email = (ListView) findViewById(R.id.Liste_email);
        Liste_type = (ListView) findViewById(R.id.Liste_type);
        GetAllUsers();
    }

    private void GetAllUsers()
    {
        Token = service.GetAllUsers();

        //requete de login
        Token.enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, retrofit2.Response<User[]> response) {
                if(response.isSuccessful())
                {
                    Liste_Compagnie_nom.add("Nom");
                    Liste_Compagnie_email.add("Email");
                    Liste_Compagnie_type.add("Type");
                    for (int i = 1;i<response.body().length;i++)
                    {
                        Liste_Compagnie_nom.add(response.body()[i].getNomutilisateur());
                        Liste_Compagnie_email.add(response.body()[i].getEmail());
                        Liste_Compagnie_type.add(response.body()[i].getTypeUser());
                    }
                    ArrayList<String> list_nom = new ArrayList<>(Liste_Compagnie_nom);
                    ArrayList<String> list_email = new ArrayList<>(Liste_Compagnie_email);
                    ArrayList<String> list_type = new ArrayList<>(Liste_Compagnie_type);
                    ArrayAdapter<String> ArrayAdapter1 = new ArrayAdapter<>(CompagnieInscriteActivity.this, android.R.layout.simple_spinner_item, list_nom);
                    ArrayAdapter<String> ArrayAdapter2 = new ArrayAdapter<>(CompagnieInscriteActivity.this, android.R.layout.simple_spinner_item, list_email);
                    ArrayAdapter<String> ArrayAdapter3 = new ArrayAdapter<>(CompagnieInscriteActivity.this, android.R.layout.simple_spinner_item, list_type);
                    Liste_nom.setAdapter(ArrayAdapter1);
                    Liste_email.setAdapter(ArrayAdapter2);
                    Liste_type.setAdapter(ArrayAdapter3);
                } else {
                    Log.e("request error : ", response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Log.e("login error : ",t.getMessage());
            }
        });
    }

}
