package com.maisonlacroix.projetfinaltehnique.Classes;

import com.squareup.moshi.Json;

public class UserInfo {

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
    @Json(name = "confirme")
    private String confirme;
    @Json(name = "dateinscription")
    private String dateinscription;
    @Json(name = "email")
    private String email;
    @Json(name = "Telephone")
    private String telephone;
    @Json(name = "description")
    private String description;

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

    public String getConfirme() {
        return confirme;
    }

    public void setConfirme(String confirme) {
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
}