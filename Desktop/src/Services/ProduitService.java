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
            String req = "INSERT INTO produit (reference,libelle,description,prix,taille,couleur,texture,poids,idBoutique) values ( ?,?,?,?,?,?,?,?,?)";                                              
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getReference());
            ps.setString(2, p.getLibelle());
            ps.setString(3, p.getDescription());
            ps.setFloat(4, p.getPrix());
            ps.setString(5, p.getTaille());
            ps.setString(6, p.getCouleur());
            ps.setString(7, p.getTexture());
            ps.setFloat(8, p.getPoids());
            ps.setInt(9, p.getIdBoutique());
            ps.executeUpdate(req);
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
            String req = "UPDATE produit SET  reference=? , libelle=? , description=? , prix=? , taille=? , couleur=? , texture=? , poids=? , idBoutique=? WHERE idProduit=?";
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getReference());
            ps.setString(2, p.getLibelle());
            ps.setString(3, p.getDescription());
            ps.setFloat(4, p.getPrix());
            ps.setString(5, p.getTaille());
            ps.setString(6, p.getCouleur());
            ps.setString(7, p.getTexture());
            ps.setFloat(8, p.getPoids());
            ps.setInt(9, p.getIdBoutique());
            ps.setInt(10, p.getIdProduit());
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
            String req = " DELETE FROM `produit` WHERE idProduit = " + id + "";
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
        try {
            ResultSet result = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produit WHERE idProduit = " + id);
            if (result.first()) {
                return produit = new Produit(result.getInt("idProduit"), result.getString("reference"), result.getString("libelle"), result.getString("description"),result.getFloat("prix"), result.getString("taille"), result.getString("couleur"), result.getString("texture"), result.getFloat("poids"), result.getInt("idBoutique"));
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
            String req = "SELECT * FROM produit WHERE idBoutique = " + idB + "";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            Produit p = new Produit();
            while (rs.next()) {
                p.setIdProduit(rs.getInt("idProduit"));
                p.setReference(rs.getString("reference"));
                p.setLibelle(rs.getString("libelle"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));
                p.setTaille(rs.getString("taille"));
                p.setCouleur(rs.getString("couleur"));
                p.setTexture(rs.getString("texture"));
                p.setPoids(rs.getFloat("poids"));
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


    
}