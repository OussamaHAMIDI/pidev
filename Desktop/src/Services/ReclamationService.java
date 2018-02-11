/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import IServices.IReclamation;
import Utils.Enumerations.TypeReclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benab
 */
public class ReclamationService implements IReclamation {

    Connection connexion;
    PreparedStatement ps;

    public ReclamationService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public boolean ajouterReclamation(Reclamation reclamation) {
        String req;
        // si le type de la reclamation est boutique, on ajoute l'id de la boutique sinon on ajoute l'id du produit
        if (reclamation.getType().equals(TypeReclamation.Produit)) {
            req = "INSERT INTO reclamation (id_produit, id_user, description , date_creation) values (?,?,?,?)";
            try {
                ps = connexion.prepareStatement(req);
                ps.setInt(1, reclamation.getProduit().getIdProduit());
                ps.setInt(2, reclamation.getUser().getId());
                ps.setString(3, reclamation.getDescription());
                ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                ps.executeUpdate();
                System.out.println("Ajout reclamation effectué " + reclamation.getType().toString());
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Echec d'ajout");
                return false;
            }
        } else {
            req = "INSERT INTO reclamation (id_boutique, id_user, description, date_creation) values (?,?,?,?)";
            try {
                ps = connexion.prepareStatement(req);
                ps.setInt(1, reclamation.getBoutique().getId());
                ps.setInt(2, reclamation.getUser().getId());
                ps.setString(3, reclamation.getDescription());
                ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                ps.executeUpdate();
                System.out.println("Ajout reclamation effectué " + reclamation.getType().toString());
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Echec d'ajout");
                return false;
            }
        }
    }

    @Override
    public boolean supprimerReclamation(int reclamationId) {

        String req = "Delete from reclamation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, reclamationId);
            ps.executeUpdate();
            System.out.println("suppresssion reclamation effectué ");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression ");
            return false;
        }
    }

    @Override
    public boolean supprimerReclamation(Reclamation reclamation) {

        String req = "Delete from reclamation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, reclamation.getId());
            ps.executeUpdate();
            System.out.println("suppresssion reclamation effectué ");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression ");
            return false;
        }
    }

    @Override
    public List<Reclamation> rechercherReclamationBoutique(Boutique boutique) {
        List<Reclamation> reclamations = null;

        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM reclamation WHERE id_boutique = '" + boutique.getId() + "'");
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setBoutique(boutique);
                reclamation.setProduit(null);
                UserService us = new UserService();
                User u = new User();
                u = us.getUserById(rs.getInt("id_user"));
                reclamation.setUser(u);
                reclamation.setType(TypeReclamation.Boutique);
                
                
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> rechercherReclamationProduit(Produit produit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reclamation> rechercherReclamationUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reclamation> rechercherReclamationUserBoutique(User user, Boutique boutique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reclamation> rechercherReclamationUserProduit(User user, Produit produit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
