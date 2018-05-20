/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Utils.Utils;
import com.neverbounce.api.client.NeverbounceClient;
import com.neverbounce.api.client.NeverbounceClientFactory;
import com.neverbounce.api.model.AccountInfoResponse;
import com.neverbounce.api.model.SingleCheckResponse;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import rest.file.uploader.tn.FileUploader;
import tray.notification.NotificationType;

/**
 *
 * @author Hamdi
 */
public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        String token = "secret_084d6973f2c771713959f58ae1848bc8";
//        NeverbounceClient neverbounceClient = NeverbounceClientFactory.create(token);
////        AccountInfoResponse accountInfoResponse = neverbounceClient
////                .createAccountInfoRequest()
////                .execute();
//
//        SingleCheckResponse singleCheckResponse = neverbounceClient
//        .prepareSingleCheckRequest()
//        .withEmail("hamdi.megdiiche@esprit.tn")
//        .withAddressInfo(false)
//        .withCreditsInfo(false)
//        .withTimeout(300)
//        .build()
//        .execute();
//        
//        System.out.println(singleCheckResponse.getResult().toString());// VALID


//        FileUploader fu = new FileUploader("localhost/pidev/WEB/web");
//
//        try {
//            String fileNameInServer = fu.upload("src/Images/user.png");
//
//            System.out.println(fileNameInServer);
//            Delete
//            if (fu.delete(fileNameInServer)) {
//                System.out.println("File " + fileNameInServer + " deleted.");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(MainUser.class.getName()).log(Level.SEVERE, null, ex);
//        }

       
    }

}
