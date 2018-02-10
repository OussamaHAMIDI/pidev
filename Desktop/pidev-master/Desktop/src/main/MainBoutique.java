/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Services.BoutiqueService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Azza
 */
public class MainBoutique {
     
    
        public static void main(String[] args) {
    Produit p = new Produit();
        Produit p2 = new Produit();
        Produit p3 = new Produit();
        Produit p4 = new Produit();
      List produits = new ArrayList();

      
      //Boutique boutique = new Boutique("hello",produits, LocalDateTime.now(), "36rue");

      produits.add(p);
      produits.add(p2);
      Boutique boutique = new Boutique("hello",produits, LocalDateTime.MIN, "36rue");

      BoutiqueService bs = new BoutiqueService();
      boutique.setUserId(1);
      bs.ajouterBoutique(boutique);
 
}
}
