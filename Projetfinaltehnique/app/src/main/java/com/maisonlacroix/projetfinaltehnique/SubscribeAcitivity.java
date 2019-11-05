package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SubscribeAcitivity extends Activity {

    private String url = "http://3.15.151.13/Laravel/api/register";
    private EditText Username;
    private EditText Password;
    private EditText nom;
    private EditText prenom;
    private EditText email;
    private Spinner type;
    private EditText telephone;
    private EditText description;
    private TextView ErreurText;
    private Button subscribeButton;
    private Button loginButton;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        loginButton = (Button) findViewById(R.id.btn_signup);
        Username = (EditText) findViewById(R.id.input_username);
        Password = (EditText) findViewById(R.id.input_adresse);
        ErreurText = (TextView) findViewById(R.id.Error);
    }


}
