/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Panier;
import Entities.User;
import IServices.IHistorique;
import Utils.Enumerations;
import Utils.Enumerations.StatusPanier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public class HistoriqueService implements IHistorique {

    Connection connexion; // last_login
    PreparedStatement ps;

    public HistoriqueService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public List<Panier> getHistoriqueUser(User user) {
        List<Panier> paniers = new ArrayList<Panier>();
        PanierService ps = new PanierService();
        UserService userGetter = new UserService();
        try {
            ResultSet rs= this.connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM panier WHERE id_user = '" + user.getId() + "' and statut = 'Valide' ");
            while (rs.next()) {
                paniers.add(new Panier(rs.getInt("id"),
                    userGetter.getUserById(rs.getInt("id_user")),
                        rs.getObject("date_creation", LocalDateTime.class),
                        rs.getObject("date_livraison", LocalDateTime.class),
                        rs.getDouble("total_ttc"),
                        rs.getDouble("frais_livraison"),
                        rs.getString("statut"),
                        rs.getString("mode_paiement"),
                        rs.getString("mode_livraison"),
                        rs.getBoolean("est_livre"),
                        rs.getBoolean("est_paye"),
                        ps.rechercherProduitsPanier(rs.getInt("id"))));
                System.out.println(paniers);
            }
        } catch (SQLException e) {
            System.out.println("erreur" + e.getMessage());
        }
        return paniers;
    }
}
