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
<<<<<<< HEAD
//        Produit p = new Produit();
//        p.setIdProduit(2);
//        Produit p2 = new Produit();
//        //Produit p3 = new Produit();
//        //Produit p4 = new Produit();
//        List produits = new ArrayList();
=======
        Produit p = new Produit();
        p.setId(2);
        Produit p2 = new Produit();
        //Produit p3 = new Produit();
        //Produit p4 = new Produit();
        List produits = new ArrayList();
>>>>>>> 83748a129083bdc33afdf08631cd522045911ba8

        //Boutique boutique = new Boutique("hello",produits, LocalDateTime.now(), "36rue");
//        produits.add(p);
//        produits.add(p2);
        User user = new User();
<<<<<<< HEAD
        user.setId(1);
//        Boutique boutique = new Boutique(user, "Soso", "3 rue",LocalDateTime.now());
        
=======
        user.setId(2);
        Boutique boutique = new Boutique(user, "hello", produits);
        boutique.setId(7);
>>>>>>> 83748a129083bdc33afdf08631cd522045911ba8
        BoutiqueService bs = new BoutiqueService();
//        boutique.setUser(user);

<<<<<<< HEAD
//        bs.modifierAdresseBoutique(boutique, "55555");
for(Boutique B :bs.lireBoutiques())
{
    System.out.println(B.toString()); 
}
        
//        p.setIdBoutique(7);
//        boutique.setId(7);
//        bs.ajouterProduit(p, boutique);
//        produits=bs.lireProduitsParBoutique(boutique);
//        System.out.println(produits);
=======
        //bs.ajouterBoutique(boutique);
        
        bs.ajouterProduit(p, boutique);
        produits=bs.lireProduitsParBoutique(boutique);
        System.out.println(produits);
>>>>>>> 83748a129083bdc33afdf08631cd522045911ba8
        //List<Boutique> boutiques = new ArrayList<Boutique>();
        //bs.modifierAdresseBoutique(boutique,"5 rue azza");
        //boutiques = bs.chercherBoutiquesParNom("hello");
        //System.out.println(boutiques);

        //bs.supprimerBoutique(boutique);
    }
}
