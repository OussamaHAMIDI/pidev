/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author monta
 */
public class Panier {
    
    private int id;
    private int user;
    private String dateCreation;
    private String dateLivraison;
    private double totalTTC;
    private double fraisLivraison;
    private String status;
    private String modePaiement;
    private String modeLivraison;
    private boolean estLivre;
    private boolean estPaye;
    private List<ProduitPanier> contenu;

    public Panier(int id, int user, String dateCreation, String dateLivraison, double totalTTC, double fraisLivraison, String status, String modePaiement, String modeLivraison, boolean estLivre, boolean estPaye, List<ProduitPanier> contenu) {
        this.id = id;
        this.user = user;
        this.dateCreation = dateCreation;
        this.dateLivraison = dateLivraison;
        this.totalTTC = totalTTC;
        this.fraisLivraison = fraisLivraison;
        this.status = status;
        this.modePaiement = modePaiement;
        this.estLivre = estLivre;
        this.estPaye = estPaye;
        this.contenu = contenu;
        this.modeLivraison = modeLivraison;
    }

    public Panier(int user, String dateCreation) {
        this.user = user;
        this.dateCreation = dateCreation;
        this.status ="Temporelle";
        this.modeLivraison = "SurPlace";
        this.contenu = new ArrayList<ProduitPanier>();
    }

    public Panier() {
        
    }

    public String getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison(String modeLivraison) {
        this.modeLivraison = modeLivraison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public double getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        this.totalTTC = totalTTC;
    }

    public double getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(double fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public boolean isEstLivre() {
        return estLivre;
    }

    public void setEstLivre(boolean estLivre) {
        this.estLivre = estLivre;
    }

    public boolean isEstPaye() {
        return estPaye;
    }

    public void setEstPaye(boolean estPaye) {
        this.estPaye = estPaye;
    }

    public List<ProduitPanier> getContenu() {
        return contenu;
    }

    public void setContenu(List<ProduitPanier> contenu) {
        this.contenu = contenu;
    }

    public void ajouter(ProduitPanier p)
    {
        boolean exist = false;
        for(ProduitPanier pp : this.contenu)
        {
            if(pp.getId()==p.getId())
            {
                exist = true;
            }
        }
        if(!exist)
        {
            this.contenu.add(p);
        }
        this.recalculer();
    }
    
    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", userId=" + user + ", dateCreation=" + dateCreation + ", dateLivraison=" + dateLivraison + ", totalTTC=" + totalTTC + ", fraisLivraison=" + fraisLivraison + ", status=" + status + ", modePaiement=" + modePaiement + ", estLivre=" + estLivre + ", estPaye=" + estPaye + ", contenu=" + contenu + '}';
    }



    public String genererMailBody() {
        String body = "";
        for (ProduitPanier p : this.contenu) {
            body += "<tr style=\"border-radius:1px;color:black;\"><td>" + p.reference + "</td>" + "<td>" + p.libelle + "</td>" + "<td>" + p.getQuantiteVendue() + "</td>" + "<td>" + p.getPrixVente() * p.getQuantiteVendue() + "</td></tr>";
        }
        return body;
    }

    public void recalculer()
    {
        this.totalTTC=0.0;
        for(ProduitPanier p: this.contenu)
        {
            this.totalTTC += p.getPrixVente()*p.getQuantiteVendue();
            
        }
    }
}
