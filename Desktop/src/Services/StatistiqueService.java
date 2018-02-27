/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import IServices.IStatistique;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author benab
 */
public class StatistiqueService implements IStatistique {

    Connection connexion;
    PreparedStatement ps;

    public StatistiqueService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public int getNombreClients() {
        String sql = "SELECT count(*) FROM user WHERE type = 'Client'";
        int i = 0;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                i = result.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public int getNombreArtisans() {
        String sql = "SELECT count(*) FROM user WHERE type = 'Artisan'";
        int i = 0;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                i = result.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public int getNombreAdministrateurs() {
        String sql = "SELECT count(*) FROM user WHERE type = 'Administrateur'";
        int i = 0;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                i = result.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public List<Produit> getTopTenProduits() {
        ProduitService ps = new ProduitService();
        List<Produit> produits = ps.listerProduits();
        List<Produit> topTenProduits = new ArrayList<Produit>();
        EvaluationService es = new EvaluationService();
        Map<Float, ArrayList<Produit>> noteProduit = new TreeMap<Float, ArrayList<Produit>>(Collections.reverseOrder());
        Float note;
        for (Produit p : produits) {
            ArrayList<Produit> prd = new ArrayList<Produit>();
            note = es.getNoteProduit(p);
            if (noteProduit.containsKey(note)) {
                prd = noteProduit.get(note);
                prd.add(p);
                noteProduit.replace(note, prd);
            } else {
                prd.add(p);
                noteProduit.put(note, prd);
            }
        }
        Iterator i = noteProduit.keySet().iterator();
        Float cle;
        ArrayList<Produit> valeur;
        int j = 0;
        while (j < 10) {
            if (i.hasNext()) {
                cle = (Float) i.next();
                valeur = (ArrayList<Produit>) noteProduit.get(cle);
                System.out.println("LA NOTE EST : " + cle);
                System.out.println("LE PRODUIT EST : " + valeur);
                for (Produit p : valeur) {
                    j++;
                    topTenProduits.add(p);
                }
            } else {
                break;
            }
        }
        return topTenProduits;
    }

    @Override
    public List<Boutique> getTopTenBoutiques() {
        BoutiqueService bs = new BoutiqueService();
        List<Boutique> boutiques = bs.lireBoutiques();
        List<Boutique> topTenBoutiques = new ArrayList<Boutique>();
        EvaluationService es = new EvaluationService();
        Map<Float, ArrayList<Boutique>> noteBoutique = new TreeMap<Float, ArrayList<Boutique>>(Collections.reverseOrder());
        Float note;
        for (Boutique b : boutiques) {
            ArrayList<Boutique> btq = new ArrayList<Boutique>();
            note = es.getNoteBoutique(b);
            if (noteBoutique.containsKey(note)) {
                btq = noteBoutique.get(note);
                btq.add(b);
                noteBoutique.replace(note, btq);
            } else {
                btq.add(b);
                noteBoutique.put(note, btq);
            }
        }
        Iterator i = noteBoutique.keySet().iterator();
        Float cle;
        ArrayList<Boutique> valeur;
        int j = 0;
        while (j < 10) {
            if (i.hasNext()) {
                cle = (Float) i.next();
                valeur = (ArrayList<Boutique>) noteBoutique.get(cle);
                System.out.println("LA NOTE EST : " + cle);
                System.out.println("LA BOUTIQUE EST : " + valeur);
                for (Boutique b : valeur) {
                    j++;
                    topTenBoutiques.add(b);
                }
            } else {
                break;
            }
        }
        return topTenBoutiques;
    }

    @Override
    public int getNombreProduits() {
        String sql = "SELECT count(*) FROM produit ";
        int i = 0;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                i = result.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public int getNombreProduitsVendus() {
        String sql = "SELECT count(*) FROM produit p, produit_panier pp, panier pa where pp.id_panier = pa.id and pp.id_produit = p.id and pa.statut = 'Valide'  ";
        int i = 0;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                i = result.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public Float getQuantiteProduitsVendusParMois(String mois) {
        //2018-02-02T00:00
        String sql = "SELECT pp.date_ajout, pp.quantite_vendu FROM produit p, produit_panier pp, panier pa where pp.id_panier = pa.id and pp.id_produit = p.id and pa.statut = 'Valide'  ";
        LocalDateTime date;
        Float quantite;
        Float i = 0.0f;
        try {
            Statement statement = connexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                date = result.getObject("pp.date_ajout", LocalDateTime.class);
                quantite = result.getFloat("pp.quantite_vendu");
                String dateString = date.toString();
                if (dateString.contains(mois)) {
                    i = i + quantite;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (i);
    }

    @Override
    public int getNombreProduits(List<Boutique> boutiques) {
        int i = 0;
        for (Boutique b : boutiques) {
            String sql = "SELECT count(*) FROM produit where id_boutique ='" + b.getId() + "'";
            try {
                Statement statement = connexion.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    i = i + result.getInt("COUNT(*)");
                }

            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (i);
    }

    @Override
    public int getNombreProduitsVendus(List<Boutique> boutiques) {
        int i = 0;
        for (Boutique b : boutiques) {
            String sql = "SELECT count(*) FROM produit p, produit_panier pp, panier pa where pp.id_panier = pa.id and pp.id_produit = p.id and pa.statut = 'Valide'  and p.id_boutique = '" + b.getId() + "'";

            try {
                Statement statement = connexion.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    i = i + result.getInt("COUNT(*)");
                }

            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return (i);
    }

    @Override
    public Float getQuantiteProduitsVendusParMois(List<Boutique> boutiques, String mois) {
        LocalDateTime date;
        Float quantite;
        Float i = 0.0f;
        for (Boutique b : boutiques) {
            String sql = "SELECT pp.date_ajout, pp.quantite_vendu FROM produit p, produit_panier pp, panier pa where pp.id_panier = pa.id and pp.id_produit = p.id and pa.statut = 'Valide' and p.id_boutique = '" + b.getId() + "'";
            try {
                Statement statement = connexion.createStatement();
                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {
                    date = result.getObject("pp.date_ajout", LocalDateTime.class);
                    quantite = result.getFloat("pp.quantite_vendu");
                    String dateString = date.toString();
                    if (dateString.contains(mois)) {
                        i = i + quantite;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (i);
    }

    @Override
    public List<Produit> getTopTenProduits(List<Boutique> boutiques) {
        BoutiqueService bs = new BoutiqueService();
        List<Produit> topTenProduits = new ArrayList<Produit>();
        EvaluationService es = new EvaluationService();
        Float note;
        int j = 0;
        Map<Float, ArrayList<Produit>> noteProduit = new TreeMap<Float, ArrayList<Produit>>(Collections.reverseOrder());
        for (Boutique b : boutiques) {
            List<Produit> produits = bs.lireProduitsParBoutique(b);
            for (Produit p : produits) {
                ArrayList<Produit> prd = new ArrayList<Produit>();
                note = es.getNoteProduit(p);
                if (noteProduit.containsKey(note)) {
                    prd = noteProduit.get(note);
                    prd.add(p);
                    noteProduit.replace(note, prd);
                } else {
                    prd.add(p);
                    noteProduit.put(note, prd);
                }
            }
        }
        Iterator i = noteProduit.keySet().iterator();
        Float cle;
        ArrayList<Produit> valeur;

        while (j < 10) {
            if (i.hasNext()) {
                cle = (Float) i.next();
                valeur = (ArrayList<Produit>) noteProduit.get(cle);
                System.out.println("LA NOTE EST : " + cle);
                System.out.println("LE PRODUIT EST : " + valeur);
                for (Produit p : valeur) {
                    j++;
                    topTenProduits.add(p);
                }
            } else {
                break;
            }
        }

        return topTenProduits;
    }

}
