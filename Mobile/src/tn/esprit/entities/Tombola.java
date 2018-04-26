package tn.esprit.entities;

import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hamdi
 */
public class Tombola {

    private String id;
    private String titre;
    private String description;
    private String dateAjout;
    private String dateTirage;
    private User artisan;
    private User gagnant;
    private String dateModif;
    private String etat;
    private String photo; // juste the name of the file.ext

    public Tombola() {
    }

    public Tombola(String id, String titre, String description, String dateAjout, String dateTirage, String dateModif, User artisan, User gagnant, String photo) {
        if (id.contains(".")) {
            this.id = id.substring(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
        this.titre = titre;
        this.description = description;
        this.dateAjout = dateAjout;
        this.dateTirage = dateTirage;
        this.artisan = artisan;
        this.gagnant = gagnant;
        this.dateModif = dateModif;
        this.photo = photo;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date tirage = s.parse(dateTirage);

            if (tirage.getTime() > new Date().getTime()) {
                if (gagnant != null) {
                    this.etat = "Cloturée";
                } else {
                    this.etat = "Ouverte";
                }
            } else {
                if (gagnant != null) {
                    this.etat = "Cloturée";
                } else {
                    this.etat = "Fermée";
                }
            }
        } catch (ParseException ex) {
            Log.e(ex);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id.contains(".")) {
            this.id = id.substring(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getDateTirage() {
        return dateTirage;
    }

    public void setDateTirage(String dateTirage) {
        this.dateTirage = dateTirage;
    }

    public User getArtisan() {
        return artisan;
    }

    public void setArtisan(User artisan) {
        this.artisan = artisan;
    }

    public User getGagnant() {
        return gagnant;
    }

    public void setGagnant(User gagnant) {
        this.gagnant = gagnant;
    }

    public String getDateModif() {
        return dateModif;
    }

    public void setDateModif(String dateModif) {
        this.dateModif = dateModif;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
