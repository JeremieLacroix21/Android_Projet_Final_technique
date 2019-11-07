package com.maisonlacroix.projetfinaltehnique.Classes;

import com.android.volley.toolbox.StringRequest;

import java.util.Date;


public class Commandes {
    public int idCommande;
    public int idFournisseur;
    public String nomFournisseur;
    public int idDistributeur;
    public String dateCreation;
    public int complete;
    public boolean EstOuvert;
    public String EmailFournisseur;
    public String telephone;
    public CommandesItems TableItem[];
}
