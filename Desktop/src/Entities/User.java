/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Utils.Enumerations.*;
import java.io.InputStream;
import java.time.LocalDateTime;

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
    private LocalDateTime dateNaissance;
    private String Sexe;
    private String email;
    private String adresse;
    private String tel;

    // FOS Bundle Table
    private LocalDateTime lastLogin;
    private String salt;
    private String role;
    private String token;
    private InputStream photo;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    // avec photo
    public User(int id, String userName, String mdp, EtatUser etat, TypeUser type, String nom, String prenom, LocalDateTime dateNaissance, String Sexe, String email, String adresse, String tel, LocalDateTime lastLogin, String salt, String role, String token, InputStream photo) {
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
        this.lastLogin = lastLogin;
        this.salt = salt;
        this.role = role;
        this.token = token;
        this.photo = photo;
    }

 // sans photo
    public User(int id, String userName, String mdp, EtatUser etat, TypeUser type, String nom, String prenom, LocalDateTime dateNaissance, String Sexe, String email, String adresse, String tel, LocalDateTime lastLogin, String salt, String role, String token) {
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
        this.lastLogin = lastLogin;
        this.salt = salt;
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

    public LocalDateTime getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDateTime dateNaissance) {
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", mdp=" + mdp + ", etat=" + etat + ", type=" + type + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", Sexe=" + Sexe + ", email=" + email + ", adresse=" + adresse + ", tel=" + tel + ", lastLogin=" + lastLogin + ", salt=" + salt + ", role=" + role + ", token=" + token + '}';
    }

    

  

}
