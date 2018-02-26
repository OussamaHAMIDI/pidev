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
    private List<Integer> ids;
    private List<Integer> quantites;
    
    public Stock() {
    }

    public Stock(int idBoutique) {
        this.idBoutique = idBoutique;
    }

    public int getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(int idBoutique) {
        this.idBoutique = idBoutique;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getQuantites() {
        return quantites;
    }

    public void setQuantites(List<Integer> quantites) {
        this.quantites = quantites;
    }

    public Stock(int idBoutique, List<Integer> ids, List<Integer> quantites) {
        this.idBoutique = idBoutique;
        this.ids = ids;
        this.quantites = quantites;
    }
    
}
