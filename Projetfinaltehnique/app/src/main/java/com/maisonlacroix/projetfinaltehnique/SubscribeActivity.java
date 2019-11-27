package com.maisonlacroix.projetfinaltehnique;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;



public class SubscribeActivity extends Activity {

    private String ID_USER;

    private String url = "http://3.15.151.13/Laravel/api/register";
    private EditText Username;
    private EditText Password;
    private EditText ConfirmPassword;
    private EditText nom;
    private EditText prenom;
    private EditText email;
    private Spinner type;
    private EditText telephone;
    private EditText description;
    private EditText adresse;
    private TextView ErreurText_subscribe;
    private Button subscribeButton;
    private Button ReturnToLoginButton;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        subscribeButton = (Button) findViewById(R.id.BTN_Subscribe);
        ReturnToLoginButton = (Button) findViewById(R.id.BTN_ReturnToLogin);
        Username = (EditText) findViewById(R.id.input_username_modifier);
        Username.setText("");
        Password = (EditText) findViewById(R.id.input_password);
        Password.setText("");
        ConfirmPassword = (EditText) findViewById(R.id.input_confirmpassword);
        ConfirmPassword.setText("");
        nom = (EditText) findViewById(R.id.input_lastname);
        nom.setText("");
        prenom = (EditText) findViewById(R.id.input_firstname);
        prenom.setText("");
        email = (EditText) findViewById(R.id.input_email_modifier);
        email.setText("");
        type = (Spinner) findViewById(R.id.spinner_typeuser);
        telephone = (EditText) findViewById(R.id.input_phone);
        telephone.setText("");
        description = (EditText) findViewById(R.id.input_description_modifier);
        description.setText("");
        adresse = (EditText) findViewById(R.id.input_adress);
        adresse.setText("");
        ErreurText_subscribe = (TextView) findViewById(R.id.Textview_error_subscribe);
        ErreurText_subscribe.setText("");
        type.setSelection(0);
    }
    public void RedirectToLogin(View view)
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    public void RedirectToLoginAfterSubscription()
    {
        Toast SubscribeSuccess = Toast.makeText(getApplicationContext(),"subsciption success",Toast.LENGTH_SHORT);
        SubscribeSuccess.show();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    public void Subscribe(View view)
    {

        if (validate())
        {
            final String username = Username.getText().toString();
            final String password = Password.getText().toString();
            final String lastsname = nom.getText().toString();
            final String firstname = prenom.getText().toString();
            final String Email = email.getText().toString();
            final String Phone = telephone.getText().toString();
            final String adresse = this.adresse.getText().toString();
            final String description = this.description.getText().toString();
            final String Type = this.type.getSelectedItem().toString();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
            String strDate = mdformat.format(calendar.getTime());
            final String dateinscription = strDate;

            //Connection
            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(SubscribeActivity.this,"Utilisateur Créé",Toast.LENGTH_LONG).show();
                    RedirectToLoginAfterSubscription();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(error.networkResponse.statusCode == 401)
                            {
                                ErreurText_subscribe.setText("Le nom d'utilisateur est déja utilisé");
                            }
                            else if(error.networkResponse.statusCode == 402)
                            {
                                ErreurText_subscribe.setText("Le email est déja utilisé");
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
                    params.put("name", username);
                    params.put("password", password);
                    params.put("email", Email);
                    params.put("nom", lastsname);
                    params.put("prenom", firstname);
                    params.put("TypeUser", Type);
                    params.put("adresse", adresse);
                    params.put("admin", "0");
                    params.put("confirme", "0");
                    params.put("dateinscription", dateinscription);
                    params.put("telephone", Phone);
                    params.put("description", description);
                    params.put("photo","android.png");
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }

    }


    public boolean validate() {
        boolean valid = true;
        String username = Username.getText().toString();
        String password = Password.getText().toString();
        String ConfimPassword = ConfirmPassword.getText().toString();
        String lastsname = nom.getText().toString();
        String firstname = prenom.getText().toString();
        String Email = email.getText().toString();
        String Phone = telephone.getText().toString();
        String adresse = this.adresse.getText().toString();
        String description = this.description.getText().toString();

        if (username.isEmpty()) {
            ErreurText_subscribe.setText("Nom d'utilisateur vide");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (Email.isEmpty()) {
            ErreurText_subscribe.setText("Email manquant");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (firstname.isEmpty()) {
            ErreurText_subscribe.setText("prénom manquant");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (lastsname.isEmpty()) {
            ErreurText_subscribe.setText("nom de famille manquant");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (Phone.isEmpty())
        {
            ErreurText_subscribe.setText("téléphone manquant");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (adresse.isEmpty()) {
            ErreurText_subscribe.setText("adresse manquante");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (password.isEmpty()) {
            ErreurText_subscribe.setText("Mot de passe vide");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        else if (ConfimPassword.isEmpty()) {
            ErreurText_subscribe.setText("confirmation est vide");
            ErreurText_subscribe.setError("");
            valid = false;
        }
        if (!password.isEmpty() && !ConfimPassword.isEmpty())
        {
            if(!ConfimPassword.equals(password.toString()))
            {
                ErreurText_subscribe.setText("le mot de passe et la confirmation sont différent");
                ErreurText_subscribe.setError("");
                valid = false;
            }
            else
            {
                ErreurText_subscribe.setText(null);
                ErreurText_subscribe.setError(null);
            }
        }
        if (valid)
        {
            ErreurText_subscribe.setText(null);
            ErreurText_subscribe.setError(null);
        }
        return valid;
    }

}
