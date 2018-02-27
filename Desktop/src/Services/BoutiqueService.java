/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import IServices.IBoutique;
import com.restfb.types.Photo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javafx.scene.image.Image;

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
    public void ajouterBoutique(Boutique boutique,int idUser) {
        try {
            String req = "INSERT INTO boutique (id_user,nom,date_creation , adresse,longitude,altitude,photo)"
                    + " values ( ? , ? , ? , ? ,?,?,?)"; 
            ps = connexion.prepareStatement(req);
            ps.setInt(1,idUser);
            ps.setString(2, boutique.getNom());
            ps.setObject(3, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setString(4, boutique.getAdresse());
            ps.setString(5, String.valueOf(boutique.getLat()));
            ps.setString(6, String.valueOf(boutique.getLong()));
            ps.setBinaryStream(7, boutique.getPhoto());
            if (boutique.getPhoto()== null){
                ps.setBinaryStream(7,new FileInputStream("src/Images/camera.png"));
            }

            ps.executeUpdate();
           
            System.out.println("L'ajout de la boutique est effectué");
        } catch (SQLException | FileNotFoundException ex) {
            System.out.println("L'ajout de la boutique a échoué " + ex.getMessage());
        }
    }

    @Override
    public void supprimerBoutique(int idBoutique) {
        try {
            String req = " DELETE FROM boutique WHERE id = '" + idBoutique + "'";
            ps = connexion.prepareStatement(req);
            ps.executeUpdate();
            System.out.println("La supression de la boutique est effectuée");
        } catch (SQLException ex) {
            System.out.println("Echec de supression");
        }
    }

    @Override
    public void supprimerBoutique(Boutique boutique) {
        try {
            String req = " DELETE FROM boutique WHERE id = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(req);
            ps.executeUpdate();
            System.out.println("La supression de la boutique est effectuée");
        } catch (SQLException ex) {
            System.out.println("Echec de supression");
        }
    }

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
                User user;
                UserService us = new UserService();
                user = us.getUserById(rs.getInt("id_user"));
                boutique = new Boutique(rs.getInt("id"), user, rs.getString("nom"), rs.getString("adresse"), lireProduitsParBoutique(nom),
                        rs.getObject("date_creation", LocalDateTime.class));
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return boutique;
    }

    @Override
    public Boutique chercherBoutiqueParID(int idBoutique) {
        Boutique boutique = null;
        Boutique boutique2= null;
        try {
            ResultSet rs = this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM boutique WHERE id = '" + idBoutique + "'");
            if (rs.first()) {
                User user;
                UserService us = new UserService();
                ProduitService ps = new ProduitService();
                user = us.getUserById(rs.getInt("id_user"));
//           boutique = new Boutique(idBoutique, user, rs.getString("nom"), rs.getString("adresse"), lireProduitsParBoutique(rs.getString("nom")));
//          boutique2 = new Boutique()     
           boutique = new Boutique(idBoutique, rs.getDouble("longitude"), rs.getDouble("altitude"), user, rs.getString("nom"), rs.getString("adresse"), null,rs.getObject("date_creation", LocalDateTime.class));
           boutique = remplirProduitsParBoutique(boutique);
           boutique2 = new Boutique(idBoutique, user, rs.getString("nom"), rs.getString("adresse"));

               
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return boutique;
    }
    @Override
    public InputStream getPhotoBoutique(int idB) {
        InputStream photo = null;
        try {
            String req = "SELECT photo FROM `boutique` WHERE id = '" + idB + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {

                photo = rs.getBinaryStream("photo");
//                System.out.println("photo retrieved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get photo");

        }
        return photo;
    }

    @Override
    public Image getPhoto(int idB) {
        InputStream photo = null;
        try {
            photo = new FileInputStream("src/Images/camera.png");
            String req = "SELECT photo FROM `boutique` WHERE id = '" + idB + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                photo = rs.getBinaryStream("photo");
                //System.out.println("photo retrieved");
            }
            return new Image(photo);

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(BoutiqueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get photo");

        }
        return new Image(photo);
    }

    @Override
    public List<Boutique> lireBoutiques() {

        List<Boutique> boutiques = new ArrayList<Boutique>();
        try {
            String sql = "SELECT * FROM boutique order by date_creation desc";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
               Boutique b = new Boutique();
                b.setId(rs.getInt("id"));
               // b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
                //b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
                b.setNom(rs.getString("nom"));
                b.setAdresse(rs.getString("adresse"));
                b.setLong(rs.getDouble("longitude"));
                b.setLat(rs.getDouble("altitude"));
                b.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                b.setPhoto(rs.getBinaryStream("photo"));
                boutiques.add(b);
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("Echec lireBoutiques");
        }
        return boutiques;
    }

//    @Override
//    public List<Produit> lireProduitsParBoutique(int idBoutique) {
////        List produits = new ArrayList();
////        try {
////            String sql = "SELECT id_produit FROM boutique WHERE id = '" + idBoutique + "'";
////            ps = connexion.prepareStatement(sql);
////            ResultSet rs = ps.executeQuery();
////            while (rs.next()) {
////                ProduitService pService = new ProduitService();
////                Produit p = pService.chercherProduitParID(rs.getInt("id_produit"));
////                produits.add(p);
////            }
////        } catch (SQLException e) {
////            System.out.println("Echec lireProduitsParBoutique(idBoutique)");
////        }
////        return produits;
//    }

    @Override
    public List<Produit> lireProduitsParBoutique(String nomBoutique) {
        List produits = new ArrayList();
        try {
            String sql = "SELECT p.id from produit p, boutique b WHERE p.id_boutique = b.id and b.nom = '" + nomBoutique + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ProduitService pService = new ProduitService();
            while (rs.next()) {
                Produit p = pService.chercherProduitParID(rs.getInt("id"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec lireProduitsParBoutique(NomBoutique) " + e.getMessage());
        }
        return produits;
    }

    @Override
    public List<Produit> lireProduitsParBoutique(Boutique boutique) {

        List produits = new ArrayList();
        try {
            String sql = "SELECT id FROM produit WHERE id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProduitService pService = new ProduitService();
                Produit p = pService.chercherProduitParID(rs.getInt("id"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec lireProduitsParBoutique(Boutique)");
        }
        return produits;
    }
    
    @Override
    public Boutique remplirProduitsParBoutique(Boutique boutique) {

        List<Produit> produits = new ArrayList<Produit>();
        try {
            String sql = "SELECT id FROM produit WHERE id_boutique = '" + boutique.getId() + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProduitService pService = new ProduitService();
                Produit p = pService.chercherProduitParID(boutique,rs.getInt("id"));
                produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Echec lireProduitsParBoutique(Boutique)");
        }
        boutique.setListProduit(produits);
        return boutique;
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

//    @Override
//    public List<Boutique> chercherBoutiquesParNom(String nom) {
//
//        List boutiques = new ArrayList();
//        try {
//            String sql = "SELECT * FROM boutique WHERE nom = '" + nom + "'";
//            ps = connexion.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            Boutique b = new Boutique();
//            while (rs.next()) {
//                b.setId(rs.getInt("id"));
//                b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
//                b.setNom(rs.getString("nom"));
//                b.setAdresse(rs.getString("adresse"));
//                b.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
//                boutiques.add(b);
//            }
//        } catch (SQLException e) {
//            System.out.println("Echec");
//        }
//        return boutiques;
//    }

    @Override
    public void modifierBoutique(Boutique boutique) {
        String req = "UPDATE boutique SET nom = ? , adresse = ? , photo = ? WHERE id = ?";
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, boutique.getNom());
            ps.setString(2, boutique.getAdresse());
             ps.setBlob(3, boutique.getPhoto());
            ps.setInt(4, boutique.getId());
            ps.executeUpdate();
            System.out.println("La mise a jour de la boutique est effectuée");
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour");
        }
    }

    @Override
    public void modifierNomBoutique(Boutique boutique, String nom) {
        String req = "UPDATE boutique SET nom = ? WHERE id = ?";
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, nom);
            ps.setInt(2, boutique.getId());
            ps.executeUpdate();
            System.out.println("La mise a jour de la boutique est effectuée");
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour");
        }
    }

    @Override
    public void modifierAdresseBoutique(Boutique boutique, String adresse) {
        String req = "UPDATE `boutique` SET nom='ahmed',`adresse`='"+adresse+"' WHERE id='"+boutique.getId()+"'";
        try {
            ps = connexion.prepareStatement(req);
          
            ps.executeUpdate();
            System.out.println("La mise a jour de la boutique est effectuée");
        } catch (SQLException ex) {
            Logger.getLogger(PanierService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de mise a jour");
        }
    }

    @Override
    public void ajouterProduit(Produit p, Boutique boutique) {
        
        try {
            String req = "INSERT INTO produit (reference,libelle,description,prix,taille,couleur,texture,poids,id_boutique) values ( ?,?,?,?,?,?,?,?,?)";
            ps = connexion.prepareStatement(req);
            ps = connexion.prepareStatement(req);
            ps.setString(1, p.getReference());
            ps.setString(2, p.getLibelle());
            ps.setString(3, p.getDescription());
            ps.setFloat(4, p.getPrix());
            ps.setString(5, p.getTaille());
            ps.setString(6, p.getCouleur());
            ps.setString(7, p.getTexture());
            ps.setFloat(8, p.getPoids());
            ps.setInt(9, p.getBoutique().getId());
            ps.executeUpdate();
            System.out.println("Ajout effectué");
        } catch (SQLException ex) {
            System.out.println("L'ajout du produit dans la boutique a échoué " + ex.getMessage());
        }
    }

    @Override
    public List<Boutique> lireBoutique(User user) {
        List<Boutique> boutiques = new ArrayList<Boutique>();
        try {
            String sql = "SELECT * FROM boutique where id_user ='" + user.getId() + "'";
            ps = connexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Boutique b = new Boutique();
                b.setId(rs.getInt("id"));
               // b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
                //b.setListProduit(lireProduitsParBoutique(rs.getInt("id")));
                b.setNom(rs.getString("nom"));
                b.setAdresse(rs.getString("adresse"));
                b.setLong(rs.getDouble("longitude"));
                b.setLat(rs.getDouble("altitude"));
                b.setUser(user);
                b.setDateCreation(rs.getObject("date_creation", LocalDateTime.class));
                boutiques.add(b);
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("Echec lireBoutiques");
        }
        return boutiques;
    }

}
