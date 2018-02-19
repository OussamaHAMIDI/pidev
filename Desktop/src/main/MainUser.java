/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Entities.User;
import Services.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @author Hamdi
 */
public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        UserService us = new UserService();
        //User u = us.getUserByUsername("HamdiMegdiche");
        User u = us.getUserById(1);
        
            u.setPhoto(new FileInputStream("src/images/HINTS.png"));
           
            us.modifierUser(u);
            
//        us.modifierEtatUser(u, EtatUser.Connected);
//         us.modifierEtatUser(u, EtatUser.Disconnected);
//         us.modifierEtatUser(u, EtatUser.Connected);


// Utils.sendMail("hamdi.megdiche@esprit.tn", Utils.generateCode(6));
//Utils.sendMail("imen.benabderrahmen@esprit.tn", Utils.generateCode(6));
       
    }

}
