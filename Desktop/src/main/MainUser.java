/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Services.*;
import Entities.*;
import Utils.Enumerations.*;
import java.time.LocalDateTime;

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
        us.ajouterUser(new User("HamdiMegdiche", "548797", TypeUser.Client, "Megdiche", "Hamdi", LocalDateTime.now(), "M", "ajhjldhlsahds", "sdasdgasdg", "1254879"));

    }

}
