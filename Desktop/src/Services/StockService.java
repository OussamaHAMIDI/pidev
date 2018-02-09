/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Produit;
import Entities.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Azza
 */
public class StockService implements IServices.IStock{
    Connection connexion;
    PreparedStatement ps;
    
    public StockService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public Stock getStock(int idBoutique) 
    {
        Stock stock = new Stock(idBoutique);
        try {
            String req = "SELECT * FROM stock WHERE idBoutique = " + idBoutique + "";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stock.getListStock().put(rs.getInt("idProduit"), rs.getInt("quantite"));
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec");
        }
        return stock;
    }

    @Override
    public Stock modifierStock(int idProduit, Stock stock, int qte) 
    {
        try {
            String req = "UPDATE stock SET  quantite=quantite + ? WHERE idBoutique=? and idProduit=?";
            ps = connexion.prepareStatement(req);
            ps.setInt(1, qte);
            ps.setInt(2, stock.getIdBoutique());
            ps.setInt(3, idProduit);
            ps.executeUpdate();
            System.out.println("Modification effectuée");
            stock.getListStock().replace(idProduit,stock.getListStock().get(idProduit)+qte);
            return stock;
        } catch (SQLException ex) {
             Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec de modification");
            return stock;
        }
    }

    
    
}
