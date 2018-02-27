/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import IServices.IProduit;
import Entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oussamahamidi
 */
public class ProduitService implements IProduit {

    Connection connexion;
    PreparedStatement ps;
    
    public ProduitService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public boolean ajouterProduit(Produit p) {
        try {

            String req = "INSERT INTO `produit`(`reference`, `libelle`, `description`, `prix`, `taille`, `couleur`, `texture`, `poids`, `date_creation`, `photo`) VALUES ( ?,?,?,?,?,?,?,?,?,?)";                                              
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getReference());
            ps.setString(2, p.getLibelle());
            ps.setString(3, p.getDescription());
            ps.setFloat(4, p.getPrix());
            ps.setString(5, p.getTaille());
            ps.setString(6, p.getCouleur());
            ps.setString(7, p.getTexture());
            ps.setFloat(8, p.getPoids());
            ps.setString(9,LocalDateTime.now().toString());
            ps.setBinaryStream(10, p.getPhoto());
            ps.executeUpdate();
            System.out.println("Ajout effectué");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec d'ajout");
            return false;
        }
    }

    @Override
    public boolean modifierProduit(Produit p) {
        try {
            String req = "UPDATE produit SET  reference=? , libelle=? , description=? , prix=? , taille=? , couleur=? , texture=? , poids=? , photo=? WHERE id=?";
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getReference());
            ps.setString(2, p.getLibelle());
            ps.setString(3, p.getDescription());
            ps.setFloat(4, p.getPrix());
            ps.setString(5, p.getTaille());
            ps.setString(6, p.getCouleur());
            ps.setString(7, p.getTexture());
            ps.setFloat(8, p.getPoids());
            ps.setBinaryStream(9, p.getPhoto());
            ps.setInt(10, p.getId());
            ps.executeUpdate();
            System.out.println("Modification effectuée");
            return true;
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de modification");
            return false;
        }
    }
    @Override
    public boolean supprimerProduit(int id) {
        try {
            String req = " DELETE FROM `produit` WHERE id = " + id + "";
            ps = connexion.prepareStatement(req);
            ps.executeUpdate();
            System.out.println("La supression de la boutique est effectuée");
            return true;
        } catch (SQLException ex) {
            System.out.println("Echec de supression");
        }
        return false;
    }

    @Override
    public Produit chercherProduitParID(int id) {
        Produit produit = null;
        BoutiqueService bs= new BoutiqueService();
        try {
            ResultSet result = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produit WHERE id = " + id);
            if (result.first()) {

                produit = new Produit(result.getInt("id"), result.getString("reference"), result.getString("libelle"), result.getString("description"),result.getFloat("prix"), result.getString("taille"), result.getString("couleur"), result.getString("texture"), result.getFloat("poids"), /*bs.chercherBoutiqueParID(result.getInt("id_boutique"))*/ null,Utils.Utils.getLocalDateTime(result.getString("date_creation")),result.getBinaryStream("photo"));
                return produit;
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur" + ex.getMessage());
        }
        return produit;
    }

    @Override
    public List<Produit> listerProduitsBoutique(int idB) {
        List produits = new ArrayList();
        try {
            String req = "SELECT * FROM produit WHERE id_boutique = " + idB + "";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            Produit p = new Produit();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setReference(rs.getString("reference"));
                p.setLibelle(rs.getString("libelle"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setTaille(rs.getString("taille"));
                p.setCouleur(rs.getString("couleur"));
                p.setTexture(rs.getString("texture"));
                p.setPoids(rs.getFloat("poids"));
                p.setPhoto(rs.getBinaryStream("photo"));
                produits.add(p);
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec");
        }
        return produits;
    }


    @Override
    public String getNextId() {
        String nextid = "";
        try {
            String req = "SHOW TABLE STATUS LIKE 'produit'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nextid = rs.getString("Auto_increment");
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get Next ID ");
        }
        return nextid;
    }

    @Override
    public List<Produit> listerProduits() {
        List<Produit> produits = new ArrayList();
        try {
            String req = "SELECT * FROM produit";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setReference(rs.getString("reference"));
                p.setLibelle(rs.getString("libelle"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setTaille(rs.getString("taille"));
                p.setCouleur(rs.getString("couleur"));
                p.setTexture(rs.getString("texture"));
                p.setPoids(rs.getFloat("poids"));
                p.setPhoto(rs.getBinaryStream("photo"));
                produits.add(p);
                p= new Produit();
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec");
        }
        return produits;
    }


}