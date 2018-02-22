/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import Services.ProduitService;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public class MainProduit {
    
    static Connection connexion;
    static Statement state;
    final static String url = "jdbc:mysql://localhost:3306/souk";
    final static String user = "root";
    final static String password = "";
    
    public static void main(String[] args) {
//        User user = new User();
//        user.setId(1);
Boutique boutique = new Boutique();
boutique.setId(7);
    Produit p = new Produit("sf", "fsc ", "fsf ", 2.0f, "gs ", "zf ", " fzs", 2.0f, boutique);
    
    
    ProduitService ps = new ProduitService();
    ps.ajouterProduit(p);
    
   
}
}
