/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.User;
import Utils.Enumerations.*;
import java.io.InputStream;

/**
 *
 * @author Hamdi
 */
public interface IUser {

    public boolean ajouterUser(User u, InputStream file, int length);

    public boolean supprimerUser(int idUser);

    public boolean modifierEtatUser(int idUser, EtatUser etat);

    public boolean ajouterPhotoUser(int idUser, InputStream file, int length);

    public boolean changerToken(String code, String email);

    public boolean verifMail(String email);

    public boolean changerMdp(int idUser, String new_mdp);

   public int getIdUser(String userName, String email);
    
    public User getUserById(int idUser);

    public int getNextId();
    
}
