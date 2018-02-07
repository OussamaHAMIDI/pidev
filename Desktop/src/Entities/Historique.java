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
    private String id;
    private String userId;
    
    public Historique(){
        listCommande = new ArrayList<Commande>();
    }
    
    public Historique(ArrayList<Commande> listCommande){
        this.listCommande=listCommande;
    }

    public List<Commande> getListPanier() {
        return listCommande;
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
