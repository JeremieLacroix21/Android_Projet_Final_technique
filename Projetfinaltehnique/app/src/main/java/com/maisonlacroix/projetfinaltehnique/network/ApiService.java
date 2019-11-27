package com.maisonlacroix.projetfinaltehnique.network;

import com.maisonlacroix.projetfinaltehnique.Classes.Access_Token;
import com.maisonlacroix.projetfinaltehnique.Classes.Commande;
import com.maisonlacroix.projetfinaltehnique.Classes.ProduitInventaire;
import com.maisonlacroix.projetfinaltehnique.Classes.User;
import com.maisonlacroix.projetfinaltehnique.Classes.UserInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    /*

    @POST("auth/register")
    @FormUrlEncoded
    Call<AccessToken> register (@Field("username") String username,
                               @Field("profile_name") String profilName,
                               @Field("email") String email,
                               @Field("password") String pwd,
                               @Field("password_confirmation") String ConfirmPwd);

     */

    @POST("login")
    @FormUrlEncoded
    Call<Access_Token> login (@Field("name") String name,
                              @Field("password") String password);

    @POST("GetCommandeDistributeur")
    @FormUrlEncoded
    Call<Commande[]> GetCommandeDistributeur (@Field("idDistributeur") String idDistributeur);

    @POST("AddProduct")
    @FormUrlEncoded
    Call<String> AddProduct( @Field("nom") String nom,
                             @Field("prix") String prix,
                             @Field("idFournisseur") String idFournisseur,
                             @Field("enStock") String enStock,
                             @Field("imgGUID") String imgGUID,
                             @Field("description") String description,
                             @Field("Tags") String[] Tags);

    @POST("AddImage")
    @FormUrlEncoded
    Call<String> AddImage( @Field("Nom") String nom);

    @GET("GetAllUsers")
    Call<User[]> GetAllUsers();

    @POST("GetProductsByFournisseur")
    @FormUrlEncoded
    Call<ProduitInventaire[]> GetProductsByFournisseur(@Field("id") String id);


    @POST("GetUserInformation")
    @FormUrlEncoded
    Call<UserInfo> GetUserInformation(@Field("iduser") String id);
}
