/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import Utils.Utils;

/**
 *
 * @author Hamdi
 */
public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        System.out.println("1");

        Utils.sendMail("hamdi.megdiche@esprit.tn", Utils.generateCode(6));// 21s 1.6.1
       

        System.out.println("2");

       
//Utils.sendMail("imen.benabderrahmen@esprit.tn", Utils.generateCode(6));
    }

}
