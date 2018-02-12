/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Boutique;
import Entities.Evaluation;
import Entities.Produit;
import Entities.User;
import java.util.List;

/**
 *
 * @author benab
 */
public interface IEvaluation {
    
    public boolean ajouterEvaluation(Evaluation evaluation);
    
    public boolean supprimerEvaluation(Evaluation evaluation);
    
    public boolean supprimerEvaluation(int evaluationId);
    
    public Evaluation getEvaluationById(int evaluationId);
    
    public List<Evaluation> rechercherEvaluationBoutique(Boutique boutique);
    
    public List<Evaluation> rechercherEvaluationProduit(Produit produit);
    
    public List<Evaluation> rechercherEvaluationUser (User user);
    
    public List<Evaluation> rechercherEvaluationUserBoutique(User user, Boutique boutique);
    
    public List<Evaluation> rechercherEvaluationUserProduit(User user, Produit produit);
    
    public float getNoteBoutique(Boutique boutique);
    
    public float getNoteProduit(Produit produit);
    
}
