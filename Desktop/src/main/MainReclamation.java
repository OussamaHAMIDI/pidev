/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.Reclamation;
import Services.ReclamationService;
import Utils.Enumerations;
import java.time.LocalDateTime;

/**
 *
 * @author benab
 */
public class MainReclamation {
    
     public static void main(String[] args) {
        
   
        LocalDateTime now;
        //now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Enumerations.TypeReclamation type = Enumerations.TypeReclamation.Boutique;
        //Evaluation e1 = new Evaluation(1,1,1,now,type,8);
        Reclamation r2 = new Reclamation(2,2,2,"description",null,type);
        
        ReclamationService rs = new ReclamationService();
        
            boolean a = rs.ajouterReclamation(r2);
//          es.ajouterEvaluation(e1);
            a = rs.supprimerReclamation(r2);
//        es.supprimerEvaluation(e2.getId());
    
}
}
