package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Liste_fournisseur_on_click(View view){
        Intent intent = new Intent(this, ListeFournisseur.class);
        startActivity(intent);
    }
    public void AboutOnClick(View view)
    {
        Intent intent = new Intent(this, AllProducts.class);
        startActivity(intent);
    }
    public void ModifierProfilOnClick(View view)
    {
        Intent intent = new Intent(this, ModifierProfilActivity.class);
        startActivity(intent);
    }
    public void InventaireOnclick(View view)
    {
        Intent intent = new Intent(this, InventaireActivity.class);
        startActivity(intent);
    }
    public void AjouterProduitOnClick(View view)
    {
        Intent intent = new Intent(this, AjouterProduitActivity.class);
        startActivity(intent);
    }
    public void VisualiserCommandeOnClick(View view)
    {
        Intent intent = new Intent(this, VisualiserCommandesActivity.class);
        startActivity(intent);
    }

    public void ListeCompagniesOnClick(View view)
    {
        Intent intent = new Intent(this, CompagnieInscriteActivity.class);
        startActivity(intent);
    }
    public void logout(View view)
    {

    }
}
