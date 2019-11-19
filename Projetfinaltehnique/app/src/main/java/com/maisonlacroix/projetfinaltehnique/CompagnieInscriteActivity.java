package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maisonlacroix.projetfinaltehnique.Classes.Access_Token;
import com.maisonlacroix.projetfinaltehnique.Classes.User;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class CompagnieInscriteActivity extends AppCompatActivity {


    private String ID_USER;
    //service API
    ApiService service;
    Call<User> Token;

    Map<Integer, String> Liste_Commandes = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compagnie_inscrite);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");
        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);
        GetAllUsers();
    }

    private void GetAllUsers()
    {
        Token = service.GetAllUsers();
        //requete de login
        Token.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(CompagnieInscriteActivity.this,response.body().getIduser().toString(),Toast.LENGTH_LONG).show();
                } else {
                    Log.e("request error : ", response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
