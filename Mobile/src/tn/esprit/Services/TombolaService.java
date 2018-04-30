package tn.esprit.Services;

import tn.esprit.entities.Tombola;
import tn.esprit.entities.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Enumerations;

/**
 *
 * @author Hamdi
 */
public class TombolaService {

    UserService us = new UserService();

    public TombolaService() {
    }

    public Tombola addOrEditTombola(Tombola t, String o) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/addOrEdit");

            if (t.getId().length() > 1) {
                r.addArgument("id", t.getId());
            }
            r.addArgument("titre", t.getTitre());
            r.addArgument("description", t.getDescription());
            r.addArgument("idArtisan", t.getArtisan().getId());
            if (t.getGagnant() != null) {
                r.addArgument("idGagnant", t.getGagnant().getId());
            }
            r.addArgument("dateTirage", t.getDateTirage()); // "2018-11-10 23:24:50"

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> a = (Map<String, Object>) response.get("idArtisan");

            User artisan = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

            User gagnant = null;
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
                gagnant = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
            }
            Tombola tt = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), response.get("dateModif").toString(),
                    artisan, gagnant, "later");

            String path = "";
            if (o.equals("a")) {
                path = t.getPhoto();
                tt = ajouterTombolaPhoto(tt.getId(), t.getPhoto(), "a");

            } else {//edit
                if (t.getPhoto().contains("temp")) {
                    tt = ajouterTombolaPhoto(tt.getId(), t.getPhoto(), "e");
                    System.out.println("update photo");
                } else {
                    // don't upload photo again ! 
                    System.out.println("don't upload photo again ");
                }
            }

            return tt;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    public Tombola ajouterTombolaPhoto(String id, String filePath, String o) {
        Tombola t = new Tombola();
        try {
            if (id.indexOf(".") > 0) {
                id = id.substring(0, id.indexOf('.'));
            }
            MultipartRequest r = new MultipartRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/addImage/" + id);
            r.setPost(true);
            r.addData("path", filePath, "image/jpeg");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> a = (Map<String, Object>) response.get("idArtisan");

            User artisan = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

            User gagnant = null;
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
                gagnant = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
            }

            t = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), "",
                    artisan, gagnant, response.get("path").toString());
            if (o.equals("e")) {
                t.setDateModif(response.get("dateModif").toString());
            }
            return t;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    public List<Tombola> getTombolas() {

        List<Tombola> tombolas = new ArrayList<Tombola>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/all");
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

                Map<String, Object> a = (Map<String, Object>) obj.get("idArtisan");

                User artisan = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

                User gagnant = null;
                Map<String, Object> g = (Map<String, Object>) obj.get("idGagnant");
                if (g != null) {
                    gagnant = new User(g.get("id").toString(), g.get("username").toString(), g.get("password").toString(),
                            Enumerations.EtatUser.valueOf(g.get("etat").toString()), Enumerations.TypeUser.valueOf(g.get("type").toString()),
                            g.get("nom").toString(), g.get("prenom").toString(), g.get("dateNaissance").toString(), g.get("sexe").toString(),
                            g.get("email").toString(), g.get("adresse").toString(), g.get("tel").toString(), g.get("pathPhotoProfil").toString());
                }

                tombolas.add(new Tombola(obj.get("id").toString(), obj.get("titre").toString(), obj.get("description").toString(),
                        obj.get("dateAjout").toString(), obj.get("dateTirage").toString(), obj.get("dateModif").toString(),
                        artisan,
                        gagnant,
                        obj.get("path").toString()));
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return tombolas;
    }

    public Tombola getTombola(String id) {
        Tombola t;
        try {
            ConnectionRequest r = new ConnectionRequest();
            if (id.indexOf(".") > 0) {
                id = id.substring(0, id.indexOf('.'));
            }
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/find/" + id);
            r.setPost(false);
            r.setHttpMethod("GET");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> a = (Map<String, Object>) response.get("idArtisan");

            User artisan = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

            User gagnant = null;
            a = null;
            a = (Map<String, Object>) response.get("idGagnant");
            if (a != null) {
                gagnant = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
            }

            t = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), response.get("dateModif").toString(),
                    artisan, gagnant, response.get("path").toString());
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return t;
    }

}
