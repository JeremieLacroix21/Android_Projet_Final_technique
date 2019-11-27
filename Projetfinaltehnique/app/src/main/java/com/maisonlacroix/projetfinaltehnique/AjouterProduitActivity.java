package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.maisonlacroix.projetfinaltehnique.network.RetrofitBuilder;
import com.squareup.picasso.Picasso;

<<<<<<< HEAD
=======

>>>>>>> a3d878c77c6c1281fe1c2881111792c9fe54eefb
import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AjouterProduitActivity extends Activity {





    //service API
    ApiService service;
    Call<String> Token1;
    Call<String> Token2;
    private String ID_USER;

    private EditText Nom;
    private EditText description;
    private EditText prix;
    private EditText quantite;
    private EditText tags;
    private TextView Textview_error_AjoutProduit;
    private ImageView imageView;
    private Button bouton_ajouter;
    private String uniqueId = null;

    //pour les photos :
    private static int RESULT_LOAD_IMAGE = 1;
    private String ImagePath;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String Tags[] = {"test", "android", "ajout", "produit"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);

        //sercvice
        service = RetrofitBuilder.createService(ApiService.class);

        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("key1");

        ImageView imageView = (ImageView) findViewById(R.id.imageView_AjouterProduit);

        if (!EasyPermissions.hasPermissions(this, galleryPermissions)) {
            EasyPermissions.requestPermissions(this, "Access for storage", 101, galleryPermissions);
        }

        Nom = findViewById(R.id.input_nom_AjoutProduit);
        description = findViewById(R.id.input_Definition_AjoutProduit);
        prix = findViewById(R.id.input_prix_AjoutProduit);
        quantite = findViewById(R.id.input_quantité_AjoutProduit);
        tags = findViewById(R.id.input_tags_AjoutProduit);
        Textview_error_AjoutProduit = findViewById(R.id.Textview_error_AjoutProduit);
        bouton_ajouter = findViewById(R.id.BTN_AjouterProduit);
        bouton_ajouter.setOnClickListener(v -> AjouterProduit());
        Button buttonLoadImage = (Button) findViewById(R.id.BTN_ChoisirImage_AjouterProduit);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
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
            String picturePath = cursor.getString(columnIndex);
            ImagePath = picturePath;
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imageView_AjouterProduit);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));



        }
    }

<<<<<<< HEAD
    public void AjouterProduit()
    {
        if(validate()) {
            Token1 = service.AddProduct(Nom.getText().toString(), prix.getText().toString(),ID_USER.toString(),quantite.getText().toString(),uniqueId + ".jpg",description.getText().toString(),Tags);
            //requete de login
=======
    public void AjouterProduit(View view)
    {
        
        if(validate()) {
            Token1 = service.AddProduct(Nom.getText().toString(), prix.getText().toString(),ID_USER.toString(),quantite.getText().toString(),"android.png",description.getText().toString(),Tags);

             //requete de login
>>>>>>> a3d878c77c6c1281fe1c2881111792c9fe54eefb
            Token1.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful())
                    {

                        //todo
                    } else {
                        Log.e("ajout produit  error : ", response.errorBody().toString());
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ajout produit error : ", t.getMessage());
                }
            });

<<<<<<< HEAD


            //Create a file object using file path



        }
    }

=======
        }
    }

    /*public void AjouterImage()
    {
        //not working
        Token2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful())
                {
                    //todo
                } else {
                    Log.e("ajout image  error : ", response.errorBody().toString());
                    if (response.code() == 400) {
                        Log.e("ajout image error : ", "...");
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ajout image error : ", t.getMessage());
            }
        });

    }
>>>>>>> a3d878c77c6c1281fe1c2881111792c9fe54eefb

     */

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
<<<<<<< HEAD

    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

=======
>>>>>>> a3d878c77c6c1281fe1c2881111792c9fe54eefb
}
