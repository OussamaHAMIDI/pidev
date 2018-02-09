/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Evaluation;
import Entities.Panier;
import IServices.IEvaluation;
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String req = "INSERT INTO evaluation (id_user, id_produit, note , date_creation) values "
                    + "(?,?,?,?)";
        if (evaluation.getType().equals(TypeReclamation.Boutique)){
        req = "INSERT INTO evaluation (id_user, id_boutique, note, date_creation) values "
                    + " ( ? , ? , ? , ? )";
        }
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, evaluation.getUserId());
            ps.setInt(2, evaluation.getProduitOrBoutiqueId());
            ps.setInt(3, evaluation.getNote());
            //ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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
            ps.setInt(1, evaluation.getId());
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
    public boolean supprimerEvaluation(int evaluationId) {
        String req = "Delete from evaluation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, evaluationId);
            ps.executeUpdate(req);
            System.out.println("suppresssion evaluation effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return false;
        }
    }

    @Override
    public Evaluation rechercherEvaluation(int evaluationId) {
        
        Evaluation evaluation = null;
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM evaluation WHERE id = '" + evaluationId + "'");
            if (rs.first()) {
                /*Evaluation = new Evaluation(rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getObject("date_création", LocalDateTime.class),
                        rs.getInt("boutique_id"),
                        rs.getInt("produit_id"),
                        rs.getInt("note"),
                        rechercherEvaluation(rs.getInt("id")));*/
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return evaluation;
    }
    
    
    
}
