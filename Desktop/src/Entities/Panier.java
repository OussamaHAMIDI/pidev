
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Utils.Enumerations;
import Utils.Enumerations.ModeLivraison;
import Utils.Enumerations.ModePaiement;
import Utils.Enumerations.StatusPanier;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author monta
 */
public class Panier {

    private int id;
    private User user;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLivraison;
    private double totalTTC;
    private double fraisLivraison;
    private StatusPanier status;
    private ModePaiement modePaiement;
    private ModeLivraison modeLivraison;
    private boolean estLivre;
    private boolean estPaye;
    private List<ProduitPanier> contenu;

    public Panier(int id, User user, LocalDateTime dateCreation, LocalDateTime dateLivraison, double totalTTC, double fraisLivraison, String status, String modePaiement, String modeLivraison, boolean estLivre, boolean estPaye, List<ProduitPanier> contenu) {
        this.id = id;
        this.user = user;
        this.dateCreation = dateCreation;
        this.dateLivraison = dateLivraison;
        this.totalTTC = totalTTC;
        this.fraisLivraison = fraisLivraison;
        this.status = StatusPanier.valueOf(status);
        this.modePaiement = ModePaiement.valueOf(modePaiement);
        this.estLivre = estLivre;
        this.estPaye = estPaye;
        this.contenu = contenu;
        this.modeLivraison = ModeLivraison.valueOf(modeLivraison.replace(" ", ""));
    }

    public Panier(User user, LocalDateTime dateCreation) {
        this.user = user;
        this.dateCreation = dateCreation;
        this.status = StatusPanier.Temporelle;
        this.modeLivraison = ModeLivraison.Surplace;

    }

    public Panier() {
    }

    public ModeLivraison getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison(ModeLivraison modeLivraison) {
        this.modeLivraison = modeLivraison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDateTime dateLivraison) {
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

    public StatusPanier getStatus() {
        return status;
    }

    public void setStatus(StatusPanier status) {
        this.status = status;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
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

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", userId=" + user.getId() + ", dateCreation=" + dateCreation + ", dateLivraison=" + dateLivraison + ", totalTTC=" + totalTTC + ", fraisLivraison=" + fraisLivraison + ", status=" + status + ", modePaiement=" + modePaiement + ", estLivre=" + estLivre + ", estPaye=" + estPaye + ", contenu=" + contenu + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.user.getId());
        hash = 31 * hash + Objects.hashCode(this.dateCreation);
        hash = 31 * hash + Objects.hashCode(this.dateLivraison);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.totalTTC) ^ (Double.doubleToLongBits(this.totalTTC) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.fraisLivraison) ^ (Double.doubleToLongBits(this.fraisLivraison) >>> 32));
        hash = 31 * hash + Objects.hashCode(this.status);
        hash = 31 * hash + Objects.hashCode(this.modePaiement);
        hash = 31 * hash + Objects.hashCode(this.contenu);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Panier other = (Panier) obj;
        if (Double.doubleToLongBits(this.totalTTC) != Double.doubleToLongBits(other.totalTTC)) {
            return false;
        }
        if (Double.doubleToLongBits(this.fraisLivraison) != Double.doubleToLongBits(other.fraisLivraison)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user.getId(), other.user.getId())) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.modePaiement, other.modePaiement)) {
            return false;
        }
        if (!Objects.equals(this.dateCreation, other.dateCreation)) {
            return false;
        }
        if (!Objects.equals(this.dateLivraison, other.dateLivraison)) {
            return false;
        }
        if (!Objects.equals(this.contenu, other.contenu)) {
            return false;
        }
        return true;
    }

    public String genererMailBody() {
        String body = "";
        for (ProduitPanier p : this.contenu) {
            body += "<tr><td>" + p.reference + "</td>" + "<td>" + p.libelle + "</td>" + "<td>" + p.getQuantiteVendue() + "</td>" + "<td>" + p.getPrixVente() * p.getQuantiteVendue() + "</td></tr>";
        }
        return body;
    }

    public boolean generatePDF() {
        try {
            PDDocument myPdf = new PDDocument();
            PDPage page = new PDPage();
            myPdf.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(myPdf, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 36);
            contentStream.setLeading(14.5f);

            contentStream.newLineAtOffset(25, 700);
            contentStream.showText("                       Commande");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(this.dateCreation.toString().replace("T", " "));
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLine();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
            contentStream.showText("Au nom de :" + this.user.getNom() + " " + this.user.getPrenom());
            contentStream.newLine();
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("_______________________________________________________________________________________________________________");

            for (ProduitPanier p : this.contenu) {
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Reference : " + p.reference + " Libelle :" + p.libelle + " quantité:" + p.getQuantiteVendue() + " prix :" + p.getPrixVente() + " Total :" + p.getPrixVente() * p.getQuantiteVendue() + "  Boutique : " + p.boutique.getNom());
                contentStream.newLine();
                contentStream.showText("_______________________________________________________________________________________________________________");
            }
            contentStream.endText();
            contentStream.close();
            myPdf.save(System.getProperty("user.home") + "/Desktop"+ this.id + ".pdf");
            myPdf.close();
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
