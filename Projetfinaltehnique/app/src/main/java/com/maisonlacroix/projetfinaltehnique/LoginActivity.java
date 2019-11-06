package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private String url = "http://3.15.151.13/Laravel/api/login";
    private EditText Username;
    private EditText Password;
    private TextView ErreurText;
    private Button loginButton;
    private RequestQueue queue;
    private CheckBox Sesouvenir;

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
    }

    public void Connection(View vue) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        if (validate()) {
            final String user = Username.getText().toString();
            final String password = Password.getText().toString();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Connection...");
            progressDialog.show();
            //Connection
            StringRequest jsonObjRequest = new StringRequest(
                    Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(Sesouvenir.isChecked())
                    {
                        //Se souvenir de moi
                        SharedPreferences reglages = getPreferences(0);
                        SharedPreferences.Editor editeur = reglages.edit();
                        editeur.putString("user", response);
                        editeur.commit();
                    }
                    Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error.networkResponse.statusCode == 401)
                            {
                                ErreurText.setText("Les informations entrées sont invalides");
                            }
                            else if(error.networkResponse.statusCode == 402)
                            {
                                ErreurText.setText("Votre compte n'est pas encore validé");
                            }
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", user);
                    params.put("password", password);
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }
        progressDialog.dismiss();
    }

    public void RedirectToSubscribe(View view){
        Intent i = new Intent(this, SubscribeAcitivity.class);
        startActivity(i);
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
}