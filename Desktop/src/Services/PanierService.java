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
import java.io.InputStream;
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
public class PanierService implements IPanier {

    Connection connexion; // last_login
    PreparedStatement ps;

    public PanierService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public Panier rechercherPanierById(int id) {
        Panier panier = new Panier();
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
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
                        rs.getString("mode_livraison"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
                        rechercherProduitsPanier(rs.getInt("id")));
                //new ArrayList<ProduitPanier>()
                //);
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
            String addMode = "";
            ModePaiement mode = panier.getModePaiement();
            if (mode == null) {
                addMode = "";
            } else {
                addMode = mode.toString();
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
        String req = "UPDATE panier SET id_user =?,date_creation =?,date_livraison =?,total_ttc =?,frais_livraison =?,statut =?,mode_paiement =?,est_livre =?,est_paye =?"
                + " WHERE id=?";

        try {
            String addMode = "";
            ModePaiement mode = panier.getModePaiement();
            if (mode == null) {
                addMode = "Espece";
            } else {
                addMode = mode.toString();
            }
            ps = connexion.prepareStatement(req);

            ps.setInt(1, panier.getUser().getId());
            ps.setObject(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setObject(3, panier.getDateLivraison());
            ps.setDouble(4, panier.getTotalTTC());
            ps.setDouble(5, panier.getFraisLivraison());
            ps.setString(6, panier.getStatus().toString());
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
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT produit_panier.*,id_boutique FROM produit_panier,produit WHERE produit_panier.id_produit=produit.id and produit_panier.id_panier='" + panierId + "'");
            ProduitService ps = new ProduitService();
            BoutiqueService bs = new BoutiqueService();
            Boutique b = new Boutique();
            while (rs.next()) {
                ProduitPanier x = new ProduitPanier(rs.getBoolean("livree"),
                        rs.getFloat("quantite_vendu"),
                        rs.getFloat("poids_vendu"),
                        rs.getFloat("prix_vente"),
                        rs.getInt("id_produit"),
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("taille"),
                        rs.getString("couleur"),
                        rs.getString("texture"),
                        rs.getFloat("poids"),
                       bs.chercherBoutiqueParID(rs.getInt("id_boutique")),
                        //ps.chercherProduitParID(rs.getInt("id_produit")).getBoutique(),
                        Utils.Utils.getLocalDateTime(rs.getString("date_ajout")),
                        ps.chercherProduitParID(rs.getInt("id_produit")).getPhoto()
                );
                //System.out.println(x);
                produits.add(x);

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
        String req = "UPDATE produit_panier SET reference=?,libelle=?,description=?,prix=?,taille=?,couleur=?,texture=?,poids=?,quantite_Vendu=?,poids_Vendu=?,prix_Vente=? where id_panier=? and id_produit=?";
        try {
            ps = connexion.prepareStatement(req);

            ps.setInt(12, idPanier);
            ps.setInt(13, produit.getId());
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
        List<Panier> paniers = new ArrayList<Panier>();
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
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
                        rs.getString("mode_livraison"),
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
        List<Panier> paniers = new ArrayList<Panier>();
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM panier WHERE id_user = '" + userId + "' AND statut='" + status + "'");
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
                        rs.getString("mode_livraison"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
                        //rechercherProduitsPanier(rs.getInt("id"))
                        new ArrayList<ProduitPanier>())
                );
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }

    @Override
    public String rechercherArtisantProduitPanier(int idproduit) {
        String result = "";
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT CONCAT(\"Nom : \",u.nom,\" \",u.prenom,\" - \",u.username) FROM user u,boutique b, produit p WHERE u.id=b.id_user and b.id=p.id_boutique and p.id ='" + idproduit + "'");
            if (rs.first()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return result;
    }

    @Override
    public String rechercheClientPanier(int idpanier) {
        String result = "";
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT CONCAT(\"Nom :\" ,u.nom,\" \",u.prenom,\" - \" ,u.username) FROM user u,panier WHERE u.id=panier.id_user and panier.id ='" + idpanier + "'");
            if (rs.first()) {
                result = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ProduitPanier> rechercherCommandeArtisant(int userId) {
        List<ProduitPanier> paniers = new ArrayList<ProduitPanier>();
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("Select * from produit_panier where produit_panier.id_produit in (Select produit.id from produit,boutique where produit.id_boutique=boutique.id and boutique.id_user='" + userId + "')");
            while (rs.next()) {
                UserService userGetter = new UserService();
                paniers.add(new ProduitPanier(rs.getBoolean("livree"),
                        rs.getFloat("quantite_vendu"),
                        rs.getFloat("poids_vendu"),
                        rs.getFloat("prix_vente"),
                        rs.getInt("id_produit"),
                        rs.getString("reference"),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("id_panier"),
                        rs.getString("couleur"),
                        rs.getString("texture"),
                        rs.getFloat("poids"),
                        new Boutique(),
                        Utils.Utils.getLocalDateTime(rs.getString("date_ajout")), null
                ));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }

    public int modifierLigneCommande(ProduitPanier produit, int idPanier) {
        String req = "UPDATE produit_panier SET livree=true where id_panier=? and id_produit=?";
        try {
            ps = connexion.prepareStatement(req);

            ps.setFloat(1, idPanier);
            ps.setFloat(2, produit.getId());
            ps.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour ligne commande");
            return 0;
        }
    }

    //Select * from produit_panier where produit_panier.id_produit in (Select produit.id from produit,boutique where produit.id_boutique=boutique.id and boutique.id_user='5')
    @Override
    public void envoyerMailArtisant(Panier panier) {
        Panier temp = panier;
        while (temp.getContenu().size() > 0) {
            List<ProduitPanier> generate = new ArrayList<ProduitPanier>();
            generate.add(temp.getContenu().get(0));
            temp.getContenu().remove(0);
            for (ProduitPanier p : temp.getContenu()) {
                if (generate.get(0).getBoutique().getId() == p.getBoutique().getId()) {
                    generate.add(p);
                    temp.getContenu().remove(p);
                }
            }
            Panier toMailBody = new Panier();
            toMailBody.setContenu(generate);
            // Envoyer mail
            toMailBody.genererMailBody();
        }
    }

    @Override
    public void envoyerMailClient(Panier panier) {
        //Envoyer mail
        panier.genererMailBody();
    }

}
