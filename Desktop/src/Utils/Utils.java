/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Hamdi
 */
public class Utils {

    public static String generateCode(int length) {

        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String hashPassword(String password_plaintext, String salt) {
        int cost = 4;// cost (iterations) in fos bundle*********************
        String digest = BCrypt.hashpw(password_plaintext, salt);
        for (int i = 0; i < cost - 2; i++) {
            digest = BCrypt.hashpw(password_plaintext, salt);
        }
        return digest;
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Hash invalide");
        }
        return BCrypt.checkpw(password_plaintext, stored_hash);
    }

    public static boolean sendMail(String to_mail, String code) {

        final String userName = "hamdi.megdiche@esprit.tn";
        final String password = "25111995";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(to_mail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_mail));
            msg.setSubject("Code d'activation du compte Souk lemdina");

            msg.setSentDate(new Date(System.currentTimeMillis()));
            String htmlBody = new String(Files.readAllBytes(Paths.get("src/Utils/mail.html"))).replace("123456", code);

//            // creates message part
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setContent(htmlBody, "text/html");
//
//            // creates multi-part
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(messageBodyPart);
//
//            MimeBodyPart imagePart = new MimeBodyPart();
//            imagePart.setHeader("Content-ID", "<souk>"); // cid:souk
//            imagePart.setDisposition(MimeBodyPart.INLINE);
//            imagePart.attachFile("src/Images/souk.png");
//          
//            multipart.addBodyPart(imagePart);
            msg.setContent(htmlBody, "text/html");
            Transport.send(msg);
            System.out.println("Mail envoyé");
            return true;

        } catch (MessagingException e) {
            System.out.println("Echec de l'envoie du mail \n" + e.getMessage());
            return false;
        } catch (IOException e) {
            return false;
        }

    }
    
    
    /**
     * **** To use this function ****
     *
     * Retrieve value From MySQL with : resultatSet.getString("date") =
     * 2018-02-10 01:09:51.0
     *
     * @param date from MySQL
     * @return null if date is different from this pattern ("yyyy-MM-dd
     * HH:mm:ss")
     */
    public static LocalDateTime getLocalDateTime(String date) {

        LocalDateTime l;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            date = date.substring(0, 19);
            l = LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            //System.out.println("Exception in getLocalDate :\n"+e.getMessage());
            return null;
        }
        return l;
    }
    
    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void showTrayNotification(NotificationType type, String title, String header, String text, InputStream img) {
        TrayNotification tray = new TrayNotification();
        tray.setNotificationType(type);
        tray.setTitle(title);
        tray.setMessage(text);
        tray.setAnimationType(AnimationType.FADE);
        tray.showAndDismiss(Duration.millis(1500));
        tray.setRectangleFill(Color.valueOf("#4183D7"));
        tray.setImage(new Image(img));
    }


}
