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
import tn.esprit.entities.Evaluation;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class EvaluationService {

    UserService us = new UserService();
    BoutiqueService bs = new BoutiqueService();

    public EvaluationService() {
    }

    public Evaluation addEvaluationBoutique(Evaluation e) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/evaluation/addBoutique");

            r.addArgument("note", String.valueOf(e.getNote()));
            r.addArgument("idBoutique", e.getBoutique().getId());
            r.addArgument("idUser", e.getUser().getId());

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
            String no = (String) response.get("note");
            int note = Integer.parseInt(no);
            Boutique boutique = new Boutique(b.get("id").toString(), b.get("adresse").toString(), longitude,
                    latitude, b.get("pathPhoto").toString(), b.get("nom").toString(), b.get("dateCreation").toString(), null);
            e = new Evaluation(response.get("id").toString(), response.get("dateCreation").toString(), user, boutique, note);
            return e;
        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    public List<Evaluation> getEvaluations() {

        List<Evaluation> evaluations = new ArrayList<Evaluation>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/evaluation/all");
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

                Double no = (Double) b.get("note");
                int note = no.intValue();
                Evaluation e = new Evaluation(response.get("id").toString(), response.get("dateCreation").toString(), user,
                        boutique, note);
                evaluations.add(e);
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        System.out.println(evaluations);
        return evaluations;
    }

    public Evaluation getEvaluation(String id) {
        Evaluation e;
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

            Double no = (Double) b.get("note");
            int note = no.intValue();
            e = new Evaluation(response.get("id").toString(), response.get("dateCreation").toString(), user,
                    boutique, note);
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return e;
    }

}
