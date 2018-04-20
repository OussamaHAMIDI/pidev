/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.neverbounce.api.client.NeverbounceClient;
import com.neverbounce.api.client.NeverbounceClientFactory;
import com.neverbounce.api.model.AccountInfoResponse;
import com.neverbounce.api.model.SingleCheckResponse;
import java.util.regex.Pattern;

/**
 *
 * @author Hamdi
 */
public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//
//        System.out.println("1");
//
//        Utils.sendMail("hamdi.megdiche@esprit.tn", Utils.generateCode(6));// 21s 1.6.1
//       
//
//        System.out.println("2");
        
 String email = "{_test_}@iana.org";
        if (Utils.Utils.verifEmail(email)) {
            System.out.println("valid");
        }
       
       String regex = "^(?!(?:(?:\\x22?\\x5C[\\x00-\\x7E]\\x22?)|(?:\\x22?[^\\x5C\\x22]\\x22?)){255,})(?!(?:(?:\\x22?\\x5C[\\x00-\\x7E]\\x22?)|(?:\\x22?[^\\x5C\\x22]\\x22?)){65,}@)(?:(?:[\\x21\\x23-\\x27\\x2A\\x2B\\x2D\\x2F-\\x39\\x3D\\x3F\\x5E-\\x7E]+)|(?:\\x22(?:[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x21\\x23-\\x5B\\x5D-\\x7F]|(?:\\x5C[\\x00-\\x7F]))*\\x22))(?:\\.(?:(?:[\\x21\\x23-\\x27\\x2A\\x2B\\x2D\\x2F-\\x39\\x3D\\x3F\\x5E-\\x7E]+)|(?:\\x22(?:[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x21\\x23-\\x5B\\x5D-\\x7F]|(?:\\x5C[\\x00-\\x7F]))*\\x22)))*@(?:(?:(?!.*[^.]{64,})(?:(?:(?:xn--)?[a-z0-9]+(?:-[a-z0-9]+)*\\.){1,126}){1,}(?:(?:[a-z][a-z0-9]*)|(?:(?:xn--)[a-z0-9]+))(?:-[a-z0-9]+)*)|(?:\\[(?:(?:IPv6:(?:(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){7})|(?:(?!(?:.*[a-f0-9][:\\]]){7,})(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,5})?::(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,5})?)))|(?:(?:IPv6:(?:(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){5}:)|(?:(?!(?:.*[a-f0-9]:){5,})(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,3})?::(?:[a-f0-9]{1,4}(?::[a-f0-9]{1,4}){0,3}:)?)))?(?:(?:25[0-5])|(?:2[0-4][0-9])|(?:1[0-9]{2})|(?:[1-9]?[0-9]))(?:\\.(?:(?:25[0-5])|(?:2[0-4][0-9])|(?:1[0-9]{2})|(?:[1-9]?[0-9]))){3}))\\]))$";
       
       if(Pattern.matches(regex, email)){
           System.out.println("valid");
       }else{
           System.out.println("not valid");
       }

        String token = "secret_084d6973f2c771713959f58ae1848bc8";
        NeverbounceClient neverbounceClient = NeverbounceClientFactory.create(token);
//        AccountInfoResponse accountInfoResponse = neverbounceClient
//                .createAccountInfoRequest()
//                .execute();

//        SingleCheckResponse singleCheckResponse = neverbounceClient
//        .prepareSingleCheckRequest()
//        .withEmail("hamdi-megdiche@outlook.fr")
//        .withAddressInfo(false)
//        .withCreditsInfo(false)
//        .withTimeout(300)
//        .build()
//        .execute();
//        
//        System.out.println(singleCheckResponse.getResult().toString());
//Utils.sendMail("imen.benabderrahmen@esprit.tn", Utils.generateCode(6));
    }

}
