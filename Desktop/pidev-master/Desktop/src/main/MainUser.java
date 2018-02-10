/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DataStorage.MyDB;
import Entities.User;
import Services.*;
import Utils.Enumerations.*;
import Utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamdi
 */
public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        UserService us = new UserService();
        User u = us.getUserByUsername("HamdiMegdiche");

        System.out.println(u.getLastLogin());
        u.setLastLogin(LocalDateTime.now());
        System.out.println(u);
        us.modifierUser(u);
//        us.modifierEtatUser(u, EtatUser.Connected);
//         us.modifierEtatUser(u, EtatUser.Disconnected);
//         us.modifierEtatUser(u, EtatUser.Connected);
        u = us.getUserByUsername("HamdiMegdiche");
        System.out.println(u.getLastLogin());
        System.out.println(u.getDateNaissance());

// Utils.sendMail("hamdi.megdiche@esprit.tn", Utils.generateCode(6));
// Utils.sendMail("hamdi-megdiche@outlook.fr", Utils.generateCode(6));
    }

}
