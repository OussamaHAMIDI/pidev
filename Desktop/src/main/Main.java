/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DataStorage.MyDB;
import Services.ProduitService;
import entities.Produit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhk
 */
public class Main {

    static Connection connexion;
    static Statement state;
    final static String url = "jdbc:mysql://localhost/3a12";
    final static String user = "root";
    final static String password = "AhmedBHK";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Produit p = new Produit("Lait", 250);
        Produit p2 = new Produit("Yaourt", 3000);
        Produit p3 = new Produit("Savon", 50000);
        Produit p4 = new Produit("Pain", 100);

        ProduitService ps= new ProduitService();
        ps.ajouterProduit(p);
        ps.ajouterProduit(p2);
        ps.ajouterProduit(p3);
        ps.ajouterProduit(p4);

    }

}
