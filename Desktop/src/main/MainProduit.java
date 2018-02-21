/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Services.ProduitService;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oussamahamidi
 */
public class MainProduit {
    
    static Connection connexion;
    static Statement state;
    final static String url = "jdbc:mysql://localhost:3306/souk";
    final static String user = "root";
    final static String password = "";
    
    public static void main(String[] args) {

    Produit p = new Produit(0,"", "ref", "", 100, "54", "fsgd", "sgg", 0, null, null, null);
    
        Boutique b = new Boutique();
        b.setId(10);
        //Produit p = new Produit("azerty", "azerty", "azert", 1.0f, "azerty" , "azerty", "azerty", 2.2f, b, LocalDateTime.MAX, null);

    
    ProduitService ps = new ProduitService();
    ps.ajouterProduit(p);
    
   
}
}
