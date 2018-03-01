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
import java.util.ArrayList;
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
                ps.setInt(1, reclamation.getProduit().getId());
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

        String req = "Delete from reclamation where id='" + reclamation.getId() +  "'";
        try {
            ps = connexion.prepareStatement(req);
            //ps.setInt(1, reclamation.getId());
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
        
        List<Reclamation> reclamations = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM reclamation WHERE id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setBoutique(boutique);
                reclamation.setProduit(null);
                UserService us = new UserService();
                User u = us.getUserById(rs.getInt("id_user"));
                reclamation.setUser(u);
                reclamation.setType(TypeReclamation.Boutique);
                reclamations.add(reclamation);
            }
            System.out.println("reclamation trouvée ");
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
    
    }

    @Override
    public List<Reclamation> rechercherReclamationProduit(Produit produit) {
        List<Reclamation> reclamations = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM reclamation WHERE id_produit = '" + produit.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setProduit(produit);
                reclamation.setBoutique(null);
                UserService us = new UserService();
                User u = us.getUserById(rs.getInt("id_user"));
                reclamation.setUser(u);
                reclamation.setType(TypeReclamation.Produit);
                reclamations.add(reclamation);
            }
            System.out.println("reclamation trouvée ");
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> rechercherReclamationUser(User user) {
        
        List<Reclamation> reclamations = new ArrayList<>();        
        try {
            String req = "SELECT * FROM reclamation WHERE id_user = '" + user.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setUser(user);
                if(rs.getInt("id_boutique")!=0){
                    BoutiqueService bs = new BoutiqueService();
                    Boutique boutique = bs.chercherBoutiqueParID(rs.getInt("id_boutique"));
                    reclamation.setBoutique(boutique);
                    reclamation.setProduit(null);
                    reclamation.setType(TypeReclamation.Boutique);
                    System.out.println("reclamation trouvée " + reclamation);
                }
                else {
                    //ProduitService ps = new ProduitService();
                    //Produit produit = ps.chercherProduitParID(rs.getInt("id_produit"));
                    //reclamation.setProduit(produit); //en attente de jappa
                    reclamation.setBoutique(null);
                    reclamation.setType(TypeReclamation.Produit);
                }
                
                reclamations.add(reclamation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> rechercherReclamationUserBoutique(User user, Boutique boutique) {
        List<Reclamation> reclamations = new ArrayList<>();        
        try {
            //String req = "SELECT * FROM reclamation WHERE id_user = " + user.getId() + " and id_boutique = " + boutique.getId() + " ";
            String req = "SELECT * FROM reclamation WHERE id_user = '" + user.getId() + "' AND id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("reclamation trouvée ");
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setUser(user);
                reclamation.setBoutique(boutique);
                reclamation.setProduit(null);
                reclamation.setType(TypeReclamation.Boutique);
                reclamations.add(reclamation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> rechercherReclamationUserProduit(User user, Produit produit) {
        
        List<Reclamation> reclamations = new ArrayList<>();        
        try {
            //String req = "SELECT * FROM reclamation WHERE id_user = " + user.getId() + " and id_boutique = " + boutique.getId() + " ";
            String req = "SELECT * FROM reclamation WHERE id_user = '" + user.getId() + "' AND id_produit = '" + produit.getId() + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("reclamation trouvée ");
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setUser(user);
                reclamation.setBoutique(null);
                reclamation.setProduit(produit);
                reclamation.setType(TypeReclamation.Boutique);
                reclamations.add(reclamation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
        
    }

    @Override
    public Reclamation getReclamationById(int reclamationId) {
        Reclamation reclamation = null;
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM reclamation WHERE id = '" + reclamationId + "'");
            if (rs.first()) {
                reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                UserService us = new UserService();
                reclamation.setUser(us.getUserById(rs.getInt("id_user")));
                if(rs.getInt("id_boutique")!=0){
                    BoutiqueService bs = new BoutiqueService();
                    Boutique boutique = bs.chercherBoutiqueParID(rs.getInt("id_boutique"));
                    reclamation.setBoutique(boutique);
                    reclamation.setProduit(null);
                    reclamation.setType(TypeReclamation.Boutique);
                    System.out.println("reclamation trouvée " + reclamation);
                }
                else {
                    ProduitService ps = new ProduitService();
                    Produit produit = ps.chercherProduitParID(rs.getInt("id_produit"));
                    reclamation.setProduit(produit); //en attente de jappa
                    reclamation.setBoutique(null);
                    reclamation.setType(TypeReclamation.Produit);
                }
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return reclamation;
    }

    
    @Override
    public List<Reclamation> getAllReclamations(){
        
        List<Reclamation> reclamations = new ArrayList<>();        
        try {
            String req = "SELECT * FROM reclamation ";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                reclamation.setDescription(rs.getString("description"));
                UserService us = new UserService();
                reclamation.setUser(us.getUserById(rs.getInt("id_user")));
                if(rs.getInt("id_boutique")!=0){
                    BoutiqueService bs = new BoutiqueService();
                    reclamation.setBoutique(bs.chercherBoutiqueParID(rs.getInt("id_boutique")));
                    reclamation.setProduit(null);
                    reclamation.setType(TypeReclamation.Boutique);
                    System.out.println("reclamation trouvée " + reclamation);
                }
                else {
                    ProduitService ps = new ProduitService();
                    Produit produit = ps.chercherProduitParID(rs.getInt("id_produit"));
                    reclamation.setProduit(produit); //en attente de jappa
                    reclamation.setBoutique(null);
                    reclamation.setType(TypeReclamation.Produit);
                }
                
                reclamations.add(reclamation);
            }
            
        } catch (SQLException e) {
            System.out.println("Echec de recherche de reclamation"+e);
        }
        return reclamations;
    }

    
    
}
