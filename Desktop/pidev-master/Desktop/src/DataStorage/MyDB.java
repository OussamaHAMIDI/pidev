<<<<<<< HEAD:Desktop/src/DataStorage/MyDB.java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;

/**
 *
 * @author Hamdi
 */
public class MyDB {

    Connection connexion;
    final String url = "jdbc:mysql://localhost:3306/souk"; // !************ change db name **************** 
    final String user = "root";
    final String password = "";
    private static MyDB instance = null;

    private MyDB() {
        try {
            connexion = DriverManager.getConnection(url, user, password);
            System.out.println("*****************************\nConnexion établie");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\n erreur connexion " + ex.getMessage());
        }
    }

    public static MyDB getinstance() {
        if (instance == null) {
            instance = new MyDB();
        }

        return instance;
    }

    public Connection getConnexion() {
        return connexion;
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamdi
 */
public class MyDB {

    Connection connexion;
    final String url = "jdbc:mysql://localhost:3306/souk?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8";
    final String user = "root";
    final String password = "";
    private static MyDB instance = null;

    private MyDB() {
        try {
            connexion = DriverManager.getConnection(url, user, password);
            System.out.println("*****************************\nConnexion établie");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\nConnexion echouée !! ");
        }
    }

    public static MyDB getinstance() {
        if (instance == null) {
            instance = new MyDB();
        }

        return instance;
    }

    public Connection getConnexion() {
        return connexion;
    }
}
>>>>>>> 3af20d300f3f53927f85836c56e58c605a351b7a:Desktop/pidev-master/Desktop/src/DataStorage/MyDB.java
