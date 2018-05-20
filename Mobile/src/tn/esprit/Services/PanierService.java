/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.Services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import tn.esprit.entities.Panier;
import tn.esprit.entities.ProduitPanier;


/**
 *
 * @author sana
 */
public class PanierService {

    public PanierService(){
    }
    
    public void addPanier(Panier panier) {
        String url = "http://localhost/pidev/WEB/web/app_dev.php/api/panier/add?totalttc="
                + panier.getTotalTTC()
                +"&fraislivraison=0.0"
                +"&status=Temporelle"
                +"&modepaiement=Especes"
                +"&modelivraison=SurPlace"
                +"&estlivre=0"
                +"&estpaye=0"
                +"&iduser="
                +panier.getUser();
        ConnectionRequest con = new ConnectionRequest(url, true);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public void addProduitPanier(ProduitPanier produit) {
        String url = "http://localhost/pidev/WEB/web/app_dev.php/api/produitpanier/add?reference="
                + produit.getReference()
                +"&idProduit="+produit.getId()
                +"&description="+produit.getDescription()
                +"&prixvente="+produit.getPrixVente()
                +"&quantitevendu="+produit.getQuantiteVendue()
                +"&livree=0"+
                "&libelle=" + produit.getLibelle() + 
                "&prix=" + produit.getPrix() + 
                "&taille=" + produit.getTaille() + 
                "&couleur=" + produit.getCouleur() + 
                "&texture=" + produit.getTexture() + 
                "&poids=" + produit.getPoids() + 
                "&date_creation=" + produit.getDate_creation()+ 
                "&quantite=" + produit.getQuantite()
                ;
        ConnectionRequest con = new ConnectionRequest(url, true);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
