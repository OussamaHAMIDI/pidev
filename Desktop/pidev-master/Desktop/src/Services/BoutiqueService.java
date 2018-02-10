/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Produit;
import IServices.IBoutique;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// manque adreesse fel base !!!!
/**
 *
 * @author Azza
 */
public class BoutiqueService implements IBoutique {

    Connection connexion;
    PreparedStatement ps;

    public BoutiqueService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public void ajouterBoutique(Boutique boutique) {
        try {
            String req = "INSERT INTO boutique (id_user,nom,date_creation , adresse) values ( ? , ? , ? , ? )"; // manque adresse fel base 
            ps = connexion.prepareStatement(req);
            ps.setInt(1, boutique.getUserId());
            ps.setString(2, boutique.getNom());
            ps.setObject(3, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setString(4, boutique.getAdresse());
           
            ps.executeUpdate();
            System.out.println("L'ajout de la boutique est effectué");
        } catch (SQLException ex) {
            System.out.println("L'ajout de la boutique a échoué " + ex.getMessage());
        }
    }

    @Override
    public void supprimerBoutique(int idBoutique) {
        try {
            String req = " DELETE FROM `boutique` WHERE `id` = '" + idBoutique + "'";
            ps = connexion.prepareStatement(req);
            ps.executeUpdate();
            System.out.println("La supression de la boutique est effectuée");
        } catch (SQLException ex) {
            System.out.println("Echec de supression");
        }
    }

//    @Override
//    public void modifierBoutique(Boutique boutique) {
//        try {
//            String sql = "UPDATE boutique SET nom=?, produit=?, date_creation=? WHERE id=?";
//            ps = connexion.prepareStatement(sql);
//            ps.setString(1, boutique.getNom());
//            ps.setProduit(2, boutique.getListProduit());
//            ps.setObject(3, boutique.getDateCreation());
//            ps.setInt(4, boutique.getId());
//            ps.executeUpdate();
//            System.out.println("La supression de la boutique est effectuée");
//        } catch (SQLException e) {
//            System.out.println("Echec de modification ");
//        }
//    }

    @Override
    public void ajouterProduit(int idProduit, int idBoutique) {
        try {
            String query = "INSERT INTO `produit_boutique`(`id_boutique`, `id_produit`, `date_ajout`) VALUES ( ?, ?, ?)";

            Statement stm = connexion.createStatement();
            ps.setInt(1, idProduit);
            ps.setInt(2, idBoutique);
            ps.setObject(3, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            System.out.println(query);
            stm.executeUpdate(query);
            System.out.println("L'ajout du produit est effectué");
        } catch (SQLException ex) {
            System.out.println("L'ajout du produit a échoué " + ex.getMessage());
        }
    }

    @Override
    public Boutique chercherBoutiqueParNom(String nom) { // add unique field to nom dans la table boutique
        Boutique boutique = null;
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM boutique WHERE nom = '" + nom + "'");
            if (rs.first()) {
                boutique = new Boutique(rs.getInt("id"), rs.getString("nom"), lireProduitsParBoutique(nom),
                        ((LocalDateTime) rs.getObject("date_creation")),rs.getString("adresse"));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return boutique;
    }

    @Override
    public Boutique chercherBoutiqueParID(int idBoutique) {
        Boutique boutique = null;
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM boutique WHERE id = '" + idBoutique + "'");
            if (rs.first()) {
                boutique = new Boutique(rs.getInt("id"), rs.getString("nom"), lireProduitsParBoutique(idBoutique),
                        ((LocalDateTime) rs.getObject("date_creation")),rs.getString("adresse"));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return boutique;
    }

    @Override
    public List<Boutique> lireBoutiques() {

        List boutiques = new ArrayList();
        try {
            String sql = "SELECT * FROM boutique";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Boutique b = new Boutique();
            while (rs.next()) {
                b.setId(rs.getInt("id"));
                b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
                b.setNom(rs.getString("nom"));
                b.setAdresse(rs.getString("adresse"));
                b.setDateCreation((LocalDateTime) rs.getObject("date_de_creation"));
                boutiques.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return boutiques;
    }

    @Override
    public List<Produit> lireProduitsParBoutique(int idBoutique) {
        List produits = new ArrayList();
        try {
            String sql = "SELECT id_produit FROM boutique WHERE boutique_id = '" + idBoutique + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProduitService pService = new ProduitService();
                Produit p = pService.chercherProduitParID(rs.getInt("id_produit"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return produits;
    }

    @Override
    public List<Produit> lireProduitsParBoutique(String nomBoutique) {
        List produits = new ArrayList();
        try {
            String sql = "SELECT id_produit FROM boutique WHERE nom = '" + nomBoutique + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProduitService pService = new ProduitService();
                Produit p = pService.chercherProduitParID(rs.getInt("id_produit"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return produits;
    }

     @Override
    public int getNextId() {
        int nextid = - 1;
        try {
            String req = "SHOW TABLE STATUS LIKE 'boutique'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nextid = rs.getInt("Auto_increment");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get Next ID ");
        }
        return nextid;
    }

   

}
