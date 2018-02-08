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

    public boolean supprimerUser(String idUser);

    public boolean modifierEtatUser(String idUser, EtatUser etat);

    public boolean ajouterPhotoUser(String idUser, InputStream file, int length);

    public boolean changerToken(String code, String email);

    public boolean verifMail(String email);

    public boolean changerMdp(String new_mdp, String email);

    public String getIdUser(String userName, String email);
    
    public User getUserById(String idUser);

    public String getNextId();
    
}
