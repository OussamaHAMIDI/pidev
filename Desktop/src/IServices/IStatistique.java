/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Boutique;
import Entities.Evaluation;
import Entities.Produit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benab
 */
public interface IStatistique {
    
    public int getNombreClients();
    public int getNombreArtisans();
    public int getNombreAdministrateurs();
    public List<Produit> getTopTenProduits();
    public List<Boutique> getTopTenBoutiques();
    public List<Produit> getTopTenProduits(List<Boutique> boutiques);
    public int getNombreProduits();
    public int getNombreProduitsVendus();
    public int getNombreProduits(List<Boutique> boutiques);
    public int getNombreProduitsVendus(List<Boutique> boutiques);
    public Float getQuantiteProduitsVendusParMois(String mois);
    public Float getQuantiteProduitsVendusParMois(List<Boutique> boutiques,String mois);
}
