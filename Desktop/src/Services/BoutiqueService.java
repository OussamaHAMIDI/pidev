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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Azza
 */
public class BoutiqueService implements IBoutique{
    Connection connexion;

    @Override
    public void ajouterBoutique(Boutique b) {
        try {
                   String query = "INSERT INTO boutique (nom,produit,date de création ) values ( '" + b.getNom()+ "'," + b.getListProduit()+ "',"+b.getDateCreation() + " )";
                   Statement stm = connexion.createStatement();
                   stm.executeUpdate(query);
                   System.out.println("L'ajout de la boutique est effectué");
               } catch (SQLException ex) {
                   System.out.println("L'ajout de la boutique a échoué");
               }    
    }

    @Override
    public void supprimerBoutique(Boutique b) {
        try {
                   String sql = "UPDATE boutique SET nom=?, produit=?, date de création=? WHERE id=?";
                   PreparedStatement ps = connexion.prepareStatement(sql);
                   ps.setString(1, b.getNom());
                   ps.setProduit(2, b.getListProduit());
                   ps.setObject(3, b.getDateCreation());
                   ps.setInt(4, b.getId());
                   ps.executeUpdate();
                   System.out.println("La supression de la boutique est effectuée");
               } catch (SQLException ex) {
                   System.out.println("Echec de supression");
               } 
    }

    @Override
    public void modifierBoutique(Boutique b) {
        try {
                    String sql = "UPDATE boutique SET nom=?, produit=?, date de création=? WHERE id=?";
                    PreparedStatement ps = connexion.prepareStatement(sql);
                    ps.setString(1, b.getNom());
                    ps.setProduit(2, b.getListProduit());
                    ps.setObject(3, b.getDateCreation());
                    ps.setInt(4, b.getId());
                    ps.executeUpdate();
                    System.out.println("La supression de la boutique est effectuée");
                } catch (SQLException e) {
                    System.out.println("Echec de modification ");
                }
    }

    @Override
    public void ajouterProduit(Produit p, Boutique b) {
        try {
                String query = "INSERT INTO boutique produit values ( '" + b.getListProduit()+ " )";
                Statement stm = connexion.createStatement();
                stm.executeUpdate(query);
                System.out.println("L'ajout du produit est effectué");
            } catch (SQLException ex) {
                System.out.println("L'ajout du produit a échoué");
            }  
    }

    @Override
    public Boutique chercherBoutiqueParNom(String nom) {
        Boutique boutique = null;
                try {
                    ResultSet result = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                            .executeQuery("SELECT * FROM boutique WHERE nom = " + nom);
                    if (result.first()) {
                        boutique = new Boutique(result.getInt("id"), result.getString("nom"), result.getProduit("produit"), result.getObject("date de création"));
                    }
                } catch (SQLException e) {
                    System.out.println("erreur" + e.getMessage());
                }
                return boutique;
    }

    @Override
    public Boutique chercherBoutiqueParID(int Id) {
        Boutique boutique = null;
                try {
                    ResultSet result = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                            .executeQuery("SELECT * FROM boutique WHERE id = " + Id);
                    if (result.first()) {
                        boutique = new Boutique(result.getInt("id"), result.getString("nom"), result.getProduit("produit"), result.getObject("date de création"));
                    }
                } catch (SQLException e) {
                    System.out.println("erreur" + e.getMessage());
                }
                return boutique;
    }

    @Override
    public Boutique chercherBoutiqueParProduit(Produit p) {
        Boutique boutique = null;
                try {
                    ResultSet result = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                            .executeQuery("SELECT * FROM boutique WHERE produit = " + p);
                    if (result.first()) {
                        boutique = new Boutique(result.getInt("id"), result.getString("nom"), result.getProduit("produit"), result.getObject("date de création"));
                    }
                } catch (SQLException e) {
                    System.out.println("erreur" + e.getMessage());
                }
                return boutique;
    }

    @Override
    public List<Boutique> lireBoutique() {
        List boutiques = new ArrayList();
        try {
            String sql = "SELECT * FROM boutique";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Boutique b = new Boutique();
            while (rs.next()) {
                b.setId(rs.getInt("id"));
                b.setListProduit(rs.getProduit("produit"));
                b.setNom(rs.getString("nom"));
                b.setDateCreation((LocalDateTime) rs.getObject("date de création"));
                boutiques.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return boutiques;    }

   
    
}
