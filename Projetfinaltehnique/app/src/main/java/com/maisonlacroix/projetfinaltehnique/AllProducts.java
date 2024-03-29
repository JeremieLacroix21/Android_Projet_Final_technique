package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maisonlacroix.projetfinaltehnique.Classes.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProducts extends AppCompatActivity {
    private String ID_USER;

    private EditText ET_filter;
    private RecyclerView RV_products;
    private RecyclerView.LayoutManager  rvLayoutManager;
    private AdapterRV mAdapter;

    private String url = "http://3.15.151.13/Laravel/api/GetAllProducts";
    public ArrayList<Product> Products = new ArrayList<Product>();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        queue = Volley.newRequestQueue(this);
        GetProducts();
        ET_filter = (EditText)findViewById(R.id.ET_Filter);
        ET_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    public void GetProducts() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            ArrayList<Product> Products1 = new ArrayList<Product>();
            @Override
            public void onResponse(String response) {
                Products1 = new Gson().fromJson(response.toString(), new TypeToken<List<Product>>(){}.getType());
                SetRecyclerView(Products1);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AllProducts.this,"error",Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        queue.add(jsonObjRequest);
    }

    void SetRecyclerView(ArrayList<Product> p){
        Products = p;
        RV_products = (RecyclerView)findViewById((R.id.RV_products));
        RV_products.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(AllProducts.this);
        mAdapter = new AdapterRV(p);
        RV_products.setLayoutManager(rvLayoutManager);
        RV_products.setAdapter(mAdapter);
    }

    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (Product item : Products) {
            if (item.getNom().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }
}
