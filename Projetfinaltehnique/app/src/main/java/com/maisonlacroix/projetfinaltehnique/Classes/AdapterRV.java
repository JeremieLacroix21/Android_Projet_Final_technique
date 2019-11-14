package com.maisonlacroix.projetfinaltehnique.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.maisonlacroix.projetfinaltehnique.AllProducts;
import com.maisonlacroix.projetfinaltehnique.R;

import java.util.ArrayList;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.ViewHolderRV> {
    private ArrayList<Product> productList;

    public static class ViewHolderRV extends RecyclerView.ViewHolder{
        public TextView mTV_nom;
        public ViewHolderRV(View itemView) {
            super(itemView);
            mTV_nom = itemView.findViewById(R.id.TV_nom_product1);
        }
    }

    public AdapterRV(ArrayList<Product> plist){
        productList = plist;
    }


    @Override
    public ViewHolderRV onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_all_products,parent,false);
        ViewHolderRV vhrv = new ViewHolderRV(view);
        return vhrv;
    }

    @Override
    public void onBindViewHolder(ViewHolderRV holder, int position) {
        Product currentitem =  productList.get(position);
        holder.mTV_nom.setText(currentitem.getNom());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
