/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import Services.BoutiqueService;
import Services.ReclamationService;
import Services.UserService;
import Utils.Enumerations;
import Utils.Enumerations.EtatUser;
import Utils.Enumerations.TypeReclamation;
import Utils.Enumerations.TypeUser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public class MainReclamation {
    
     public static void main(String[] args) {
        
   
        TypeReclamation type1 = TypeReclamation.Boutique;
        TypeReclamation type2 = TypeReclamation.Produit;

        Produit p1 = new Produit();
        p1.setIdProduit(2);
        Produit p2 = new Produit();
        List<Produit> produits = new ArrayList<Produit>();

        produits.add(p1);
        produits.add(p2);
        User user ;
        UserService us = new UserService();
        user = us.getUserById(2);
        user.setId(2);
        Boutique boutique = new Boutique(user,"Hellou",produits);
        boutique.setId(2);
        BoutiqueService bs = new BoutiqueService();
        bs.ajouterBoutique(boutique);
        
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Reclamation r1 = new Reclamation(user, boutique,"maaasset");
        Reclamation r2 = new Reclamation(user,p1,"hloww barcha");
        ReclamationService rs = new ReclamationService();

        boolean a = rs.ajouterReclamation(r1);
        boolean b = rs.ajouterReclamation(r2);
        //List<Reclamation> reclamations = new ArrayList<Reclamation>();
        //reclamations = rs.rechercherReclamationBoutique(boutique);
        
        //boolean x = es.supprimerReclamation(r1);
    
}
}
