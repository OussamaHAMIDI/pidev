package tn.esprit.entities;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class Evaluation {
    private String id;
    private String dateCreation;
    private User user;
    private Boutique boutique;
    //TO DO Produit
    private int note;

    public Evaluation() {
    }

    public Evaluation(String id, String dateCreation, User user, Boutique boutique, int note) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.user = user;
        this.boutique = boutique;
        this.note = note;
    }

    public Evaluation(User user, Boutique boutique, int note) {
        this.user = user;
        this.boutique = boutique;
        this.note = note;
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

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "\n Evaluation{" + "id=" + id + ", dateCreation=" + dateCreation + ", user=" + user + ", boutique=" + boutique + ", note=" + note + '}';
    }

    
    
    
}
