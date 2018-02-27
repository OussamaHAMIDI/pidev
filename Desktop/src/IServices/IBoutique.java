/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import java.io.InputStream;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Azza
 */
public interface IBoutique {

    public void ajouterBoutique(Boutique boutique,int idUser);

    public void ajouterProduit(int idProduit, int idBoutique);
    
    public void ajouterProduit(Produit produit, Boutique boutique);

    public void supprimerBoutique(int idBoutique);
    
    public void supprimerBoutique(Boutique boutique);

   public Boutique chercherBoutiqueParNom(String nom);
    
    //public List<Boutique> chercherBoutiquesParNom(String nom);

    public Boutique chercherBoutiqueParID(int idBoutique);
    public InputStream getPhotoBoutique(int idB);
    public Image getPhoto(int idB);

   // public List<Produit> lireProduitsParBoutique(int idBoutique);
    
    public List<Produit> lireProduitsParBoutique(Boutique boutique);

    public List<Produit> lireProduitsParBoutique(String nomBoutique);

    public List<Boutique> lireBoutiques();
    
    public void modifierBoutique(Boutique boutique);
    
    public void modifierNomBoutique(Boutique boutique,String nom);
    
    public void modifierAdresseBoutique(Boutique boutique,String adresse);
    
     public int getNextId();

//     public Boutique chercherBoutiqueParProduit (Produit produit);
//     public void modifierBoutique(Boutique boutique);
     
     public List<Boutique> lireBoutique(User user);
}
