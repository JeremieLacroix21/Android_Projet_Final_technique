package com.maisonlacroix.projetfinaltehnique.Classes;

import com.squareup.moshi.Json;

public class TableItem {

    @Json(name = "imgGUID")
    private String imgGUID;
    @Json(name = "nom")
    private String nom;
    @Json(name = "prix")
    private Integer prix;
    @Json(name = "quantite")
    private Integer quantite;
    @Json(name = "description")
    private String description;

    public String getImgGUID() {
        return imgGUID;
    }

    public void setImgGUID(String imgGUID) {
        this.imgGUID = imgGUID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}