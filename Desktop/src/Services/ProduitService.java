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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamdi
 */
public class ProduitService implements IProduit {

    Connection connexion;

    public ProduitService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public void ajouterProduit(Produit p) {
        try {
            String query = "INSERT INTO produit (libelle, nombre) values ( '" + p.getLibelle() + "'," + p.getNombre() + " )";
            Statement stm = connexion.createStatement();
            stm.executeUpdate(query);
            System.out.println("Ajout effectué");
        } catch (SQLException ex) {
            System.out.println("Echec d'ajout");
        }
    }

    @Override
    public void supprimerProduit(Produit p) {
        try {
            String sql = "UPDATE produit SET libelle=?, nombre=? WHERE id=?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, p.getLibelle());
            ps.setInt(2, p.getNombre());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            System.out.println("Supression effectuée");
        } catch (SQLException ex) {
            System.out.println("Echec de supression");
        }
    }

    @Override
    public Produit chercherProduitParID(int ID) {
        Produit produit = null;
        try {
            ResultSet result = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM produit WHERE id = " + ID);
            if (result.first()) {
                produit = new Produit(result.getInt("id"), result.getString("libelle"), result.getInt("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return produit;
    }

    @Override
    public List<Produit> lireProduits() {
        List produits = new ArrayList();
        try {
            String sql = "SELECT * FROM produit";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Produit p = new Produit();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setLibelle(rs.getString("libelle"));
                p.setNombre(rs.getInt("nombre"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return produits;
    }

    @Override
    public void modifierProduit(Produit p) {
        try {
            String sql = "UPDATE produit SET libelle=?, nombre=? WHERE id=?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, p.getLibelle());
            ps.setInt(2, p.getNombre());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Echec de modification ");
        }
    }

    @Override
    public String getNextId() {
        String nextid = "";
        try {
            String sql = "SHOW TABLE STATUS LIKE 'produit'";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nextid = rs.getString("Auto_increment");
            }
        } catch (SQLException e) {
            System.out.println("Echec get Next ID ");
        }
        return nextid;
    }

}
