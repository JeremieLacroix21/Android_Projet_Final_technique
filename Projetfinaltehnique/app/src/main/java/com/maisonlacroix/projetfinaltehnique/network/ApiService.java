package com.maisonlacroix.projetfinaltehnique.network;

import com.maisonlacroix.projetfinaltehnique.Classes.Access_Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
}
