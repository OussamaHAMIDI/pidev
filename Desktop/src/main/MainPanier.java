/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Panier;
import Services.PanierService;
import java.sql.Connection;
import java.sql.Statement;

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
    Panier p = new Panier();
    
    
    PanierService ps = new PanierService();
    ps.ajouterPanier(p);
}
}