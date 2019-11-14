package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maisonlacroix.projetfinaltehnique.Classes.Commande;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class AjouterProduitActivity extends Activity {


    //pour les photos :
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    //service API
    ApiService service;
    Call<String> Token;
    private String UserId = "84";

    private EditText Nom;
    private EditText description;
    private EditText prix;
    private EditText quantite;
    private EditText tags;
    private TextView Textview_error_AjoutProduit;


    private String ImagePath;
    private String ImageName;

    private String Tags[] = {"test", "android", "ajout", "produit"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);
        Nom = findViewById(R.id.input_nom_AjoutProduit);
        description = findViewById(R.id.input_Definition_AjoutProduit);
        prix = findViewById(R.id.input_prix_AjoutProduit);
        quantite = findViewById(R.id.input_quantité_AjoutProduit);
        tags = findViewById(R.id.input_tags_AjoutProduit);
        Textview_error_AjoutProduit = findViewById(R.id.Textview_error_AjoutProduit);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imageView_AjouterProduit);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void AjouterProduit(View view)
    {

        if(validate()) {
            Token = service.AddProduct(Nom.getText().toString(), prix.getText().toString(),UserId,quantite.getText().toString(),"temp.jpg",description.getText().toString(),Tags);
            //requete de login
            Token.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful())
                    {
                        //todo
                    } else {
                        Log.e("request error : ", response.errorBody().toString());

                        if (response.code() == 400) {
                            Log.e("request error : ", "...");
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("request error : ", t.getMessage());
                }
            });

            Token = service.AddImage("temp.jpg");
            //requete d'ajout d'image
            Token.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful())
                    {
                        //todo
                    } else {
                        Log.e("request error : ", response.errorBody().toString());
                        if (response.code() == 400) {
                            Log.e("request error : ", "...");
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("request error : ", t.getMessage());
                }
            });
        }
    }

    private boolean validate()
    {
        boolean valid = true;
        String nom = Nom.getText().toString();
        String Prix = prix.getText().toString();
        String qte = quantite.getText().toString();


        if (nom.isEmpty()) {
            Textview_error_AjoutProduit.setText("Nom vide");
            Textview_error_AjoutProduit.setError("");
            valid = false;
        }
        else if (Prix.isEmpty()) {
            Textview_error_AjoutProduit.setText("prix vide");
            Textview_error_AjoutProduit.setError("");
            valid = false;
        }
        else if (qte.isEmpty()) {
            Textview_error_AjoutProduit.setText("quantite vide");
            Textview_error_AjoutProduit.setError("");
            valid = false;
        }
        if (valid)
        {
            Textview_error_AjoutProduit.setText(null);
            Textview_error_AjoutProduit.setError(null);
        }
        return valid;
    }

    public void pickFromGallery(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

}
