/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Reclamation;
import IServices.IReclamation;
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        // si le type de la reclamation est boutique, on ajoute l'id de la boutique sinon on ajoute l'id du produit
        String req = "INSERT INTO reclamation (user_id, produit_id, description, date_creation) values "
                    + "(?,?,?,?)";
        if (reclamation.getType().equals(TypeReclamation.Boutique)){
        req = "INSERT INTO reclamation (user_id, boutique_id, description, date_creation) values "
                    + "(?,?,?,?)";
        }
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, reclamation.getUserId());
            ps.setInt(2, reclamation.getProduitOrBoutiqueId());
            ps.setString(3, reclamation.getDescription());
            //ps.setObject(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(4, reclamation.getDateCreation());
            ps.executeUpdate(req);
            System.out.println("Ajout reclamation effectué" + reclamation.getType().toString());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return false;
        }

    }

    @Override
    public boolean supprimerReclamation(int reclamationId) {
       
        String req = "Delete from reclamation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, reclamationId);
            ps.executeUpdate(req);
            System.out.println("suppresssion reclamation effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return false;
        }
    }
    
     @Override
    public boolean supprimerReclamation(Reclamation reclamation) {
       
        String req = "Delete from reclamation where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, reclamation.getId());
            ps.executeUpdate(req);
            System.out.println("suppresssion reclamation effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return false;
        }
    }

    @Override
    public Reclamation rechercherReclamation(int reclamationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
