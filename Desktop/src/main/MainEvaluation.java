/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Boutique;
import Entities.Evaluation;
import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import Entities.User;
import Services.BoutiqueService;
import Services.EvaluationService;
import Services.PanierService;
import Services.StatistiqueService;
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

//        TypeReclamation type1 = TypeReclamation.Boutique;
//        TypeReclamation type2 = TypeReclamation.Produit;
//
//        Produit p1 = new Produit();
//        p1.setId(2);
//        Produit p2 = new Produit();
//        List<Produit> produits = new ArrayList<Produit>();
//
//        produits.add(p1);
//        produits.add(p2);
        User user ;
        UserService us = new UserService();
        user = us.getUserById(2);
        System.out.println(user);
//        user.setId(2);
//        Boutique boutique = new Boutique(user,"Hellou",produits);
//        boutique.setId(2);
//        BoutiqueService bs = new BoutiqueService();
//        //bs.ajouterBoutique(boutique);
//        
//        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
//        //Evaluation e1 = new Evaluation(user,boutique,5);
//        //Evaluation e2 = new Evaluation(user,p1,5);
//        EvaluationService es = new EvaluationService();

        //boolean a = rs.ajouterEvaluation(e1);
        //boolean b = rs.ajouterEvaluation(e2);
        
        
//        es.ajouterEvaluation(e1);
//        es.supprimerEvaluation(e2);
//        es.supprimerEvaluation(e2.getId());
        //List<Evaluation> hey = new ArrayList<Evaluation>();
        //hey = es.rechercherEvaluationUserProduit(user,p1);
        //System.out.println(hey);
//        float note = es.getNoteBoutique(boutique);
//        System.out.println(note);
//        Evaluation ev = es.getEvaluationById(5);
//        ev.setNote(10);
//        es.modifierEvaluation(ev);
        
//        StatistiqueService ss = new StatistiqueService();
////        
////        lb = ss.getTopTenBoutiques();
//        BoutiqueService bs = new BoutiqueService();
//        List<Boutique> lb = bs.lireBoutique(user);
//        List<Produit> lp = new ArrayList<Produit>();
//        for(Boutique b : lb){
//            lp.addAll(bs.lireProduitsParBoutique(b));
//            b.setListProduit(lp);
//        }
//        
//       System.out.println(lb);
//        System.out.println(lp);
//        Float x = ss.getQuantiteProduitsVendusParMois("-02-");
//        System.out.println(x);
        PanierService ps = new PanierService();
        //Panier panier = ps.rechercherPanierById(29);
        List<ProduitPanier> lpp = ps.rechercherProduitsPanier(29);
        System.out.println("Le panier EST" + lpp);
    }

}
