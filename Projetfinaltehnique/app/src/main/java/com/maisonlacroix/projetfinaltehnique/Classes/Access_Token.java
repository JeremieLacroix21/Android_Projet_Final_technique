package com.maisonlacroix.projetfinaltehnique.Classes;

import com.squareup.moshi.Json;

public class Access_Token {
    @Json(name = "iduser")
    private Integer iduser;
    @Json(name = "nomutilisateur")
    private String nomutilisateur;
    @Json(name = "nom")
    private String nom;
    @Json(name = "prenom")
    private String prenom;
    @Json(name = "TypeUser")
    private String typeUser;
    @Json(name = "adresse")
    private String adresse;
    @Json(name = "admin")
    private Integer admin;
    @Json(name = "confirme")
    private Integer confirme;
    @Json(name = "dateinscription")
    private String dateinscription;
    @Json(name = "email")
    private String email;
    @Json(name = "Telephone")
    private String telephone;
    @Json(name = "description")
    private String description;
    @Json(name = "imgGUID")
    private String imgGUID;
    @Json(name = "nbEtoiles")
    private Integer nbEtoiles;
    @Json(name = "profit")
    private Integer profit;
    @Json(name = "idFournisseur")
    private Integer idFournisseur;
    /*
    @Json(name = "cart")
    private List<Object> cart = null;

     */

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getNomutilisateur() {
        return nomutilisateur;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getConfirme() {
        return confirme;
    }

    public void setConfirme(Integer confirme) {
        this.confirme = confirme;
    }

    public String getDateinscription() {
        return dateinscription;
    }

    public void setDateinscription(String dateinscription) {
        this.dateinscription = dateinscription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgGUID() {
        return imgGUID;
    }

    public void setImgGUID(String imgGUID) {
        this.imgGUID = imgGUID;
    }

    public Integer getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(Integer nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    /*
    public List<Object> getCart() {
        return cart;
    }

    public void setCart(List<Object> cart) {
        this.cart = cart;
    }

     */

    public static class tags {
    }
}
