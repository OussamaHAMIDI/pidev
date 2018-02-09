/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Azza
 */
public class Stock {
    
    
    private int idBoutique;
    private Map<Integer,Integer> listStock;

    public Stock() {
    }

    public Stock(int idBoutique) {
        this.idBoutique = idBoutique;
    }
    
    public Stock(int idBoutique, Map<Integer, Integer> listStock) {
        this.idBoutique = idBoutique;
        this.listStock = listStock;
    }

    public int getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(int idBoutique) {
        this.idBoutique = idBoutique;
    }

    public Map<Integer, Integer> getListStock() {
        return listStock;
    }

    public void setListStock(Map<Integer, Integer> listStock) {
        this.listStock = listStock;
    }
    
    
    
    
   
    
    
    
  
}
