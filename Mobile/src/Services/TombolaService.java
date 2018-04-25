package Services;

import Entities.Tombola;
import Entities.User;
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

/**
 *
 * @author Hamdi
 */
public class TombolaService {

    UserService us = new UserService();

    public TombolaService() {
    }

    Tombola ajouterTombola(Tombola t) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/add");

            r.addArgument("titre", t.getTitre());
            r.addArgument("description", t.getDescription());
            r.addArgument("idArtisan", t.getArtisan().getId());
            r.addArgument("idGagnant", t.getGagnant().getId());
            r.addArgument("dateTirage", t.getDateTirage()); // "2018-11-10 23:24:50"

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            User artisan = us.getUser(((Map<String, Object>) response.get("idArtisan")).get("id").toString());
            User gagnant = null;
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
                gagnant = us.getUser(g.get("id").toString());
            }

            t = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), response.get("dateModif").toString(),
                    artisan, gagnant, response.get("path").toString());
            return t;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    Tombola ajouterTombolaPhoto(String id, String filePath) {
        Tombola t = new Tombola();
        try {
            MultipartRequest r = new MultipartRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/addImage/" + id.substring(0, id.indexOf('.')));
            r.setPost(true);
            r.addData("path", filePath, "image/jpeg");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            User artisan = us.getUser(((Map<String, Object>) response.get("idArtisan")).get("id").toString());
            User gagnant = null;
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
                gagnant = us.getUser(g.get("id").toString());
            }

            t = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), response.get("dateModif").toString(),
                    artisan, gagnant, response.get("path").toString());
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

                User artisan = us.getUser(((Map<String, Object>) obj.get("idArtisan")).get("id").toString());
                User gagnant = null;
                Map<String, Object> g = (Map<String, Object>) obj.get("idGagnant");
                if (g != null) {
                    gagnant = us.getUser(g.get("id").toString());
                }
                tombolas.add(new Tombola(obj.get("id").toString(), obj.get("titre").toString(), obj.get("description").toString(),
                        obj.get("dateAjout").toString(), obj.get("dateTirage").toString(), obj.get("dateModif").toString(),
                        artisan, gagnant, obj.get("path").toString()));
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return tombolas;
    }

    public Tombola getTombola(String id) {

        //  List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
        Tombola t;
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/find/" + id.substring(0, id.indexOf('.')));
            r.setPost(false);
            r.setHttpMethod("GET");

//            InfiniteProgress prog = new InfiniteProgress();
//            Dialog dlg = prog.showInifiniteBlocking();
//            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            User artisan = us.getUser(((Map<String, Object>) response.get("idArtisan")).get("id").toString());
            User gagnant = null;
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
                gagnant = us.getUser(g.get("id").toString());
            }

            t = new Tombola(response.get("id").toString(), response.get("titre").toString(), response.get("description").toString(),
                    response.get("dateAjout").toString(), response.get("dateTirage").toString(), response.get("dateModif").toString(),
                    artisan, gagnant, response.get("path").toString());

            //content = (List<Map<String, Object>>) response.get("root");
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return t;
    }

}
