/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Evaluation;
import Services.EvaluationService;
import Utils.Enumerations.TypeReclamation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 *
 * @author benab
 */
public class MainEvaluation {
    
    public static void main(String[] args) {
        
   
        LocalDateTime now;
        //now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        TypeReclamation type = TypeReclamation.Boutique;
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Evaluation e2 = new Evaluation(2,2,2,null,type,5);
        
        EvaluationService es = new EvaluationService();
        
        boolean a = es.ajouterEvaluation(e2);
//        es.ajouterEvaluation(e1);
//        es.supprimerEvaluation(e2);
//        es.supprimerEvaluation(e2.getId());
        
        
    }
    
}
