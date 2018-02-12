/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DataStorage.MyDB;
import Entities.User;
import IServices.IUser;
import Utils.Enumerations.*;
import Utils.Utils;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamdi
 */
public class UserService implements IUser {

    Connection connexion; // last_login
    PreparedStatement ps;

    public UserService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    /**
     *
     * @param u User
     *
     * @return
     */
    @Override
    public boolean ajouterUser(User u) {

        if (u.getEtat() == EtatUser.Connected) {
            System.out.println("User :" + u.getUserName() + " is connected");
            return false;
        }
        // Verifier si l'utilisateur n'existe pas dans la table (etat = deleted or pending)
        if (u.getEtat() == EtatUser.Deleted || u.getEtat() == EtatUser.Inactive) {
            modifierEtatUser(u, EtatUser.Active);
        } else if (u.getEtat() == EtatUser.Pending) {
            // pas encore activé (resend verification mail and notify user from GUI)
        } else {
            try {
                String req = "INSERT INTO `user`(`username`, `username_canonical`, `email`, `email_canonical`, `enabled`,"
                        + "`password`, `roles`,`type` , `etat` , `adresse`, `tel`, `nom`, `prenom`, `date_naissance`, `sexe`) "
                        + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connexion.prepareStatement(req);
                ps.setString(1, u.getUserName());
                ps.setString(2, u.getUserName());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getEmail());
                ps.setInt(5, 1); // pas sur de la valeur !!*****************************
                ps.setString(6, u.getMdp());
                // ps.setString(7, "587dsa8754487dsfa");//u.getToken());
                ps.setString(7, "a:1:{i:0;s:11:\"ROLE_CLIENT\";}");
                ps.setString(8, u.getType().toString());
                ps.setString(9, EtatUser.Pending.toString());
                ps.setString(10, u.getAdresse());
                ps.setString(11, u.getTel());
                ps.setString(12, u.getNom());
                ps.setString(13, u.getPrenom());
                ps.setString(14, u.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE));
                ps.setString(15, u.getSexe());

                ps.executeUpdate();
                System.out.println("Ajout User réussi");
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Ajout User echoué");
                return false;
            }
        }

        return false;
    }

    /**
     *
     * @param u User
     * @param file La photo de User
     * @param length Get it from the file
     * @return
     */
    @Override
    public boolean ajouterUser(User u, InputStream file, int length) {
        // Verifier si l'utilisateur n'existe pas dans la table (etat = deleted or pending)
        if (u.getId() > -1) { //if existed
            if (u.getEtat() == EtatUser.Deleted || u.getEtat() == EtatUser.Inactive) {
                modifierEtatUser(u, EtatUser.Active);
            }
            if (u.getEtat() == EtatUser.Pending) {
                // pas encore activé (resend verification mail and notify user from GUI)
            }
        } else {
            try {
                String req = "INSERT INTO `user`(`username`, `username_canonical`, `email`, `email_canonical`, `enabled` , "
                        + "`password`, `roles`,`type` , `etat` , `adresse`, `tel`, `nom`, `prenom`, `date_naissance`, `sexe`,"
                        + "`photo_profil`) "
                        + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = connexion.prepareStatement(req);
                ps.setString(1, u.getUserName());
                ps.setString(2, u.getUserName());
                ps.setString(3, u.getEmail());
                ps.setString(4, u.getEmail());
                ps.setInt(5, 1); // pas sur de la valeur !!*****************************
                ps.setString(6, u.getMdp());
                ps.setString(7, "a:1:{i:0;s:11:\"ROLE_CLIENT\";}");
                ps.setString(8, u.getType().toString());
                ps.setString(9, u.getEtat().toString());
                ps.setString(10, u.getAdresse());
                ps.setString(11, u.getTel());
                ps.setString(12, u.getNom());
                ps.setString(13, u.getPrenom());
                ps.setObject(14, u.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE));
                ps.setString(15, u.getSexe());
                ps.setBinaryStream(16, file, length);
                ps.executeUpdate();
                System.out.println("Ajout User réussi");
                return true;

            } catch (SQLException ex) {
                Logger.getLogger(UserService.class
                        .getName()).log(Level.SEVERE, null, ex);
                System.out.println("Ajout User echoué");
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @param u
     * @param etat
     */
    @Override
    public void modifierEtatUser(User u, EtatUser etat) {

        String req;
        if (etat == EtatUser.Connected) {
            req = "UPDATE user  SET etat = ? , last_login = '" + LocalDateTime.now() + "' WHERE id = ?";
        } else {
            req = "UPDATE user  SET etat = ? WHERE id = ?";
        }
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, etat.toString());
            ps.setInt(2, u.getId());
            ps.executeUpdate();
            System.out.println("Client " + u.getNom() + " " + u.getPrenom() + " est " + etat);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur modifier etat user");
        }
    }

    /**
     *
     * @param idUser idUser
     * @param file La photo de User
     * @param length Get it from the file
     * @return
     */
    @Override
    public boolean ajouterPhotoUser(int idUser, InputStream file, int length) {
        try {
            String req = "UPDATE `user` SET `photo_profil` = ? WHERE id='" + idUser + "'";
            ps = connexion.prepareStatement(req);
            ps.setBinaryStream(1, file, length);
            ps.executeUpdate();
            System.out.println("photo ajoutée avec succès");
            return true;
        } catch (SQLException ex) {
            System.out.println("echec de l'ajout de la photo");
            Logger
                    .getLogger(UserService.class
                            .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean changerToken(String code, String email) {
        try {
            String req = "UPDATE user  SET confirmation_token = ?  WHERE email_canonical = '" + email + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, code);
            ps.executeUpdate();
            System.out.println("token changé avec succes");
            return true;
        } catch (SQLException ex) {
            System.out.println("erreur changement token");
            Logger
                    .getLogger(UserService.class
                            .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean verifMail(String email) {
        ResultSet rs;
        int nb = 0;
        try {
            String req = "SELECT id from user where email_canonical = '" + email + "'";
            ps = connexion.prepareStatement(req);
            rs = ps.executeQuery();
            while (rs.next()) {
                nb++;
            }
            return nb > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean changerMdp(int idUser, String new_mdp) {
        try {
            String req = "UPDATE user  SET password = ?  WHERE id = '" + idUser + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, new_mdp);
            ps.executeUpdate();
            System.out.println("mot de passe changé avec succes");
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur changement mdp");
            return false;
        }
    }

    /**
     *
     * @param userName is a unique column in user table !
     * @param email
     * @return -1 si les informations en parametres ne correspondent pas à aucun
     * id dans la table user.
     */
    @Override
    public int getIdUser(String userName, String email) {
        ResultSet rs;
        int nb = 0;
        int id = -1;
        try {
            String req = "SELECT id FROM user where email_canonical = ? AND email = ? AND username_canonical = ? AND username = ?";
            ps = connexion.prepareStatement(req);
            ps.setString(1, email);
            ps.setString(2, email);
            ps.setString(3, userName);
            ps.setString(4, userName);
            rs = ps.executeQuery(req);
            while (rs.next()) {
                nb++;
                id = rs.getInt("id");
            }
            if (nb > 1) {
                System.out.println("Il y'a plus qu'un user ayant ces informations !");
                return id;
            }
            if (nb < 1) {
                System.out.println("Il y'a pas de user ayant ces informations !");
                return id;
            }
            return id; // nb == 1

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echech de récuperaton de l'ID !");
            return id;
        }
    }

    /**
     *
     * @param idUser
     * @return null si idUser n'existe pas
     */
    @Override
    public User getUserById(int idUser) {
        User u = null;
        try {
            String req = "SELECT * FROM `user` WHERE id = '" + idUser + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {

                u = new User(idUser, rs.getString("username"),  rs.getString("password"), EtatUser.valueOf(rs.getString("etat")),
                        TypeUser.valueOf(rs.getString("type")), rs.getString("nom"), rs.getString("prenom"),
                        Utils.getLocalDateTime(rs.getString("date_naissance")), rs.getString("sexe"), rs.getString("email"),
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                                rs.getString("salt") ,rs.getString("roles"), rs.getString("confirmation_token"),rs.getBinaryStream("photo_profil"));
                System.out.println("User retrieved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get User");
            return u;
        }
        return u;
    }

    /**
     *
     * @param username
     * @return null si username n'existe pas
     */
    @Override
    public User getUserByUsername(String username) {
        User u = null;
        try {
            String req = "SELECT * FROM `user` WHERE username = '" + username + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                u = new User(rs.getInt("id"), username, rs.getString("password"), EtatUser.valueOf(rs.getString("etat")),
                        TypeUser.valueOf(rs.getString("type")), rs.getString("nom"), rs.getString("prenom"),
                        Utils.getLocalDateTime(rs.getString("date_naissance")), rs.getString("sexe"), rs.getString("email"),
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                                rs.getString("salt") ,rs.getString("roles"), rs.getString("confirmation_token"),rs.getBinaryStream("photo_profil"));
                System.out.println("User retrieved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get User");
            return u;
        }
        return u;
    }

    @Override
    public int getNextId() {
        int nextid = - 1;
        try {
            String req = "SHOW TABLE STATUS LIKE 'user'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nextid = rs.getInt("Auto_increment");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get Next ID ");
        }
        return nextid;
    }

    @Override
    public boolean modifierUser(User u) {

        try {
            String req = "UPDATE `user` SET `username`=?,`username_canonical`=?,`email`=?,`email_canonical`=?"
                    + " , `password`=?,`last_login`= ? ,`roles`=?,`type`=?,`etat`= ?,`adresse`=?,`tel`=?,`nom`= ?,`prenom`=?"
                    + ", `date_naissance`= ? ,`sexe`=?,`salt`=? ,`photo_profil`=? WHERE id = '" + u.getId() + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getUserName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getMdp());
            ps.setString(6, u.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            ps.setString(7, u.getRole());
            ps.setString(8, u.getType().toString());
            ps.setString(9, u.getEtat().toString());
            ps.setString(10, u.getAdresse());
            ps.setString(11, u.getTel());
            ps.setString(12, u.getNom());
            ps.setString(13, u.getPrenom());
            ps.setString(14, u.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(15, u.getSexe());
            ps.setString(16, u.getSalt());
            ps.setBinaryStream(17, u.getPhoto());
            ps.executeUpdate();
            System.out.println("Modification User " + u.getUserName() + " réussie");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Modification User echouée");
            return false;
        }
    }

}
