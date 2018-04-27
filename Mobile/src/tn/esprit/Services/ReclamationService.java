package tn.esprit.Services;

import tn.esprit.entities.Tombola;
import tn.esprit.entities.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Reclamation;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class ReclamationService {

    UserService us = new UserService();
    BoutiqueService bs = new BoutiqueService();

    public ReclamationService() {
    }

    public Reclamation addReclamation(Reclamation rec) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/reclamation/add");

            r.addArgument("description", rec.getDescription());
            r.addArgument("idBoutique", rec.getBoutique().getId());
            r.addArgument("idUser", rec.getUser().getId());

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            Map<String, Object> a = (Map<String, Object>) response.get("idUser");
            Map<String, Object> b = (Map<String, Object>) response.get("idBoutique");

            User user = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                    Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                    a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                    a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());

            Double lo = (Double) b.get("longitude");
            Double la = (Double) b.get("altitude");
            float longitude = lo.floatValue();
            float latitude = la.floatValue();
            Boutique boutique = new Boutique(b.get("id").toString(), b.get("adresse").toString(), longitude,
                    latitude, b.get("pathPhoto").toString(), b.get("nom").toString(), b.get("dateCreation").toString(),
                    null);

            rec = new Reclamation(response.get("id").toString(), response.get("dateCreation").toString(), user,
                    boutique, response.get("description").toString());
            return rec;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    public List<Reclamation> getReclamations() {

        List<Reclamation> reclamations = new ArrayList<Reclamation>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/reclamation/all");
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

                Map<String, Object> a = (Map<String, Object>) obj.get("idUser");
                Map<String, Object> b = (Map<String, Object>) obj.get("idBoutique");

                User user = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
                Double lo = (Double) b.get("longitude");
                Double la = (Double) b.get("altitude");
                float longitude = lo.floatValue();
                float latitude = la.floatValue();

                Boutique boutique = new Boutique(b.get("id").toString(), b.get("adresse").toString(), longitude,
                        latitude, b.get("pathPhoto").toString(), b.get("nom").toString(), b.get("dateCreation").toString(),
                        null);

                System.out.println(obj.get("id"));
                System.out.println(obj.get("dateCreation"));
                System.out.println(obj.get("description"));
                Reclamation rec = new Reclamation(obj.get("id").toString(), obj.get("dateCreation").toString(), user,
                        boutique, obj.get("description").toString());
                reclamations.add(rec);
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        System.out.println(reclamations);
        return reclamations;
    }
    
    public List<Reclamation> getReclamationsUser(String id) {

        List<Reclamation> reclamations = new ArrayList<Reclamation>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/reclamation/allUser");
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

                Map<String, Object> a = (Map<String, Object>) obj.get("idUser");
                Map<String, Object> b = (Map<String, Object>) obj.get("idBoutique");

                User user = new User(a.get("id").toString(), a.get("username").toString(), a.get("password").toString(),
                        Enumerations.EtatUser.valueOf(a.get("etat").toString()), Enumerations.TypeUser.valueOf(a.get("type").toString()),
                        a.get("nom").toString(), a.get("prenom").toString(), a.get("dateNaissance").toString(), a.get("sexe").toString(),
                        a.get("email").toString(), a.get("adresse").toString(), a.get("tel").toString(), a.get("pathPhotoProfil").toString());
                Double lo = (Double) b.get("longitude");
                Double la = (Double) b.get("altitude");
                float longitude = lo.floatValue();
                float latitude = la.floatValue();

                Boutique boutique = new Boutique(b.get("id").toString(), b.get("adresse").toString(), longitude,
                        latitude, b.get("pathPhoto").toString(), b.get("nom").toString(), b.get("dateCreation").toString(),
                        null);

                System.out.println(obj.get("id"));
                System.out.println(obj.get("dateCreation"));
                System.out.println(obj.get("description"));
                Reclamation rec = new Reclamation(obj.get("id").toString(), obj.get("dateCreation").toString(), user,
                        boutique, obj.get("description").toString());
                reclamations.add(rec);
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        System.out.println(reclamations);
        return reclamations;
    }

    public Tombola getTombola(String id) {
        Tombola t;
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/tombola/find/" + id.substring(0, id.indexOf('.')));
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
            Map<String, Object> g = (Map<String, Object>) response.get("idGagnant");
            if (g != null) {
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
