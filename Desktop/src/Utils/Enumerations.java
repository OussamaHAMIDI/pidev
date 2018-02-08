/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Hamdi
 */

public class Enumerations {
    
    /**
     *  Enumeration pour l'état de user 
     */
    public static enum EtatUser{

        /**
         * En attente de confirmation par l'admin ou par mail
         */
        Pending,

        /**
         * Le compte User est activé / verifé
         */
        Active,
         /**
         * Le compte user est desactivé
         */
        Inactive,
        /**
         * User est connecté
         */
        Connected,
         /**
         * User est deconnecté
         */
        Disconnected,
        /**
         * Le compte user est supprimé
         */
        Deleted;
        
    }
    
    /**
     *  Enumeration pour le type de user 
     */
    public static enum TypeUser{
        Artisan,
        Client;
    }
    
     /**
     *  Enumeration pour l'état d'une boutique 
     */
    public static enum EtatBoutique{
        Opened,Closed;
    }
    
     public static enum ModePaiement{
        Cheque,
        Espece,
        Internet;
       
    }
     
     public static enum ModeLivraison{
        Domicile,
        SurPlace,
        Poste;
    }
      public static enum StatusPanier{
        Temporelle,
        Valide;
    }
      
      public static enum TypeReclamation{
      Boutique,
      Produit;
    }
      
      
}
