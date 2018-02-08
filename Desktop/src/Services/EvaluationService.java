/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Evaluation;
import IServices.IEvaluation;
import Utils.Enumerations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benab
 */
public class EvaluationService implements IEvaluation{

    
    Connection connexion;
    PreparedStatement ps;
    
    public EvaluationService() {
        connexion = MyDB.getinstance().getConnexion();
    }
    
    @Override
    public boolean ajouterEvaluation(Evaluation evaluation) {
        
        // si le type de la reclamation est boutique, on ajoute l'id de la boutique sinon on ajoute l'id du produit
        String req = "INSERT INTO evaluation (user_id, produit_id, description, date_creation) values "
                    + "(?,?,?,?)";
        if (evaluation.getType().equals(Enumerations.TypeReclamation.Boutique)){
        req = "INSERT INTO evaluation (user_id, boutique_id, description, date_creation) values "
                    + "(?,?,?,?)";
        }
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, evaluation.getUserId());
            ps.setString(2, evaluation.getProduitOrBoutiqueId());
            ps.setString(3, evaluation.getDescription());
            //ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(4, evaluation.getDateCreation());
            ps.executeUpdate(req);
            System.out.println("Ajout evaluation effectué" + evaluation.getType().toString());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return false;
        }
        
    }

    @Override
    public boolean supprimerEvaluation(Evaluation evaluation) {
        String req = "Delete from evaluation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, evaluation.getId());
            ps.executeUpdate(req);
            System.out.println("suppresssion evaluation effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return false;
        }
    }

    @Override
    public boolean supprimerEvaluation(String evaluationId) {
        String req = "Delete from evaluation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, evaluationId);
            ps.executeUpdate(req);
            System.out.println("suppresssion evaluation effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return false;
        }
    }
    
    
    
}
