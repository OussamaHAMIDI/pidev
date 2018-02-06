/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Hamdi
 */
public class Produit {

    int id;
    String libelle;
    int nombre;

    public Produit() {
    }

    public Produit(int id, String libelle, int nombre) {
        this.id = id;
        this.libelle = libelle;
        this.nombre = nombre;
    }

    public Produit(String libelle, int nombre) {
        this.libelle = libelle;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", libelle=" + libelle + ", nombre=" + nombre + '}';
    }

}
