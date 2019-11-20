package com.maisonlacroix.projetfinaltehnique.Classes;


import java.util.List;
import com.squareup.moshi.Json;

public class ProduitInventaire {

    @Json(name = "idproduits")
    private Integer idproduits;
    @Json(name = "nom")
    private String nom;
    @Json(name = "prix")
    private Integer prix;
    @Json(name = "idFournisseur")
    private Integer idFournisseur;
    @Json(name = "enStock")
    private Integer enStock;
    @Json(name = "imgGUID")
    private String imgGUID;
    @Json(name = "description")
    private String description;
    //@Json(name = "tags")
    //private List<Tag> tags = null;

    public Integer getIdproduits() {
        return idproduits;
    }

    public void setIdproduits(Integer idproduits) {
        this.idproduits = idproduits;
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

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public Integer getEnStock() {
        return enStock;
    }

    public void setEnStock(Integer enStock) {
        this.enStock = enStock;
    }

    public String getImgGUID() {
        return imgGUID;
    }

    public void setImgGUID(String imgGUID) {
        this.imgGUID = imgGUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
*/
}