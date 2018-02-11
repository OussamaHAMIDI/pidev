/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Evaluation;
import Entities.Produit;
import Entities.User;
import IServices.IEvaluation;
import Utils.Enumerations.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
                Logger.getLogger(EvaluationService.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(EvaluationService.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public List<Evaluation> rechercherEvaluationBoutique(Boutique boutique) {
        
        List<Evaluation> evaluations = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM evaluation WHERE id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                evaluation.setNote(rs.getInt("note"));
                evaluation.setBoutique(boutique);
                evaluation.setProduit(null);
                UserService us = new UserService();
                User u = us.getUserById(rs.getInt("id_user"));
                evaluation.setUser(u);
                evaluation.setType(TypeReclamation.Boutique);
                evaluations.add(evaluation);
                System.out.println("evaluation trouvée ");
            }
        } catch (SQLException e) {
            System.out.println("Echec de recherche de evaluation"+e);
        }
        return evaluations;
        
    }

    @Override
    public List<Evaluation> rechercherEvaluationProduit(Produit produit) {
        
        List<Evaluation> evaluations = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM evaluation WHERE id_produit = '" + produit.getIdProduit() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                evaluation.setNote(rs.getInt("note"));
                evaluation.setProduit(produit);
                evaluation.setBoutique(null);
                UserService us = new UserService();
                User u = us.getUserById(rs.getInt("id_user"));
                evaluation.setUser(u);
                evaluation.setType(TypeReclamation.Produit);
                evaluations.add(evaluation);
            }
            System.out.println("evaluation trouvée ");
        } catch (SQLException e) {
            System.out.println("Echec de recherche de evaluation"+e);
        }
        return evaluations;
    
    }

    @Override
    public List<Evaluation> rechercherEvaluationUser(User user) {
        
        List<Evaluation> evaluations = new ArrayList<>();        
        try {
            String req = "SELECT * FROM evaluation WHERE id_user = '" + user.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                evaluation.setNote(rs.getInt("note"));
                evaluation.setUser(user);
                if(rs.getInt("id_boutique")!=0){
                    BoutiqueService bs = new BoutiqueService();
                    Boutique boutique = bs.chercherBoutiqueParID(rs.getInt("id_boutique"));
                    evaluation.setBoutique(boutique);
                    evaluation.setProduit(null);
                    evaluation.setType(TypeReclamation.Boutique);
                    System.out.println("evaluation trouvée " + evaluation);
                }
                else {
                    //ProduitService ps = new ProduitService();
                    //Produit produit = ps.chercherProduitParID(rs.getInt("id_produit"));
                    //reclamation.setProduit(produit); //en attente de jappa
                    evaluation.setBoutique(null);
                    evaluation.setType(TypeReclamation.Produit);
                }
                
                evaluations.add(evaluation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de evaluation"+e);
        }
        return evaluations;
    }

    @Override
    public List<Evaluation> rechercherEvaluationUserBoutique(User user, Boutique boutique) {
        
        List<Evaluation> evaluations = new ArrayList<>();        
        try {
            //String req = "SELECT * FROM reclamation WHERE id_user = " + user.getId() + " and id_boutique = " + boutique.getId() + " ";
            String req = "SELECT * FROM evaluation WHERE id_user = '" + user.getId() + "' AND id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("evaluation trouvée ");
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                evaluation.setNote(rs.getInt("note"));
                evaluation.setUser(user);
                evaluation.setBoutique(boutique);
                evaluation.setProduit(null);
                evaluation.setType(TypeReclamation.Boutique);
                evaluations.add(evaluation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de evaluation"+e);
        }
        return evaluations;
    }

    @Override
    public List<Evaluation> rechercherEvaluationUserProduit(User user, Produit produit) {
        
        List<Evaluation> evaluations = new ArrayList<>();        
        try {
            String req = "SELECT * FROM evaluation WHERE id_user = '" + user.getId() + "' AND id_produit = '" + produit.getIdProduit() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("evaluation trouvée ");
                Evaluation evaluation = new Evaluation();
                evaluation.setId(rs.getInt("id"));
                evaluation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                evaluation.setNote(rs.getInt("note"));
                evaluation.setUser(user);
                evaluation.setBoutique(null);
                evaluation.setProduit(produit);
                evaluation.setType(TypeReclamation.Boutique);
                evaluations.add(evaluation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de evaluation"+e);
        }
        return evaluations;
        
    }

}
