/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Commande;
import Entities.Panier;
import Entities.Produit;
import Entities.User;
import IServices.ICommande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public class CommandeService implements ICommande{

    Connection connexion;

    public CommandeService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public void ajouterCommande(Commande c) {
        try {
            String query = "INSERT INTO commande (id, user_id, panier_id, statut, date_creation, date_livraison, date_paiement) values ( '" + 
                    c.getId() + "'," + c.getUser().getId()  + "'," + c.getPanier().getId()  + "'," + c.getStatut()  + 
                    "'," + c.getDateCreation()  + "'," + c.getDateLivraison()  + "'," + c.getDatePaiement() + " )";
            Statement stm = connexion.createStatement();
            stm.executeUpdate(query);
            System.out.println("Ajout effectué");
        } catch (SQLException ex) {
            System.out.println("Echec d'ajout");
        }
    }

    @Override
    public void modifierCommande(Commande c) {
        try {
            String sql = "UPDATE commande SET user_id=?, panier_id=?, statut=?, date_creation=?, date_livraison=?, date_paiement=? WHERE id=?";
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.setString(1, c.getUser().getId());
            ps.setString(2, c.getPanier().getId());
            ps.setString(3, c.getStatut());
            ps.setDate(4,c.getDateCreation());
            ps.setDate(5,c.getDateLivraison());
            ps.setDate(3,c.getDatePaiement());
            
            ps.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException ex) {
            System.out.println("Echec de modification");
        }
    }

    @Override
    public Commande chercherCommandeParID(String id) {
        try {
            
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM commande WHERE id = " + id);
            if (rs.first()) {
                Commande c = new Commande();
                c.setId(rs.getString("id"));
                c.setStatut(rs.getString("statut"));
                c.setDateCreation(rs.getDate("date_creation"));
                c.setDateLivraison(rs.getDate("date_livraison"));
                c.setDatePaiement(rs.getDate("date_paiement"));
                ResultSet rs3 = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("select * from user where id =" + rs.getString("user_id"));
                User user = new User();
                user.setId(rs3.getString("id"));
                c.setUser(user);
                // remplire
                
                String sql2 = "Select * from panier where id =" + rs.getString("panier_id") ;
                PreparedStatement ps2 = connexion.prepareStatement(sql2);
                ResultSet rs2 = ps2.executeQuery();
                Panier panier= new Panier();
                rs2.next();
                // Remplire les attributs
                panier.setId(rs2.getString("id"));
                c.setPanier(panier);
                return c;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Commande> getCommandes(User user) {
        List produits = new ArrayList();
        try {
            String sql = "SELECT * FROM commande where user_id = "+ user.getId();
            PreparedStatement ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Commande> commandes = new ArrayList();
            while (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getString("id"));
                c.setStatut(rs.getString("statut"));
                c.setDateCreation(rs.getDate("date_creation"));
                c.setDateLivraison(rs.getDate("date_livraison"));
                c.setDatePaiement(rs.getDate("date_paiement"));
                c.setUser(user);
                String sql2 = "Select * from panier where id =" + rs.getString("panier_id") ;
                PreparedStatement ps2 = connexion.prepareStatement(sql2);
                ResultSet rs2 = ps2.executeQuery();
                Panier panier= new Panier();
                rs2.next();
                // Remplire les attributs
                panier.setId(rs2.getString("id"));
                c.setPanier(panier);
                
                commandes.add(c);
               
            }
            return commandes;
        } catch (SQLException e) {
            System.out.println("Echec");
        }
        return produits;
    }

    @Override
    public void supprimerCommande(Commande c) {
        try {
            String sql = "delete from commande WHERE id=" + c.getId();
            PreparedStatement ps = connexion.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Echec de suppression ");
        }
    }
    
}
