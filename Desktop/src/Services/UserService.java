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
import java.io.IOException;
import java.net.ProtocolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.BCrypt;
import rest.file.uploader.tn.FileUploader;

/**
 *
 * @author Hamdi
 */
public class UserService implements IUser {

    Connection connexion;
    PreparedStatement ps;
    FileUploader fu = new FileUploader("localhost/pidev/WEB/web");

    public UserService() {
        connexion = MyDB.getinstance().getConnexion();
    }

    @Override
    public User ajouterUser(User u) {
        try {
            String req = "INSERT INTO `user`(`username`, `username_canonical`, `email`, `email_canonical`, `enabled`,`password`, `roles`,`type` , "
                    + "`etat` , `adresse`, `tel`, `nom`, `prenom`, `date_naissance`, `sexe`,`confirmation_token`,`path_photo_profil`) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connexion.prepareStatement(req);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getUserName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getEmail());
            ps.setInt(5, 1);
            ps.setString(6, u.getMdp());

            //Role definition
            String t = u.getType().toString().toUpperCase();
            if (t.contains("ADMIN")) {
                t = "ADMIN";
            }
            String tt = "ROLE_" + t;
            String role = "a:1:{i:0;s:" + tt.length() + ":\"" + tt + "\";}";

            ps.setString(7, role);

            ps.setString(8, u.getType().toString());
            ps.setString(9, u.getEtat().toString());
            ps.setString(10, u.getAdresse());
            ps.setString(11, u.getTel());
            ps.setString(12, u.getNom());
            ps.setString(13, u.getPrenom());
            ps.setString(14, u.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(15, u.getSexe());
            ps.setString(16, u.getToken());

            String fileNameInServer;
            if (u.getPhoto() != null) {
                fileNameInServer = fu.upload(u.getPhoto());
            } else {
                fileNameInServer = fu.upload("src/Images/user.png");
            }
            u.setPhoto(fileNameInServer);
            ps.setString(17, u.getPhoto());

            ps.executeUpdate();
            // System.out.println("Ajout User réussi");
            return u;
        } catch (IOException | SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ajout User echoué");
            return null;
        }
    }

    @Override
    public void modifierEtatUser(int idUser, EtatUser etat) {

        String req;
        if (etat == EtatUser.Connected) {
            req = "UPDATE user  SET etat = ? , last_login = '" + LocalDateTime.now() + "' WHERE id = ?";
        } else {
            req = "UPDATE user  SET etat = ? WHERE id = ?";
        }
        try {
            ps = connexion.prepareStatement(req);
            ps.setString(1, etat.toString());
            ps.setInt(2, idUser);
            ps.executeUpdate();
            // System.out.println("User updated to " + etat);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur modifier etat user");
        }
    }

    @Override
    public boolean changerToken(String code, String email) {
        try {
            String req = "UPDATE user  SET confirmation_token = ?  WHERE email_canonical = '" + email + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, code);
            ps.executeUpdate();
            //System.out.println("token changé avec succes");
            return true;
        } catch (SQLException ex) {
            System.out.println("erreur changement token");
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean verifColumn(String columnName, String columnValue) {
        int nb = 0;
        try {
            String req = "SELECT id from user where " + columnName + " = '" + columnValue + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
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
            String mdp = BCrypt.hashpw(new_mdp, BCrypt.gensalt(10)).replace("$2a$", "$2y$"); // cost in security.yml

            String req = "UPDATE user  SET password = ?   WHERE id = '" + idUser + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, mdp);
            ps.executeUpdate();
//            System.out.println("mot de passe changé avec succes");
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
//                System.out.println("Il y'a plus qu'un user ayant ces informations !");
                return id;
            }
            if (nb < 1) {
//                System.out.println("Il y'a pas de user ayant ces informations !");
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

    @Override
    public User getUserById(int idUser) {
        User u = null;
        try {
            String req = "SELECT * FROM `user` WHERE id = '" + idUser + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {

                u = new User(idUser, rs.getString("username"), rs.getString("password"), EtatUser.valueOf(rs.getString("etat")),
                        TypeUser.valueOf(rs.getString("type")), rs.getString("nom"), rs.getString("prenom"),
                        Utils.getLocalDate(rs.getString("date_naissance")), rs.getString("sexe"), rs.getString("email"),
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                        rs.getString("salt"), rs.getString("roles"), rs.getString("confirmation_token"), rs.getString("path_photo_profil"));
                u.setPhotoPermis(rs.getString("path_photo_permis"));
//                System.out.println("User retrieved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get User");
            return u;
        }
        return u;
    }

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
                        Utils.getLocalDate(rs.getString("date_naissance")), rs.getString("sexe"), rs.getString("email"),
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                        rs.getString("salt"), rs.getString("roles"), rs.getString("confirmation_token"), rs.getString("path_photo_profil"));
//                System.out.println("User retrieved");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get User");
            return u;
        }
        return u;
    }

    @Override
    public User getUserByEmail(String email) {
        User u = null;
        try {
            String req = "SELECT * FROM `user` WHERE email = '" + email + "'";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                u = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), EtatUser.valueOf(rs.getString("etat")),
                        TypeUser.valueOf(rs.getString("type")), rs.getString("nom"), rs.getString("prenom"),
                        Utils.getLocalDate(rs.getString("date_naissance")), rs.getString("sexe"), email,
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                        rs.getString("salt"), rs.getString("roles"), rs.getString("confirmation_token"), rs.getString("path_photo_profil"));
                u.setPhotoPermis(rs.getString("path_photo_permis"));
//                System.out.println("User retrieved");
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
//            System.out.println("Echec get Next ID ");
        }
        return nextid;
    }

    @Override
    public User modifierUser(User u) {
        try {
            String req = "UPDATE `user` SET `username`=?,`username_canonical`=?,`email`=?,`email_canonical`=?,`password`=?"
                    + ",`last_login`=? ,`roles`=?,`type`=?,`etat`= ?,`adresse`=?,`tel`=?,`nom`= ?,`prenom`=?"
                    + ",`date_naissance`=? ,`sexe`=?,`path_photo_profil`=? WHERE id = '" + u.getId() + "'";
            ps = connexion.prepareStatement(req);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getUserName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getMdp());

            try {
                String date = u.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                ps.setString(6, date);
            } catch (SQLException e) {
                ps.setString(6, null);
            }

            //Role definition
            String t = u.getType().toString().toUpperCase();
            if (t.contains("ADMIN")) {
                t = "ADMIN";
            }
            String tt = "ROLE_" + t;
            String role = "a:1:{i:0;s:" + tt.length() + ":\"" + tt + "\";}";

            ps.setString(7, role);
            ps.setString(8, u.getType().toString());
            ps.setString(9, u.getEtat().toString());
            ps.setString(10, u.getAdresse());
            ps.setString(11, u.getTel());
            ps.setString(12, u.getNom());
            ps.setString(13, u.getPrenom());
            ps.setString(14, u.getDateNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(15, u.getSexe());

            String fileNameInServer;
            String oldPhoto = getUserById(u.getId()).getPhoto();

            if (u.getPhoto() != null) {
                fu.delete(oldPhoto);
                fileNameInServer = fu.upload(u.getPhoto());
            } else {
                fileNameInServer = oldPhoto;
            }
            u.setPhoto(fileNameInServer);
            ps.setString(16, u.getPhoto());

            ps.executeUpdate();
//            System.out.println("Modification User " + u.getUserName() + " réussie");
            return u;

        } catch (IOException | SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Modification User echouée");
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            String req = "SELECT * FROM `user` order by id desc ";
            ps = connexion.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), EtatUser.valueOf(rs.getString("etat")),
                        TypeUser.valueOf(rs.getString("type")), rs.getString("nom"), rs.getString("prenom"),
                        Utils.getLocalDate(rs.getString("date_naissance")), rs.getString("sexe"), rs.getString("email"),
                        rs.getString("adresse"), rs.getString("tel"), Utils.getLocalDateTime(rs.getString("last_login")),
                        rs.getString("salt"), rs.getString("roles"), rs.getString("confirmation_token"), rs.getString("path_photo_profil"));
                u.setPhotoPermis(rs.getString("path_photo_permis"));
                users.add(u);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Echec get User");
            return null;
        }

        return users;
    }

    @Override
    public void supprimerUser(int idUser) {
//        
//        u.setEtat(EtatUser.Deleted);
//        u.setEmail(u.getEmail() + u.getId());
//        u.setUserName(u.getUserName() + u.getId());
        modifierEtatUser(idUser, EtatUser.Deleted);
//        if (!us.modifierUser(u)) {
//            System.out.println("Suppression User echouée");
//        }
    }

    @Override
    public User addPhotoArtisan(User u, String photo) {
        try {
            String req = "UPDATE `user` SET `path_photo_permis`=? WHERE id = '" + u.getId() + "'";
            ps = connexion.prepareStatement(req);

            String fileNameInServer = null;
            if (photo != null) {
                fileNameInServer = fu.upload(photo);
            }
            u.setPhotoPermis(fileNameInServer);

            ps.setString(1, u.getPhotoPermis());
            ps.executeUpdate();
//            System.out.println("Modification photo artisan réussie");
            return u;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Modification photo artisan echouée");
            return null;
        }
    }

