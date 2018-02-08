/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Produit;
import Entities.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Azza
 */
public class StockService implements IServices.IStock{
    Connection connexion;
    @Override
    public void creerStock(Stock st) {
        try {
         String query = "INSERT INTO stock (produit,quantitée max ) values ( '" + st.getListProduit()+ "'," + st.getQteMax()+" )";
                   Statement stm = connexion.createStatement();
                   stm.executeUpdate(query);
                   System.out.println("L'ajout du nouveau stock est effectué");
               } 
            catch (SQLException ex) {
                   System.out.println("L'ajout du nouveau stock a échoué");
}
    }

    @Override
    public void supprimerStocks(Stock st) {
        try {
                   String sql = "UPDATE stock SET produit=?, quantitée max=? WHERE id=?";
                   PreparedStatement ps = connexion.prepareStatement(sql);
                   ps.setProduit(1, st.getListProduit());
                   ps.setInt(2, st.getQteMax());
                   ps.executeUpdate();
                   System.out.println("La supression du stock est effectuée");
               } catch (SQLException ex) {
                   System.out.println("Echec de supression");
               } 
    }

    @Override
    public void ajouterProduit(Produit pr, Stock st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dimunierProduit(Produit pr, Stock st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
