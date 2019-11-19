package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
import com.maisonlacroix.projetfinaltehnique.Classes.AdapterRV;
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
    private RecyclerView.Adapter mAdapter;

    private String url = "http://3.15.151.13/Laravel/api/GetAllProducts";
    public ArrayList<Product> Products = new ArrayList<Product>();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        queue = Volley.newRequestQueue(this);
        //ET_filter = (EditText)findViewById(R.id.ET_Filter);
        GetProducts();

        for (Product item:Products) {
            Toast.makeText(AllProducts.this,item.getNom(),Toast.LENGTH_LONG).show();
        }

        RV_products = (RecyclerView)findViewById((R.id.RV_products));
        RV_products.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(AllProducts.this);
        mAdapter = new AdapterRV(Products);
        RV_products.setLayoutManager(rvLayoutManager);
        RV_products.setAdapter(mAdapter);

    }

    public void GetProducts() {
        //final ArrayList<Product> Products1 = new ArrayList<Product>();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            ArrayList<Product> Products1 = new ArrayList<Product>();
            @Override
            public void onResponse(String response) {

                Products1 = new Gson().fromJson(response.toString(), new TypeToken<List<Product>>(){}.getType());



                //SetRecyclerView(Products);
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

    }
}
