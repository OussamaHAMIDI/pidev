/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Services.*;
import Entities.*;
import Utils.Enumerations.*;

/**
 *
 * @author Hamdi
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




        ProduitService ps= new ProduitService();

        PanierService pps = new PanierService();
        
        UserService us = new UserService();
     
        BoutiqueService bs = new BoutiqueService();
        
        Boutique testBoutique = new Boutique();
        User testUser = new User();
        Panier testPanier = new Panier();
        testBoutique = bs.chercherBoutiqueParID(94);
       testPanier = pps.rechercherPanierById(3);
        testUser = us.getUserById(5);
        
 testBoutique.toString();
 
    }

}
