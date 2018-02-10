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

        LocalDateTime now;
        //now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
        bs.ajouterBoutique(boutique);
        User user = new User();
        user.setId(1);
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Evaluation e2 = new Evaluation(3, user, boutique, null, type1, 5);
        Evaluation e3 = new Evaluation(5,user,p1,null,type2,5);
        EvaluationService es = new EvaluationService();

        boolean a = es.ajouterEvaluation(e2);
        boolean b = es.ajouterEvaluation(e3);
//        es.ajouterEvaluation(e1);
//        es.supprimerEvaluation(e2);
//        es.supprimerEvaluation(e2.getId());

    }

}
