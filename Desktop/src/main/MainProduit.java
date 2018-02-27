/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Services.ProduitService;
import Utils.SmsSender;
import java.io.IOException;
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
    
    public static void main(String[] args) throws IOException {

//    Produit p = new Produit(0,"", "azzzz", "", 100, "54", "fsgd", "sgg", 0, null, null, null);
    
        Boutique b = new Boutique();
        b.setId(10);
        Produit p = new Produit("ttttt", "tttt", "ttt", 1.0f, "ttttt" , "tttt", "tttt", 2.2f, b, LocalDateTime.MAX, null);

    
        ProduitService ps = new ProduitService();
        if(ps.ajouterProduit(p))
        {
            SmsSender ss = new SmsSender();
            ss.sendSms("ajout%20effectué", "54476969");
        }
    
   
}
}
