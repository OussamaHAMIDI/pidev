package tn.esprit.entities;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class Reclamation {
    private String id;
    private String dateCreation;
    private User user;
    private Boutique boutique;
    //TO DO Produit
    private String description;

    public Reclamation() {
    }

    public Reclamation(String id, String dateCreation, User user, Boutique boutique, String description) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.user = user;
        this.boutique = boutique;
        this.description = description;
    }

    public Reclamation(User user, Boutique boutique, String description) {
        this.user = user;
        this.boutique = boutique;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\n Evaluation{" + "id=" + id + ", dateCreation=" + dateCreation + ", user=" + user + ", boutique=" + boutique + ", description=" + description + '}';
    }

}
