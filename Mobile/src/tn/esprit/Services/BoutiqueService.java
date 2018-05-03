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
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import tn.esprit.entities.Boutique;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.User;

/**
 *
 * @author sana
 */
public class BoutiqueService {

    public BoutiqueService() {
    }

    public Boutique add(Boutique boutique) {

        ConnectionRequest r = new ConnectionRequest();
        r.setPost(true);
        r.setHttpMethod("GET");
        r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/boutique/add");
        r.addArgument("name", boutique.getNom());
        r.addArgument("adresse", boutique.getAdresse());
        r.addArgument("idUser", boutique.getUser().getId());

        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(r);
        try {
            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> u = (Map<String, Object>) response.get("idUser");

            User user = new User(u.get("id").toString(), u.get("username").toString(), u.get("password").toString(),
                    Enumerations.EtatUser.valueOf(u.get("etat").toString()), Enumerations.TypeUser.valueOf(u.get("type").toString()),
                    u.get("nom").toString(), u.get("prenom").toString(), u.get("dateNaissance").toString(), u.get("sexe").toString(),
                    u.get("email").toString(), u.get("adresse").toString(), u.get("tel").toString(), u.get("pathPhotoProfil").toString());

            Double lo = (Double) response.get("longitude");
            Double la = (Double) response.get("altitude");
            float longitude = lo.floatValue();
            float latitude = la.floatValue();
            Boutique b = new Boutique(response.get("id").toString(), response.get("adresse").toString(), longitude,
                    latitude, response.get("pathPhoto").toString(), response.get("nom").toString(), response.get("dateCreation").toString(),
                    user);
            ajouterBoutiquePhoto(b.getId(), boutique.getPhoto());
            return b;
        } catch (IOException ex) {
            Log.e(ex);
            return null;
        }

    }

    public List<Boutique> getBoutiques() {

        List<Boutique> boutiques = new ArrayList<Boutique>();
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/boutique/all");
            r.setPost(false);
            r.setHttpMethod("GET");
            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("root");

            for (Map<String, Object> obj : content) {

                Map<String, Object> u = (Map<String, Object>) obj.get("idUser");

                User user = new User(u.get("id").toString(), u.get("username").toString(), u.get("password").toString(),
                        Enumerations.EtatUser.valueOf(u.get("etat").toString()), Enumerations.TypeUser.valueOf(u.get("type").toString()),
                        u.get("nom").toString(), u.get("prenom").toString(), u.get("dateNaissance").toString(), u.get("sexe").toString(),
                        u.get("email").toString(), u.get("adresse").toString(), u.get("tel").toString(), u.get("pathPhotoProfil").toString());

                Double lo = (Double) obj.get("longitude");
                Double la = (Double) obj.get("altitude");
                float longitude = lo.floatValue();
                float latitude = la.floatValue();
                boutiques.add(new Boutique(obj.get("id").toString(), obj.get("adresse").toString(), longitude,
                        latitude, obj.get("pathPhoto").toString(), obj.get("nom").toString(), obj.get("dateCreation").toString(),
                        user));
            }
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        //System.out.println(boutiques);
        return boutiques;
    }

    public List<Boutique> getBoutiquesUser(String id) {

        List<Boutique> boutiques = new ArrayList<Boutique>();
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/boutique/allUser/" + id);
            r.setPost(false);
            r.setHttpMethod("GET");
            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("root");

            for (Map<String, Object> obj : content) {

                Map<String, Object> u = (Map<String, Object>) obj.get("idUser");

                User user = new User(u.get("id").toString(), u.get("username").toString(), u.get("password").toString(),
                        Enumerations.EtatUser.valueOf(u.get("etat").toString()), Enumerations.TypeUser.valueOf(u.get("type").toString()),
                        u.get("nom").toString(), u.get("prenom").toString(), u.get("dateNaissance").toString(), u.get("sexe").toString(),
                        u.get("email").toString(), u.get("adresse").toString(), u.get("tel").toString(), u.get("pathPhotoProfil").toString());

                Double lo = (Double) obj.get("longitude");
                Double la = (Double) obj.get("altitude");
                float longitude = lo.floatValue();
                float latitude = la.floatValue();
                boutiques.add(new Boutique(obj.get("id").toString(), obj.get("adresse").toString(), longitude,
                        latitude, obj.get("pathPhoto").toString(), obj.get("nom").toString(), obj.get("dateCreation").toString(),
                        user));
            }
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        System.out.println(boutiques);
        return boutiques;
    }

    public Boutique deleteBoutique(String id) {
        Boutique b;
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/boutique/delete/" + id.substring(0, id.indexOf('.')));
            r.setPost(false);
            r.setHttpMethod("GET");
            

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> a = (Map<String, Object>) response.get("idUser");

            User user = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

            Double lo = (Double) a.get("longitude");
            Double la = (Double) a.get("altitude");
            float longitude = lo.floatValue();
            float latitude = la.floatValue();
            b = new Boutique(a.get("id").toString(), a.get("adresse").toString(), longitude,
                    latitude, a.get("pathPhoto").toString(), a.get("nom").toString(), a.get("dateCreation").toString(),
                    user);
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return b;
    }

    public Boutique ajouterBoutiquePhoto(String id, String filePath) {
        Boutique b;
        try {
            if (id.indexOf(".") > 0) {
                id = id.substring(0, id.indexOf('.'));
            }
            MultipartRequest r = new MultipartRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/boutique/addImage/" + id);
            r.setPost(true);
            r.addData("path", filePath, "image/jpeg");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

//            Map<String, Object> a = (Map<String, Object>) response.get("idUser");
//
//            User user = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
//                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
//                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
//                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
//
//            System.out.println(a.get("longitude"));
//            System.out.println(a.get("latitude"));
//            System.out.println(a.get("pathPhoto"));
//            
//            
//            Double lo = (Double) a.get("longitude");
//            Double la = (Double) a.get("altitude");
//            float longitude = lo.floatValue();
//            float latitude = la.floatValue();
//            b = new Boutique(a.get("id").toString(), a.get("adresse").toString(), longitude,
//                    latitude, a.get("pathPhoto").toString(), a.get("nom").toString(), a.get("dateCreation").toString(),
//                    user);
//            return b;
b = new Boutique();
return b ;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    /*public ArrayList<Boutique> getTasks() {
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
    }*/
}