//    @Override
//    public InputStream getPhotoUser(int id) {
//        InputStream photo = null;
//        try {
//            String req = "SELECT photo_profil FROM `user` WHERE id = '" + id + "'";
//            ps = connexion.prepareStatement(req);
//            ResultSet rs = ps.executeQuery();
//            if (rs.first()) {
//
//                photo = rs.getBinaryStream("photo_profil");
//                // System.out.println("photo retrieved");
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Echec get photo");
//
//        }
//        return photo;
//    }
//
//    @Override
//    public Image getPhoto(int id) {
//        InputStream photo = null;
//        try {
//            photo = new FileInputStream("src/Images/user.png");
//            String req = "SELECT photo_profil FROM `user` WHERE id = '" + id + "'";
//            ps = connexion.prepareStatement(req);
//            ResultSet rs = ps.executeQuery();
//            if (rs.first()) {
//                photo = rs.getBinaryStream("photo_profil");
//                //System.out.println("photo retrieved");
//            }
//            return new Image(photo);
//
//        } catch (SQLException | FileNotFoundException ex) {
//            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Echec get photo");
//
//        }
//        return new Image(photo);
//    }
//
//
//    @Override
//    public Image getPhotoArtisan(int idArtisan) {
//        InputStream permis = null;
//        try {
//
//            String req = "SELECT path_photo_permis FROM `user` WHERE id = '" + idArtisan + "'";
//            ps = connexion.prepareStatement(req);
//            ResultSet rs = ps.executeQuery();
//            if (rs.first()) {
//                permis = rs.getBinaryStream("path_photo_permis");
//                //System.out.println("photo retrieved");
//            }
//            return new Image(permis);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Echec get photo artisan");
//
//        }
//        return new Image(permis);
//    }
}
