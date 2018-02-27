/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.User;
import Utils.Enumerations.*;
import java.io.InputStream;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Hamdi
 */
public interface IUser {

    public boolean ajouterUser(User u);

    public boolean modifierUser(User u);

    public void modifierEtatUser(int idUser, EtatUser etat);

    public boolean changerToken(String code, String email);

    public boolean changerMdp(int idUser, String new_mdp);

    public int getIdUser(String userName, String email);

    public User getUserById(int idUser);

    public User getUserByUsername(String username);

    public User getUserByEmail(String email);

    public int getNextId();

    public Image getPhoto(int id);

    public boolean verifColumn(String columnName, String columnValue);

    public List<User> getUsers();

    public void supprimerUser(int idUser);

    public InputStream getPhotoUser(int id);
    
    public boolean addPhotoArtisan(int idArtisan,InputStream photo);
    
    public Image getPhotoArtisan(int idArtisan);

}
