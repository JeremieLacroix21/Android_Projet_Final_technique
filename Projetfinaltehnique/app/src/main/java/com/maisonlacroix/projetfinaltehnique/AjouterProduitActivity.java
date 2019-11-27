package com.maisonlacroix.projetfinaltehnique;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.maisonlacroix.projetfinaltehnique.network.ApiService;
import com.squareup.picasso.Picasso;

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


    //pour les photos :
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;


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

    private ImageView image;
    private String ImagePath;
    private String ImageName;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String Tags[] = {"test", "android", "ajout", "produit"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_produit);

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
    public void AjouterProduit(View view)
    {
        String namef = UUID.randomUUID().toString();
        if(validate()) {
            Token1 = service.AddProduct(Nom.getText().toString(), prix.getText().toString(),ID_USER,quantite.getText().toString(),namef,description.getText().toString(),Tags);
            //requete de login
            Token1.enqueue(new Callback<String>() {
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

            //uploadToServer(ImagePath, );

            //Create a file object using file path
            File file = new File(ImagePath);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);

            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part = MultipartBody.Part.createFormData("upload", namef, fileReqBody);

            //
            //Call call = uploadAPIs.uploadImage(part, description);
            Call call = service.AddImage(part);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

           // Token2 = service.AddImage("temp.jpg");
            //requete d'ajout d'image
           // Token2.enqueue(new Callback<String>() {
             //   @Override
             //   public void onResponse(Call<String> call, retrofit2.Response<String> response) {
              //      if (response.isSuccessful())
                 //   {
                //        //todo
                  //  } else {
                  //      Log.e("request error : ", response.errorBody().toString());
                  //      if (response.code() == 400) {
                 //           Log.e("request error : ", "...");
                  //      }
               //     }
               // }
              //  @Override
              //  public void onFailure(Call<String> call, Throwable t) {
              //      Log.e("request error : ", t.getMessage());
             //   }
           // });
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

    public void RedirectToMainMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key1", ID_USER);
        startActivity(intent);
    }

    private void uploadToServer(String filePath, String nom) {


    }
}
