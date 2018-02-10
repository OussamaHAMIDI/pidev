/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Panier;
import Entities.ProduitPanier;
import Services.PanierService;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 *
 * @author benab
 */
public class MainPanier {
    
    static Connection connexion;
    static Statement state;
    final static String url = "jdbc:mysql://localhost:3306/souk";
    final static String user = "root";
    final static String password = "";
    
    public static void main(String[] args) {
    Panier p = new Panier(1, LocalDateTime.now());
    
    
    PanierService ps = new PanierService();
    ps.ajouterPanier(p);
   // ProduitPanier p1 = new ProduitPanier(2, 2.2, 2.1, 1, user, url, user, 0, url, user, url, 0, 0)
}
}