package com.maisonlacroix.projetfinaltehnique;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifierProfilActivity extends Activity {

    private String ID_USER;


    private String urlGetinfo = "http://3.15.151.13/Laravel/api/GetUserInformation";
    private String urlUpdateinfo = "http://3.15.151.13/Laravel/api/UpdateUser";
    private EditText Username;
    private EditText Email;
    private EditText Phone;
    private EditText Description;
    private TextView ErreurText_Modifier;
    private Button BTN_modifier;
    private RequestQueue queue;
    private String username_json;
    private String Email_json;
    private String Phone_json;
    private String Description_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");



        BTN_modifier = (Button) findViewById(R.id.BTN_modifier);
        Username = (EditText) findViewById(R.id.input_username_modifier);
        Email = (EditText) findViewById(R.id.input_email_modifier);
        Phone = (EditText) findViewById(R.id.input_phone_modifier);
        Description = (EditText) findViewById(R.id.input_description_modifier);
        ErreurText_Modifier = (TextView) findViewById(R.id.Textview_error_modifier);
        GetUserInfo();
    }
    public void GetUserInfo() {

            //Connection
            StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST, urlGetinfo, response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        username_json = jsonResponse.getString("nomutilisateur");
                        Email_json  = jsonResponse.getString("email");
                        Phone_json = jsonResponse.getString("Telephone");
                        Description_json = jsonResponse.getString("description");
                        Username.setText(username_json);
                        Email.setText(Email_json);
                        Phone.setText(Phone_json);
                        Description.setText(Description_json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> ErreurText_Modifier.setText(error.toString())
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("iduser", ID_USER);
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }

    public void ModifierProfil(View view) {
            //Connection
            StringRequest jsonObjRequest = new StringRequest(
                    Request.Method.POST,
                    urlUpdateinfo,
                    response -> Toast.makeText(ModifierProfilActivity.this,"Profile modifié",Toast.LENGTH_LONG).show(),
                    error -> ErreurText_Modifier.setText(error.toString())
            ) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("iduser", ID_USER);
                    params.put("nomutilisateur", Username.getText().toString());
                    params.put("email", Email.getText().toString());
                    params.put("description", Description.getText().toString());
                    params.put("Telephone", Phone.getText().toString());
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }

    public boolean validate() {
        boolean valid = true;
        String username = Username.getText().toString();
        String Email = this.Email.getText().toString();
        String Phone = this.Phone.getText().toString();
        String Description = this.Description.getText().toString();

        if (username.isEmpty()) {
            ErreurText_Modifier.setText("Nom d'utilisateur vide");
            ErreurText_Modifier.setError("");
            valid = false;
        }
        else if (Email.isEmpty()) {
            ErreurText_Modifier.setText("Email vide");
            ErreurText_Modifier.setError("");
            valid = false;
        }
        else if (Phone.isEmpty()) {
            ErreurText_Modifier.setText("téléphone vide");
            ErreurText_Modifier.setError("");
            valid = false;
        }
        if (valid)
        {
            ErreurText_Modifier.setText(null);
            ErreurText_Modifier.setError(null);
        }
        return valid;
    }
}