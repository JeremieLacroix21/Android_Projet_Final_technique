package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends Activity {

    private String url = "http://3.15.151.13";
    private EditText Username;
    private EditText Password;
    private TextView ErreurText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.btn_signup);
        Username = (EditText) findViewById(R.id.input_username);
        Password = (EditText) findViewById(R.id.input_password);
        ErreurText = (TextView) findViewById(R.id.Error);
    }

    public void Connection(View vue) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        RequestQueue MyRequestQueue = null;
        StringRequest MyStringRequest = null;
        if (validate()) {
            final String user = Username.getText().toString();
            final String password = Password.getText().toString();
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Connection...");
            progressDialog.show();
            //Connection
            MyRequestQueue = Volley.newRequestQueue(this);
            MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("","youpi");
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErreurText.setText(error.toString());
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("nomutilisateur", user);//Add the data you'd like to send to the server.
                    MyData.put("motdepasse", password);
                    return MyData;
                }
            };
            MyRequestQueue.add(MyStringRequest);
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
}
