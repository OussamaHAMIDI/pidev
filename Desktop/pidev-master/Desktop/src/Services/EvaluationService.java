/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Evaluation;
import IServices.IEvaluation;
import Utils.Enumerations.*;
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
public class EvaluationService implements IEvaluation {

    Connection connexion;
    PreparedStatement ps;

    public EvaluationService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public boolean ajouterEvaluation(Evaluation evaluation) {
        String req;
        // si le type de la reclamation est boutique, on ajoute l'id de la boutique sinon on ajoute l'id du produit
        if (evaluation.getType().equals(TypeReclamation.Produit)) {
            req = "INSERT INTO evaluation (id_produit, id_user, note , date_creation) values (?,?,?,?)";
            try {
                ps = connexion.prepareStatement(req);
                ps.setInt(1, evaluation.getProduit().getIdProduit());
                ps.setInt(2, evaluation.getUser().getId());
                ps.setInt(3, evaluation.getNote());
                ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                ps.executeUpdate();
                System.out.println("Ajout evaluation effectué " + evaluation.getType().toString());
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Echec d'ajout");
                return false;
            }
        } else {
            req = "INSERT INTO evaluation (id_boutique, id_user, note, date_creation) values (?,?,?,?)";
            try {
                ps = connexion.prepareStatement(req);
                ps.setInt(1, evaluation.getBoutique().getId());
                ps.setInt(2, evaluation.getUser().getId());
                ps.setInt(3, evaluation.getNote());
                ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                ps.executeUpdate();
                System.out.println("Ajout evaluation effectué " + evaluation.getType().toString());
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Echec d'ajout");
                return false;
            }
        }
    }

    @Override
    public boolean supprimerEvaluation(Evaluation evaluation) {
        String req = "Delete from evaluation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, evaluation.getId());
            ps.executeUpdate();
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
            ps.executeUpdate();
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
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
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
