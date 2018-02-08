/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import IServices.IProduit;
import Entities.Reclamation;
import Entities.Produit;
import IServices.IReclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class ProduitService implements IProduit {

    Connection connexion;
    PreparedStatement ps;
    
    public ProduitService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public boolean ajouterProduit(Produit p) {
        try {
            String req = "INSERT INTO produit (libelle, nombre) values ( '" + p.getLibelle() + "'," + p.getNombre() + " )";
            ps = connexion.prepareStatement(req);
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
    public boolean supprimerProduit(Produit p) {
        try {
            String req = "UPDATE produit SET libelle=?, nombre=? WHERE id=?";
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getLibelle());
            ps.setInt(2, p.getNombre());
            ps.setString(3, p.getId());
            ps.executeUpdate();
            System.out.println("Supression effectuée");
            return true;
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de supression");
            return false;
        }
    }

    @Override
    public Produit chercherProduitParID(String ID) {
        Produit produit = null;
        try {
            ResultSet result = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produit WHERE id = " + ID);
            if (result.first()) {
                produit = new Produit(result.getString("id"), result.getString("libelle"), result.getInt("nombre"));
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur" + ex.getMessage());
        }
        return produit;
    }

    @Override
    public List<Produit> lireProduits() {
        List produits = new ArrayList();
        try {
            String req = "SELECT * FROM produit";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            Produit p = new Produit();
            while (rs.next()) {
                p.setId(rs.getString("id"));
                p.setLibelle(rs.getString("libelle"));
                p.setNombre(rs.getInt("nombre"));
                produits.add(p);
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec");
        }
        return produits;
    }

    @Override
    public boolean modifierProduit(Produit p) {
        try {
            String req = "UPDATE produit SET libelle=?, nombre=? WHERE id=?";
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getLibelle());
            ps.setInt(2, p.getNombre());
            ps.setString(3, p.getId());
            ps.executeUpdate();
             System.out.println("Modification effectuée");
            return true;
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de modification ");
            return false;
        }
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
    public Produit getProduit(String idProduit) {
        
       Produit p = null;
        try {
            String sql = "SELECT *  FROM produit where id_produit = '" + idProduit + "'";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            p = new Produit();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setCouleur(rs.getString("couleur"));
                // a compelelte les autres colonnes
                
                
               
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return p;
    }
}