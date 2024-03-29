package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maisonlacroix.projetfinaltehnique.Classes.Access_Token;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends Activity implements Serializable {

    private EditText Username;
    private EditText Password;
    private TextView ErreurText;
    private Button loginButton;
    private RequestQueue queue;
    private CheckBox Sesouvenir;

    ApiService service;
    Call<Access_Token> Token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.btn_signup);
        Username = (EditText) findViewById(R.id.input_username_modifier);
        Password = (EditText) findViewById(R.id.input_adresse);
        ErreurText = (TextView) findViewById(R.id.Error);
        Sesouvenir = (CheckBox) findViewById(R.id.ch_rememberme);

        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);

        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("user_name", "No name defined");//"No name defined" is the default value.
        String pass = prefs.getString("password", "No name defined");//"No name defined" is the default value.
        //Se souvenir de moi
        if (!name.equals("No name defined"))
        {
            Sesouvenir.setChecked(true);
            Username.setText(name);
            Password.setText(pass);
        }
    }

    public void LOGIN(View view)
    {
        final Intent i = new Intent(this, MainActivity.class);
        Token = service.login(Username.getText().toString(),Password.getText().toString());
        //requete de login
        Token.enqueue(new Callback<Access_Token>() {
            @Override
            public void onResponse(Call<Access_Token> call, retrofit2.Response<Access_Token> response) {
                if(response.isSuccessful())
                {
                    final String MY_PREFS_NAME = "MyPrefsFile";
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    if(Sesouvenir.isChecked())
                    {
                        editor.putString("user_name", response.body().getNomutilisateur());
                        editor.putString("password",Password.getText().toString());
                        editor.apply();
                    }
                    else
                    {
                        editor.clear();
                    }
                    //todo
                    //Toast.makeText(LoginActivity.this,response.body().getNomutilisateur(),Toast.LENGTH_SHORT).show();
                    i.putExtra("key1",response.body().getIduser().toString());
                    i.putExtra("key2",response.body().getTypeUser());
                    startActivity(i);
                } else {
                    Log.e("login error : ", response.errorBody().toString());

                    if(response.code() == 400)
                    {
                        Log.e("login error : ", "username invalid");
                    }
                }
            }
            @Override
            public void onFailure(Call<Access_Token> call, Throwable t) {
                Log.e("login error : ",t.getMessage());
            }
        });
    }

    public void Connection(View vue) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
        if (validate()) {
            final String user = Username.getText().toString();
            final String password = Password.getText().toString();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Connection...");
            progressDialog.show();

            //Connection
            StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST,
                "http://3.15.151.13/Laravel/api/",
                response -> {
                    if (Sesouvenir.isChecked()) {
                        //Se souvenir de moi
                        SharedPreferences reglages = getPreferences(0);
                        SharedPreferences.Editor editeur = reglages.edit();
                        editeur.putString("user", response);
                        editeur.commit();
                    }

                },
                error -> {
                    if (error.networkResponse.statusCode == 401) {
                        ErreurText.setText("Les informations entrées sont invalides");
                    } else if (error.networkResponse.statusCode == 402) {
                        ErreurText.setText("Votre compte n'est pas encore validé");
                    }
                }
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", user);
                    params.put("password", password);
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }
        progressDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;
        String user = Username.getText().toString();
        String password = Password.getText().toString();
        if (user.isEmpty()) {
            ErreurText.setText("Nom d'utilisateur incorrect");
            ErreurText.setError("");
            valid = false;
        } else {
            ErreurText.setText(null);
            ErreurText.setError(null);
        }

        if (password.isEmpty()) {
            ErreurText.setText("Mot de passe incorrect");
            ErreurText.setError("");
            valid = false;
        } else {
            ErreurText.setText(null);
            ErreurText.setError(null);
        }
        return valid;
    }
    public void RedirectToSubscribe(View v)
    {
        Intent i = new Intent(this, SubscribeActivity.class);
        startActivity(i);
    }
}