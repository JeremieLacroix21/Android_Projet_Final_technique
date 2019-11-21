package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maisonlacroix.projetfinaltehnique.chatkit.conversations.ConversationsActivity;

public class MainActivity extends AppCompatActivity {

    private String ID_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");
    }

    public void Liste_fournisseur_on_click(View view){
        Intent intent = new Intent(this, ListeFournisseur.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void AboutOnClick(View view)
    {
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void ModifierProfilOnClick(View view)
    {
        Intent intent = new Intent(this, ModifierProfilActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void InventaireOnclick(View view)
    {
        Intent intent = new Intent(this, InventaireActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void AjouterProduitOnClick(View view)
    {
        Intent intent = new Intent(this, AjouterProduitActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void VisualiserCommandeOnClick(View view)
    {
        Intent intent = new Intent(this, VisualiserCommandesActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void ListeCompagniesOnClick(View view)
    {
        Intent intent = new Intent(this, CompagnieInscriteActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void ProduitsOnClick(View view)
    {
        Intent intent = new Intent(this, AllProducts.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    public void ChatOnClick(View view)
    {
        Intent chatIntent = new Intent(this, ConversationsActivity.class);
        chatIntent.putExtra("curr_user_id", ID_USER);
        startActivity(chatIntent);
    }

    public void logout(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
