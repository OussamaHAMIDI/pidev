package tn.esprit.entities;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class Boutique {
    private String id;
    private String adresse;
    private String dateCreation;
    private float longitude;
    private float altitude;
    private String photo;
    private String nom;
    private User user;

    @Override
    public String toString() {
        return "\nBoutique{" + "id=" + id + ", adresse=" + adresse + ", dateCreation=" + dateCreation + ", longitude=" + longitude + ", altitude=" + altitude + ", photo=" + photo + ", nom=" + nom + ", user=" + user + '}';
    }

    
    public Boutique(){ 
    }

    public Boutique(String id, String adresse, float longitude, float altitude, String photo, String nom,String dateCreation, User user) {
        if (id.contains(".")) {
            this.id = id.substring(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
        this.adresse = adresse;
        this.longitude = longitude;
        this.altitude = altitude;
        this.photo = photo;
        this.nom = nom;
        this.user = user;
        this.dateCreation = dateCreation;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
    
}
