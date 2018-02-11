/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Evaluation;
import Entities.Produit;
import Entities.User;
import Services.BoutiqueService;
import Services.EvaluationService;
import Services.UserService;
import Utils.Enumerations.TypeReclamation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author benab
 */
public class MainEvaluation {

    public static void main(String[] args) {

        TypeReclamation type1 = TypeReclamation.Boutique;
        TypeReclamation type2 = TypeReclamation.Produit;

        Produit p1 = new Produit();
        p1.setIdProduit(2);
        Produit p2 = new Produit();
        List<Produit> produits = new ArrayList<Produit>();

        produits.add(p1);
        produits.add(p2);
        Boutique boutique = new Boutique("hello",produits, LocalDateTime.MIN, "36rue");
        boutique.setId(2);
        BoutiqueService bs = new BoutiqueService();
        boutique.setUserId(1);
        bs.ajouterBoutique(boutique);
        User user ;
        UserService us = new UserService();
        user = us.getUserById(2);
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Evaluation e1 = new Evaluation(user, boutique, null, type1,5);
        Reclamation r2 = new Evaluation(user,p1,null,type2,5);
        ReclamationService rs = new ReclamationService();

        boolean a = rs.ajouterReclamation(r1);
        boolean b = rs.ajouterReclamation(r2);
        
        
//        es.ajouterEvaluation(e1);
//        es.supprimerEvaluation(e2);
//        es.supprimerEvaluation(e2.getId());

    }

}
