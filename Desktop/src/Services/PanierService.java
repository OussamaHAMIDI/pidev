/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import IServices.IPanier;
import Utils.Enumerations;
import Utils.Enumerations.ModePaiement;
import Utils.Enumerations.StatusPanier;
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
                UserService userGetter = new UserService();
                
                panier = new Panier(rs.getInt("id"),
                        userGetter.getUserById(rs.getInt("id_user")),
                        rs.getObject("date_creation", LocalDateTime.class),
                        rs.getObject("date_livraison", LocalDateTime.class),
                        rs.getDouble("total_ttc"),
                        rs.getDouble("frais_livraison"),
                        rs.getString("statut"),
                        rs.getString("mode_paiement"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
                        rechercherProduitsPanier(rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return panier;
    }

    @Override
    public int ajouterPanier(Panier panier) {
        
                   String req = "INSERT INTO panier (id_user,date_creation,date_livraison,total_ttc,frais_livraison,statut,mode_paiement,est_livre,est_paye) values "
                    + "(?,?,?,?,?,?,?,?,?)";
       
        try {
            String addMode="";
            ModePaiement mode = panier.getModePaiement();
            if(mode==null)
            {
                addMode="";
            }
            else
            {
               addMode= mode.toString();
            }
            ps = connexion.prepareStatement(req);
            ps.setInt(1, panier.getUser().getId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, panier.getStatus().toString());
            ps.setString(7, addMode);
            ps.setBoolean(8, panier.isEstLivre());
            ps.setBoolean(9, panier.isEstPaye());
            ps.executeUpdate();
return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return 0;
        }
    }

    @Override
    public int miseAJourPanier(Panier panier) {
        String req = "UPDATE panier SET id_user ='?',date_creation ='?',date_livraison ='?',total_ttc ='?',frais_livraison ='?',statut ='?',mode_paiement ='?',est_livre ='?',est_paye ='?'"
                    + " WHERE id='?'";
       
        try {
            String addMode="";
            ModePaiement mode = panier.getModePaiement();
            if(mode==null)
            {
                addMode="";
            }
            else
            {
               addMode= mode.toString();
            }
            ps = connexion.prepareStatement(req);
            
            ps.setInt(1, panier.getUser().getId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, addMode);
            ps.setString(7, panier.getModePaiement().toString());
            ps.setBoolean(8, panier.isEstLivre());
            ps.setBoolean(9, panier.isEstPaye());
            ps.setInt(10, panier.getId());
            ps.executeUpdate();
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
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de suppression");
            return 0;
        }
    }

 

    @Override
    public List<ProduitPanier> rechercherProduitsPanier(int panierId) {

        List<ProduitPanier> produits = new ArrayList<ProduitPanier>();

        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produit_panier WHERE id_panier = '" + panierId + "'");
            ProduitService ps = new ProduitService();
            Boutique b = new Boutique();
            while (rs.next()) {
                ProduitPanier x = new ProduitPanier(rs.getFloat("quantite_vendu"),
                        rs.getFloat("poids_vendu"),
                        rs.getFloat("prix_vente"),
                        rs.getInt("id_produit"),
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getFloat("id_produit"),
                        rs.getString("taille"),
                        rs.getString("couleur"),
                        rs.getString("texture"),
                        rs.getFloat("poids"),
<<<<<<< HEAD
                        bs.chercherBoutiqueParID(rs.getInt("id_boutique")),
                        Utils.Utils.getLocalDateTime(rs.getString("date_ajout")),null
                        ));
=======
                        ps.chercherProduitParID(rs.getInt("id_produit")).getBoutique(),
                        Utils.Utils.getLocalDateTime(rs.getString("date_ajout"))
                        );
                //System.out.println(x);
               produits.add(x);
>>>>>>> 8858ead19622c628a6e7d5a389751cc5ce9f2363
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return produits;
    }

    @Override
    public int ajouterProduitPanier(ProduitPanier produit, int idPanier) {
       
            String req = "INSERT INTO produit_panier (id_panier,id_produit,reference,libelle,description,prix,taille,couleur,texture,poids,quantite_vendu,poids_vendu,prix_vente) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";                                              
              try {
            ps = connexion.prepareStatement(req);
            
            ps.setInt(1, idPanier);
            ps.setInt(2, produit.getId());
            ps.setString(3, produit.getReference());
            ps.setString(4, produit.getLibelle());
            ps.setString(5, produit.getDescription());
            ps.setFloat(6, produit.getPrix());
            ps.setString(7, produit.getTaille());
            ps.setString(8, produit.getCouleur());
            ps.setString(9, produit.getTexture());
            ps.setFloat(10, produit.getPoids());
            ps.setFloat(11, produit.getQuantiteVendue());
            ps.setFloat(12, produit.getPoidsVendu());
            ps.setFloat(13, produit.getPrixVente());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return 0;
        }
    }

    @Override
    public int modifierProduitPanier(ProduitPanier produit, int idPanier) {
         String req = "UPDATE produit_panier SET reference=?,libelle=?,description=?,prix=?,taille=?,couleur=?,texture=?,poids=?,quantite_Vendu=?,poids_Vendu=?,prix_Vendu=? where id_panier=? and id_produit=?";                                              
              try {
            ps = connexion.prepareStatement(req);
            
            ps.setInt(13, idPanier);
            ps.setInt(14, produit.getId());
            ps.setString(1, produit.getReference());
            ps.setString(2, produit.getLibelle());
            ps.setString(3, produit.getDescription());
            ps.setFloat(4, produit.getPrix());
            ps.setString(5, produit.getTaille());
            ps.setString(6, produit.getCouleur());
            ps.setString(7, produit.getTexture());
            ps.setFloat(8, produit.getPoids());
            ps.setFloat(9, produit.getQuantiteVendue());
            ps.setFloat(10, produit.getPoidsVendu());
            ps.setFloat(11, produit.getPrixVente());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour ligne panier");
            return 0;
        }
    }

    @Override
    public int supprimerProduitPanier(int produitId, int idPanier) {
          String req = "Delete from produit_panier where id_produit=?,id_panier=? ";
        try {
            ps = connexion.prepareStatement(req);
            ps.setInt(1, produitId);
            ps.setInt(2, idPanier);
            ps.executeUpdate();
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
                    .executeQuery("SELECT * FROM panier WHERE id_user = '" + userId + "'");
            while (rs.next()) {
                 UserService userGetter = new UserService();
                paniers.add(new Panier(rs.getInt("id"),
                       userGetter.getUserById(rs.getInt("id_user")),
                        rs.getObject("date_creation", LocalDateTime.class),
                        rs.getObject("date_livraison", LocalDateTime.class),
                        rs.getDouble("total_ttc"),
                        rs.getDouble("frais_livraison"),
                        rs.getString("statut"),
                        rs.getString("mode_paiement"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
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
                    .executeQuery("SELECT * FROM panier WHERE id_user = '" + userId + "' AND statut='"+status+"'");
            while (rs.next()) {
                UserService userGetter = new UserService();
                paniers.add(new Panier(rs.getInt("id"),
                    userGetter.getUserById(rs.getInt("id_user")),
                        rs.getObject("date_creation", LocalDateTime.class),
                        rs.getObject("date_livraison", LocalDateTime.class),
                        rs.getDouble("total_ttc"),
                        rs.getDouble("frais_livraison"),
                        rs.getString("statut"),
                        rs.getString("mode_paiement"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
                        rechercherProduitsPanier(rs.getInt("id"))));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }
    
}
