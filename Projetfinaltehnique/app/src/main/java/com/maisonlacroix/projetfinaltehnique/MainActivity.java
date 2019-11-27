package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maisonlacroix.projetfinaltehnique.Classes.Access_Token;
import com.maisonlacroix.projetfinaltehnique.Classes.User;
import com.maisonlacroix.projetfinaltehnique.Classes.UserInfo;
import com.maisonlacroix.projetfinaltehnique.chatkit.conversations.ConversationsActivity;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private String ID_USER;
    private String TYPE_USER;
    //service API
    ApiService service;


    //Boutton
    private Button button_inventaire;
    private Button button_AjouterProduit;
    private Button button_Commandes;
    private Button button_Produit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");
        TYPE_USER = intent.getStringExtra("key2");
        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);
        button_inventaire = findViewById(R.id.button_inventaire);
        button_AjouterProduit= findViewById(R.id.button_addproduct);
        button_Commandes= findViewById(R.id.button_commandes);
        button_Produit= findViewById(R.id.button_produits);

        if (TYPE_USER.equals("Fournisseur"))
        {
            button_Produit.setVisibility(View.GONE);
            button_Commandes.setVisibility(View.GONE);
        }
        else if (TYPE_USER.equals("Distributeur"))
        {
            button_inventaire.setVisibility(View.GONE);
            button_AjouterProduit.setVisibility(View.GONE);
        }
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
