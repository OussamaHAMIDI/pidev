/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Entities.User;
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
        p.setIdProduit(2);
        Produit p2 = new Produit();
        //Produit p3 = new Produit();
        //Produit p4 = new Produit();
        List produits = new ArrayList();

        //Boutique boutique = new Boutique("hello",produits, LocalDateTime.now(), "36rue");
        produits.add(p);
        produits.add(p2);
        User user = new User();
        user.setId(2);
        Boutique boutique = new Boutique(user, "hello", produits);
        
        BoutiqueService bs = new BoutiqueService();
        boutique.setUser(user);

        //bs.ajouterBoutique(boutique);
        boutique.setId(7);
        bs.ajouterProduit(p, boutique);
        //List<Boutique> boutiques = new ArrayList<Boutique>();
        //bs.modifierAdresseBoutique(boutique,"5 rue azza");
        //boutiques = bs.chercherBoutiquesParNom("hello");
        //System.out.println(boutiques);

        //bs.supprimerBoutique(boutique);
    }
}
