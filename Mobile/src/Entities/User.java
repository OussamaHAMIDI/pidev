package Entities;

import Entities.Enumerations.*;

/**
 *
 * @author Hamdi
 */
public class User {

    private String id;
    private String userName; // pour la connexion ******** UNIQUE ******************
    private String mdp;
    private EtatUser etat;
    private TypeUser type;

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String Sexe;
    private String email;
    private String adresse;
    private String tel;

    private String photo;

    public User() {
    }

    public User(String id, String userName, String mdp, EtatUser etat, TypeUser type, String nom, String prenom, String dateNaissance, String Sexe, String email, String adresse, String tel, String photo) {
        if (id.contains(".")) {
            this.id = id.substring(0, id.indexOf('.'));
        } else {
            this.id = id;
        }
        this.userName = userName;
        this.mdp = mdp;
        this.etat = etat;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.Sexe = Sexe;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.photo = photo;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public EtatUser getEtat() {
        return etat;
    }

    public void setEtat(EtatUser etat) {
        this.etat = etat;
    }

    public TypeUser getType() {
        return type;
    }

    public void setType(TypeUser type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String Sexe) {
        this.Sexe = Sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
