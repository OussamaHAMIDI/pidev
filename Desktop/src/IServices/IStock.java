/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Produit;
import Entities.Stock;

/**
 *
 * @author Azza
 */
public interface IStock {
    
    public void getStock (int idBoutique);
    public void ajouterProduit (Produit produit,int idBoutique);
    public void dimunierProduit (Produit produit,int idBoutique);
    
}
