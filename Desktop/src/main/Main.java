/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Services.ProduitService;
import entities.Produit;

/**
 *
 * @author hamdi
 */
public class Main {

//    static Connection connexion;
//    static Statement state;
//    final static String url = "jdbc:mysql://localhost/DB_Name"; // !************ change db name **************** 
//    final static String user = "root";
//    final static String password = "";

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
