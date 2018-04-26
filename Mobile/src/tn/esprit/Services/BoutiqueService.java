/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.Services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import tn.esprit.entities.Boutique;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author sana
 */
public class BoutiqueService {

    public void add(Boutique boutique) {
        String url = "http://localhost/pidev/WEB/web/app_dev.php/api/boutique/add?name="
                + boutique.getNom();
        //true si post false sinon
        ConnectionRequest con = new ConnectionRequest(url, true);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    public ArrayList<Boutique> getTasks() {
        ArrayList<Boutique> listb = new ArrayList<>();
        String url = "http://localhost/pidev/WEB/web/app_dev.php/api/boutique/all";
        ConnectionRequest con = new ConnectionRequest(url, false) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                JSONParser json = new JSONParser();

                try (Reader r = new InputStreamReader(input)) {
                    Map<String, Object> data = json.parseJSON(r);
                    java.util.List<Map<String, Object>> content = (java.util.List<Map<String, Object>>) data.get("root");
                    System.out.println("content" + content);
                    for (Map<String, Object> obj : content) {
                        Boutique b = new Boutique();
                        Double dId = (Double) obj.get("id");
                        int id = dId.intValue();
                        b.setId(id);
                        b.setNom((String) obj.get("name"));
//                       
//
//                        java.util.List<Map<String, Object>> userContent = (java.util.List<Map<String, Object>>) data.get("idUser");
//                        for (Map<String, Object> uobj : userContent) {
//                            User user = new User();
//                            Double duId = (Double) uobj.get("id");
//                            int uid = duId.intValue();
//                            user.setId(uid);
//                            user.setName((String) uobj.get("name"));
//                            System.out.println("user ==== " + user);
//                            task.setUser(user);
//                        }

                        listb.add(b);
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            }
        };
        InfiniteProgress progress = new InfiniteProgress();
        Dialog dialog = progress.showInifiniteBlocking();
        con.setDisposeOnCompletion(dialog);
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(" la liste fi wost el service === " + listb);
        return listb;
    }

}
