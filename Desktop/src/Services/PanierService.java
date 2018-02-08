/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import DataStorage.MyDB;
import Entities.Panier;
import Entities.Produit;
import IServices.IPanier;
import Utils.Enumerations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamdi
 */
public class PanierService implements IPanier{

    Connection connexion; // last_login
    PreparedStatement ps;

    public PanierService() {
        connexion = MyDB.getinstance().getConnexion();
    }
    
    @Override
    public int ajouterProduitPanier(Produit prod, Panier pan) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int supprimerProduitPanier(Produit prod, Panier pan) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Panier> rechercherPaniersUtilisateur(String userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Panier> rechercherPaniersUtilisateur(String userId, String status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Panier rechercherPanierById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int ajouterPanier(Panier panier) {
        
                   String req = "INSERT INTO panier (userid,datecreation,datelivraison,totalttc,fraislivraison,status,modepaiement,estlivre,estpaye) values "
                    + "(?,?,?,?,?,?,?,?,?)";
       
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, panier.getUserId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, panier.getStatus());
            ps.setString(7, panier.getModePaiement());
            ps.setBoolean(8, panier.isEstLivre());
            ps.setBoolean(9, panier.isEstPaye());
            ps.executeUpdate(req);
return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return 0;
        }
    }

    @Override
    public int miseAJourPanier(Panier panier) {
        String req = "UPDATE panier SET userid ='?',datecreation ='?',datelivraison ='?',totalttc ='?',fraislivraison ='?',status ='?',modepaiement ='?',estlivre ='?',estpaye ='?'"
                    + " WHERE id='?'";
       
        try {
            ps = connexion.prepareStatement(req);
            
            ps.setString(1, panier.getUserId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, panier.getStatus());
            ps.setString(7, panier.getModePaiement());
            ps.setBoolean(8, panier.isEstLivre());
            ps.setBoolean(9, panier.isEstPaye());
            ps.setString(10, panier.getId());
            ps.executeUpdate(req);
return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour");
            return 0;
        }
    }

    @Override
    public int supprimerPanier(Panier panier) {
        String req = "Delete from panier where id=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, panier.getId());
            ps.executeUpdate(req);
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return 0;
        }
    }

    @Override
    public int ajouterProduitPanier(Produit produit, String idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int supprimerProduitPanier(Produit produit, String idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int modifierProduitPanier(Produit produit, String idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
