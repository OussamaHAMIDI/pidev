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
    
    public void creerStock (Stock st);
    public void supprimerStocks (Stock st);
    public void ajouterProduit (Produit pr,Stock st);
    public void dimunierProduit (Produit pr,Stock st);
    
}
