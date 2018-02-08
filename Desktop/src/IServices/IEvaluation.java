/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Evaluation;

/**
 *
 * @author benab
 */
public interface IEvaluation {
    
    public boolean ajouterEvaluation(Evaluation evaluation);
    public boolean supprimerEvaluation(Evaluation evaluation);
    public boolean supprimerEvaluation(int evaluationId);
}
