/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import DataStorage.MyDB;
import Services.ProduitService;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Azza
 */
public class Boutique {

    private int id;
    private Double Long;
    private Double Lat;
    private User user;
    private String nom;
    private String adresse;
    private List<Produit> listProduit;
    private LocalDateTime dateCreation;
     private InputStream photo;

    
        public int getIDUser(){return user.getId();
}

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public Boutique(int id, Double Long, Double Lat, User user, String nom, String adresse, List<Produit> listProduit, LocalDateTime dateCreation) {
        this.id = id;
        this.Long = Long;
        this.Lat = Lat;
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.listProduit = listProduit;
        this.dateCreation = dateCreation;
    }

    public Boutique(int id, User user, String nom, String adresse, List<Produit> listProduit) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.listProduit = listProduit;
    }

    public Boutique(int id, User user, String nom, String adresse) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
    }

    public void setIDUser(int id){this.user.setId(id);}
    public Boutique() {
        user = new User();
    }

    public Double getLong() {
        return Long;
    }

    public void setLong(Double Long) {
        this.Long = Long;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double Lat) {
        this.Lat = Lat;
    }

    public Boutique(User user, String nom, String adresse, List<Produit> listProduit, LocalDateTime dateCreation) {
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.listProduit = listProduit;
        this.dateCreation = dateCreation;
    }

       public Boutique(User user, String nom, String adresse, LocalDateTime dateCreation) {
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.dateCreation = dateCreation;
    }

    public Boutique(User user, String nom, List<Produit> listProduit, LocalDateTime dateCreation) {
        this.user = user;
        this.nom = nom;
        this.listProduit = listProduit;
        this.dateCreation = dateCreation;
    }

    public Boutique(User user, String nom, List<Produit> listProduit) {
        this.user = user;
        this.nom = nom;
        this.listProduit = listProduit;
    }

    public Boutique(User user, String nom, String adresse, List<Produit> listProduit) {
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.listProduit = listProduit;
    }

    public Boutique(int id, User user, String nom, String adresse, List<Produit> listProduit, LocalDateTime dateCreation) {
        this.id = id;
        this.user = user;
        this.nom = nom;
        this.adresse = adresse;
        this.listProduit = listProduit;
        this.dateCreation = dateCreation;
    }

   

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(List<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Boutique other = (Boutique) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
  
//    @Override
//    public String toString() {

    @Override
    public String toString() {
//        return "Boutique{" + "id=" + id + ", Long=" + Long + ", Lat=" + Lat + ", user=" + user + ", nom=" + nom + ", adresse=" + adresse + ", listProduit=" + listProduit + ", dateCreation=" + dateCreation + '}';
        return "Boutique{" + "id=" + id + ", Long=" + Long + ", Lat=" + Lat + ", user=" + user + ", nom=" + nom + ", adresse=" + adresse + ", listProduit=" + listProduit + ", dateCreation=" + dateCreation+'}';
    }

   

//        return "Les informations de la Boutique sont :" + " l'id est " + id + ", le nom est " + nom + ", le produit est " + listProduit + ", la date de Creation est " + dateCreation;
//    }
    public void ajouterProduit(Produit produit) {
        listProduit.add(produit);
    }

    public void supprimerProduit(Produit produit) {
        listProduit.remove(produit);
    }

}
