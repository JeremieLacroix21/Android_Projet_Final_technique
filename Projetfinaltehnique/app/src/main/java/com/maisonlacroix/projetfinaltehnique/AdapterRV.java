package com.maisonlacroix.projetfinaltehnique;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.maisonlacroix.projetfinaltehnique.Classes.Product;

import java.util.ArrayList;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.ViewHolderRV> {
    private ArrayList<Product> productList;

    public static class ViewHolderRV extends RecyclerView.ViewHolder{
        public TextView mTV_nom;
        public TextView mTV_prix;
        public TextView mTV_quantite;
        public TextView mTV_fournisseur;

        public ViewHolderRV(View itemView) {
            super(itemView);
            mTV_nom = itemView.findViewById(R.id.TV_nom_product1);
            mTV_prix = itemView.findViewById(R.id.TV_prix);
            mTV_quantite = itemView.findViewById(R.id.TV_quantite);
            mTV_fournisseur = itemView.findViewById(R.id.TV_produitfournisseur);
        }
    }

    public AdapterRV(ArrayList<Product> plist){
        productList = plist;
    }


    @Override
    public ViewHolderRV onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        ViewHolderRV vhrv = new ViewHolderRV(view);
        return vhrv;

    }

    @Override
    public void onBindViewHolder(ViewHolderRV holder, int position) {
        Product currentitem =  productList.get(position);
        holder.mTV_nom.setText(currentitem.getNom());
        holder.mTV_prix.setText(Integer.toString(currentitem.prix) + "$");
        holder.mTV_quantite.setText(Integer.toString(currentitem.enStock) + " en stock ");
        holder.mTV_fournisseur.setText(currentitem.nomFournisseur+ "    ");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
