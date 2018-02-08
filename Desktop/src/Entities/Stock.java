/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.List;

/**
 *
 * @author Azza
 */
public class Stock {
    
    
    private int idBoutique;
    private List<Integer> listIdProduit;
    private List<Integer> listQuantiteeProduit;


    public Stock() {
    }

    public Stock(int idBoutique, List<Integer> listIdProduit, List<Integer> listQuantiteeProduit) {
        this.idBoutique = idBoutique;
        this.listIdProduit = listIdProduit;
        this.listQuantiteeProduit = listQuantiteeProduit;
    }

    public int getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(int idBoutique) {
        this.idBoutique = idBoutique;
    }

    public List<Integer> getListIdProduit() {
        return listIdProduit;
    }

    public void setListIdProduit(List<Integer> listIdProduit) {
        this.listIdProduit = listIdProduit;
    }

    public List<Integer> getListQuantiteeProduit() {
        return listQuantiteeProduit;
    }

    public void setListQuantiteeProduit(List<Integer> listQuantiteeProduit) {
        this.listQuantiteeProduit = listQuantiteeProduit;
    }

    @Override
    public String toString() {
        return "Le stock de la Boutique " + idBoutique + " du produit " + listIdProduit + " et de quantitée " + listQuantiteeProduit ;
    }

   
    
    
    
  
}
