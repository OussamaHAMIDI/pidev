    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Produit;

/**
 *
 * @author Worm-root
 */
public class ProduitService {
    private int boutique;
    private String reference;
    private String libelle;
    private String description;
    private int prix;
    private String taille;
    private String couleur;
    private String texture;
    private int poids;
    private String date_creation;
    private int quantite;
    private String path;
    
    
       public static  void ajoutProduit(Produit ta) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/pidev/WEB/web/produit.php?service=create" +
                "&boutique=" + ta.getBoutique() + 
                "&reference=" + ta.getReference() + 
                "&libelle=" + ta.getLibelle() + 
                "&description=" + ta.getDescription() + 
                "&prix=" + ta.getPrix() + 
                "&taille=" + ta.getTaille() + 
                "&couleur=" + ta.getCouleur() + 
                "&texture=" + ta.getTexture() + 
                "&poids=" + ta.getPoids() + 
                "&date_creation=" + ta.getDate_creation()+ 
                "&quantite=" + ta.getQuantite()+ 
                "&path=" + ta.getPath();

           System.out.println("url : "+Url);
        con.setUrl(Url);

        System.out.println("IN ADD produit>>");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Confirmation", "Produit ajouté avec succé ", "OK" , null );
//       
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
       
       public static  void editeProduit(Produit ta) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/pidev/WEB/web/produit.php?service=update" +
                "&id=" + ta.getId() + 
                "&boutique=" + ta.getBoutique() + 
                "&reference=" + ta.getReference() + 
                "&libelle=" + ta.getLibelle() + 
                "&description=" + ta.getDescription() + 
                "&prix=" + ta.getPrix() + 
                "&taille=" + ta.getTaille() + 
                "&couleur=" + ta.getCouleur() + 
                "&texture=" + ta.getTexture() + 
                "&poids=" + ta.getPoids() + 
                "&date_creation=" + ta.getDate_creation()+ 
                "&quantite=" + ta.getQuantite()+ 
                "&path=" + ta.getPath();

           System.out.println("url : "+Url);
        con.setUrl(Url);

        System.out.println("IN EDITE produit>>");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Confirmation", "Modification Enregistré avec succée ", "OK" , null );
//       
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
       
       public static  void deleteProduit(Produit ta) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/pidev/WEB/web/produit.php?service=delete" +
                "&id=" + ta.getId() ;

           System.out.println("url : "+Url);
        con.setUrl(Url);

        System.out.println("IN DELETE produit>>");

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            Dialog.show("Confirmation", "Produit "+ta.getLibelle()+" supprimé avec succée ", "OK" , null );
//       
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    

    public static ArrayList<Produit> getList() {
        ArrayList<Produit> listProduits = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/WEB/web/produit.php?service=getall");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listProduits = getListProduit(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    System.out.println(new String(con.getResponseData()));
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("data");
                    for (Map<String, Object> obj : list) {
                        //Produit p = new Produit();      
  
                        float id = Float.parseFloat(obj.get("id").toString());
                        float boutique = Float.parseFloat(obj.get("boutique").toString());
                        float prix = Float.parseFloat(obj.get("prix").toString());
                        float poids = Float.parseFloat(obj.get("poids").toString());
                        float quantite = Float.parseFloat(obj.get("id").toString());
                        
                        Produit p = new Produit((int)id, 
                                          (int)boutique, 
                                          obj.get("reference").toString(), obj.get("libelle").toString(), 
                                          obj.get("description").toString(), 
                                          (int)prix, 
                                          obj.get("taille").toString(), 
                                          obj.get("couleur").toString(), obj.get("texture").toString(),
                                          (int)poids,
                                          obj.get("date_creation").toString(), (int)quantite, 
                                          obj.get("path").toString());

                        listProduits.add(p);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(listProduits);
        return listProduits;
    }


    
    public static ArrayList<Produit> getListbyBoutique(int idb) {
        ArrayList<Produit> listProduits = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/WEB/web/produit.php?service=getallbyboutique&id="+idb);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listProduits = getListProduit(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();
                
                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("data");
                    for (Map<String, Object> obj : list) {
                        //Produit p = new Produit();      
  
                        float id = Float.parseFloat(obj.get("id").toString());
                        float boutique = Float.parseFloat(obj.get("boutique").toString());
                        float prix = Float.parseFloat(obj.get("prix").toString());
                        float poids = Float.parseFloat(obj.get("poids").toString());
                        float quantite = Float.parseFloat(obj.get("id").toString());
                        
                        Produit p = new Produit((int)id, 
                                          (int)boutique, 
                                          obj.get("reference").toString(), obj.get("libelle").toString(), 
                                          obj.get("description").toString(), 
                                          (int)prix, 
                                          obj.get("taille").toString(), 
                                          obj.get("couleur").toString(), obj.get("texture").toString(),
                                          (int)poids,
                                          obj.get("date_creation").toString(), (int)quantite, 
                                          obj.get("path").toString());

                        listProduits.add(p);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listProduits;
    }


    
    
}
