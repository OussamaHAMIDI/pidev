/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public class Historique {

    private List<Commande> listCommande;
    private List<Panier> listPanier;

    private String id;
    private String userId;

    public Historique() {

        listCommande = new ArrayList();
        listPanier = new ArrayList();
    }

     public Historique(List list) {
        if (list instanceof Commande) {
            this.listCommande = list;
        } else if (list instanceof Panier) {
            this.listPanier = list;
        }

        // LE CONSTRUCTEUR NE DIFFERENCIE PAS ENTRE LES TYPES DE LISTE !!!
        /**
         * ca marche pas comme ca :
         * public Historique(List<Commande> listxxx) {
         */
    }
     
    public List<Commande> getListCommande() {
        return listCommande;
    }

    public void setListCommande(List<Commande> listCommande) {
        this.listCommande = listCommande;
    }

    public List<Panier> getListPanier() {
        return this.listPanier;
    }

    public void setListPanier(List<Commande> listCommande) {
        this.listCommande = listCommande;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

   

}
