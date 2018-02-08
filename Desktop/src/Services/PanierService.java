/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import DataStorage.MyDB;
import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import IServices.IPanier;
import Utils.Enumerations;
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
 * @author Hamdi
 */
public class PanierService implements IPanier{

    Connection connexion; // last_login
    PreparedStatement ps;

    public PanierService() {
        connexion = MyDB.getinstance().getConnexion();
    }
    
  

  

    @Override
    public Panier rechercherPanierById(int id) {
        Panier panier = null;
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM panier WHERE id = '" + id + "'");
            if (rs.first()) {
                panier = new Panier(rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getObject("datecreation", LocalDateTime.class),
                        rs.getObject("datelivraison", LocalDateTime.class),
                        rs.getDouble("totalttc"),
                        rs.getDouble("fraislivraison"),
                        rs.getString("status"),
                        rs.getString("modepaiement"),
                        rs.getBoolean("estlivre"),
                        rs.getBoolean("estpaye"),
                        rechercherProduitsPanier(rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return panier;
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
            
            ps.setInt(1, panier.getUserId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, panier.getStatus());
            ps.setString(7, panier.getModePaiement());
            ps.setBoolean(8, panier.isEstLivre());
            ps.setBoolean(9, panier.isEstPaye());
            ps.setInt(10, panier.getId());
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
            ps.setInt(1, panier.getId());
            ps.executeUpdate(req);
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return 0;
        }
    }

 

    @Override
    public List<Produit> rechercherProduitsPanier(int panierId) {
        List<Produit> produits = null;
        
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produitpanier WHERE id_panier = '" + panierId + "'");
            while (rs.next()) {
                produits.add(new ProduitPanier(rs.getInt("idproduit"),
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getFloat("idproduit"),
                        rs.getString("taille"),
                        rs.getString("couleur"),
                        rs.getString("texture"),
                        rs.getFloat("poids"),
                        rs.getFloat("quantitevendu"),
                        rs.getFloat("poidsvendu"),
                        rs.getFloat("prixvente")));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return produits;
    }

    @Override
    public int ajouterProduitPanier(ProduitPanier produit, int idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int modifierProduitPanier(ProduitPanier produit, int idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int supprimerProduitPanier(int produitId, int idPanier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  @Override
    public List<Panier> rechercherPaniersUtilisateur(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Panier> rechercherPaniersUtilisateur(int userId, String status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
