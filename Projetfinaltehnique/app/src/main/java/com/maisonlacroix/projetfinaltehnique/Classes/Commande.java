package com.maisonlacroix.projetfinaltehnique.Classes;


import java.util.List;
import com.squareup.moshi.Json;

public class Commande {

    @Json(name = "idCommande")
    private Integer idCommande;
    @Json(name = "idDistributeur")
    private Integer idDistributeur;
    @Json(name = "dateCreation")
    private String dateCreation;
    @Json(name = "complete")
    private Integer complete;
    @Json(name = "idFournisseur")
    private Integer idFournisseur;
    @Json(name = "nomFournisseur")
    private String nomFournisseur;
    @Json(name = "EmailFournisseur")
    private String emailFournisseur;
    @Json(name = "telephone")
    private String telephone;
    //@Json(name = "TableItem")
    //private List<TableItem> tableItem = null;

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Integer getIdDistributeur() {
        return idDistributeur;
    }

    public void setIdDistributeur(Integer idDistributeur) {
        this.idDistributeur = idDistributeur;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public String getEmailFournisseur() {
        return emailFournisseur;
    }

    public void setEmailFournisseur(String emailFournisseur) {
        this.emailFournisseur = emailFournisseur;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    //public List<TableItem> getTableItem() {
    //    return tableItem;
   // }

    //public void setTableItem(List<TableItem> tableItem) {
    //    this.tableItem = tableItem;
    //}

}
