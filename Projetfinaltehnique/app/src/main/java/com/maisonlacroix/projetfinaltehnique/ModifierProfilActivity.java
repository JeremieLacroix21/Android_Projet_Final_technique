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

public class ModifierProfilActivity extends Activity {

    private String url = "http://3.15.151.13/Laravel/api/GetUserInformation";
    private EditText Username;
    private EditText Email;
    private EditText Phone;
    private EditText Description;
    private TextView ErreurText_Modifier;
    private Button BTN_modifier;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);
        BTN_modifier = (Button) findViewById(R.id.BTN_modifier);
        Username = (EditText) findViewById(R.id.input_username_modifier);
        Email = (EditText) findViewById(R.id.input_email_modifier);
        Phone = (EditText) findViewById(R.id.input_phone_modifier);
        Description = (EditText) findViewById(R.id.input_description_modifier);
        ErreurText_Modifier = (TextView) findViewById(R.id.Textview_error_modifier);
    }

    public void GetUserInfo(View vue) {

            //Connection
            StringRequest jsonObjRequest = new StringRequest(
                    Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(ModifierProfilActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ErreurText_Modifier.setText(error.toString());

                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("iduser", "11");
                    return params;
                }
            };
            queue.add(jsonObjRequest);
        }


    public void RedirectToSubscribe(View view){
        Intent i = new Intent(this, SubscribeAcitivity.class);
        startActivity(i);
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