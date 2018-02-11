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
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
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
        Produit p2 = new Produit();
        List<Produit> produits = new ArrayList<Produit>();

        produits.add(p1);
        produits.add(p2);
        p1.setIdProduit(1);
        Boutique boutique = new Boutique(5,"hello", produits, LocalDateTime.MIN, "36rue");
        BoutiqueService bs = new BoutiqueService();
        boutique.setUserId(1);
        User user = new User();
        user.setId(1);
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Reclamation r1 = new Reclamation(user, boutique,"maaasset", null, type1);
        Reclamation r2 = new Reclamation(user,p1,"hloww barcha",null,type2);
        ReclamationService rs = new ReclamationService();

        boolean a = rs.ajouterReclamation(r1);
        boolean b = rs.ajouterReclamation(r2);
        List<Reclamation> reclamations = new ArrayList<Reclamation>();
        reclamations = rs.rechercherReclamationBoutique(boutique);
        
        //boolean x = es.supprimerReclamation(r1);
    
}
}
