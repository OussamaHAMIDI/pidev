/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author oussamahamidi
 */
public class SmsSender {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SmsSender() {
        this.url = "http://172.20.10.2:8080/v1/sms/send/?";
    }


    public int sendSms(String message, String num) throws IOException {
    URL url = new URL(this.getUrl()+"phone="+num+"&message="+message);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    int status = con.getResponseCode();
    System.out.println(status);
    return status;
}
    
}
