/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Utils.Enumerations.*;
import java.sql.Date;

/**
 *
 * @author Hamdi
 */
public class User {
    
    private int id;
    private String userName; // pour la connexion ******** UNIQUE ******************
    private String mdp;
    private EtatUser etat;
    private TypeUser type;
    
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String Sexe;
    private String email;
    private String adresse;
    private String tel;
    
    
    
    // FOS Bundle Table
    private String role;
    private String token;

    public User() {
    }

    public User(int id, String userName, String mdp, EtatUser etat, TypeUser type, String nom, String prenom, Date dateNaissance, String Sexe, String email, String adresse, String tel, String role, String token) {
        this.id = id;
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
        this.role = role;
        this.token = token;
    }

    public User(String userName, String mdp, EtatUser etat, TypeUser type, String nom, String prenom, Date dateNaissance, String Sexe, String email, String adresse, String tel, String role, String token) {
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
        this.role = role;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", mdp=" + mdp + ", etat=" + etat + ", type=" + type + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", Sexe=" + Sexe + ", email=" + email + ", adresse=" + adresse + ", tel=" + tel + ", role=" + role + ", token=" + token + '}';
    }

    
}
