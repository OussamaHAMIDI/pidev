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
    
    private List<Panier> listPanier;
    private String id;
    private String userId;
    
    public Historique(){
        listPanier = new ArrayList();
    }
    
    public Historique(List listPanier){
        this.listPanier=listPanier;
    }

    public List<Panier> getListPanier() {
        return listPanier;
    }

    public void setListPanier(List<Panier> listPanier) {
        this.listPanier = listPanier;
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
