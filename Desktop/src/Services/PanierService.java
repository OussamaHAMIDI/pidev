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
                produits.add(new ProduitPanier(rs.getFloat("quantitevendu"),
                        rs.getFloat("poidsvendu"),
                        rs.getFloat("prixvente"),rs.getInt("idproduit"),
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getFloat("idproduit"),
                        rs.getString("taille"),
                        rs.getString("couleur"),
                        rs.getString("texture"),
                        rs.getFloat("poids")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return produits;
    }

    @Override
    public int ajouterProduitPanier(ProduitPanier produit, int idPanier) {
       
            String req = "INSERT INTO produitpanier (idpanier,idproduit,reference,libelle,description,prix,taille,couleur,texture,poids,idBoutique,quantiteVendu,poidsVendu,prixVendu) values (?,?,?,?,?,?,?,?,?,?,?,?)";                                              
              try {
            ps = connexion.prepareStatement(req);
            
            ps.setInt(1, idPanier);
            ps.setInt(2, produit.getIdProduit());
            ps.setString(3, produit.getReference());
            ps.setString(4, produit.getLibelle());
            ps.setString(5, produit.getDescription());
            ps.setFloat(6, produit.getPrix());
            ps.setString(7, produit.getTaille());
            ps.setString(8, produit.getCouleur());
            ps.setString(9, produit.getTexture());
            ps.setFloat(10, produit.getPoids());
            ps.setInt(11, produit.getIdBoutique());
            ps.setFloat(12, produit.getQuantiteVendue());
            ps.setFloat(13, produit.getPoidsVendu());
            ps.setFloat(14, produit.getPrixVente());
            ps.executeUpdate(req);
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return 0;
        }
    }

    @Override
    public int modifierProduitPanier(ProduitPanier produit, int idPanier) {
         String req = "UPDATE produitpanier SET reference=?,libelle=?,description=?,prix=?,taille=?,couleur=?,texture=?,poids=?,idBoutique=?,quantiteVendu=?,poidsVendu=?,prixVendu=? where idpanier=? and idproduit=?";                                              
              try {
            ps = connexion.prepareStatement(req);
            
            ps.setInt(13, idPanier);
            ps.setInt(14, produit.getIdProduit());
            ps.setString(1, produit.getReference());
            ps.setString(2, produit.getLibelle());
            ps.setString(3, produit.getDescription());
            ps.setFloat(4, produit.getPrix());
            ps.setString(5, produit.getTaille());
            ps.setString(6, produit.getCouleur());
            ps.setString(7, produit.getTexture());
            ps.setFloat(8, produit.getPoids());
            ps.setInt(9, produit.getIdBoutique());
            ps.setFloat(10, produit.getQuantiteVendue());
            ps.setFloat(11, produit.getPoidsVendu());
            ps.setFloat(12, produit.getPrixVente());
            ps.executeUpdate(req);
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour ligne panier");
            return 0;
        }
    }

    @Override
    public int supprimerProduitPanier(int produitId, int idPanier) {
          String req = "Delete from produitpanier where idproduit=?,idpanier=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, produitId);
            ps.setInt(2, idPanier);
            ps.executeUpdate(req);
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression ligne produit");
            return 0;
        }
    }

  @Override
    public List<Panier> rechercherPaniersUtilisateur(int userId) {
        List<Panier> paniers = null;
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM panier WHERE userid = '" + userId + "'");
            while (rs.next()) {
                paniers.add(new Panier(rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getObject("datecreation", LocalDateTime.class),
                        rs.getObject("datelivraison", LocalDateTime.class),
                        rs.getDouble("totalttc"),
                        rs.getDouble("fraislivraison"),
                        rs.getString("status"),
                        rs.getString("modepaiement"),
                        rs.getBoolean("estlivre"),
                        rs.getBoolean("estpaye"),
                        rechercherProduitsPanier(rs.getInt("id"))));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }

    @Override
    public List<Panier> rechercherPaniersUtilisateur(int userId, String status) {
        List<Panier> paniers = null;
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM panier WHERE userid = '" + userId + "' AND status='"+status+"'");
            while (rs.next()) {
                paniers.add(new Panier(rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getObject("datecreation", LocalDateTime.class),
                        rs.getObject("datelivraison", LocalDateTime.class),
                        rs.getDouble("totalttc"),
                        rs.getDouble("fraislivraison"),
                        rs.getString("status"),
                        rs.getString("modepaiement"),
                        rs.getBoolean("estlivre"),
                        rs.getBoolean("estpaye"),
                        rechercherProduitsPanier(rs.getInt("id"))));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }
    
}
