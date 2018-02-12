/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Panier;
import Entities.ProduitPanier;
import Entities.User;
import Services.PanierService;
import Services.UserService;
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
        UserService us = new UserService();
        User u = us.getUserById(1);
        Panier p = new Panier(u, LocalDateTime.now());
        PanierService ps = new PanierService();
       //ps.ajouterPanier(p);
       
       ProduitPanier pp = new ProduitPanier(1, 1, 1, 2, "omek", "bouk", "jedek", 1, "m", "rouge", "", 0, 2);
       ps.ajouterProduitPanier(pp, 1);
}
}