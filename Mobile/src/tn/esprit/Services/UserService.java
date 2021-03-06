package tn.esprit.Services;

import tn.esprit.entities.Enumerations.*;
import tn.esprit.entities.User;
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

/**
 *
 * @author Hamdi
 */
public class UserService {

    public UserService() {
    }

    User ajouterUser(User u) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/user/add");

            r.addArgument("nom", u.getNom());
            r.addArgument("prenom", u.getPrenom());
            r.addArgument("email", u.getEmail());
            r.addArgument("dateNaissance", u.getDateNaissance());
            r.addArgument("type", u.getType().toString()); 
            r.addArgument("etat", u.getEtat().toString());

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            u = new User(response.get("id").toString(), response.get("username").toString(), response.get("password").toString(),
                    EtatUser.valueOf(response.get("etat").toString()), TypeUser.valueOf(response.get("type").toString()),
                    response.get("nom").toString(), response.get("prenom").toString(), response.get("dateNaissance").toString(),
                    response.get("sexe").toString(), response.get("email").toString(), response.get("adresse").toString(),
                    response.get("tel").toString(), response.get("pathPhotoProfil").toString());
            return u;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    User ajouterUserPhoto(String id, String filePath) {
        User u = new User();
        try {
            if(id.indexOf(".")>0){
                id = id.substring(0, id.indexOf('.'));
            }
            MultipartRequest r = new MultipartRequest();
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/user/addImage/" + id);
            r.setPost(true);
            r.addData("path", filePath, "image/jpeg");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            u = new User(response.get("id").toString(), response.get("username").toString(), response.get("password").toString(),
                    EtatUser.valueOf(response.get("etat").toString()), TypeUser.valueOf(response.get("type").toString()),
                    response.get("nom").toString(), response.get("prenom").toString(), response.get("dateNaissance").toString(),
                    response.get("sexe").toString(), response.get("email").toString(), response.get("adresse").toString(),
                    response.get("tel").toString(), response.get("pathPhotoProfil").toString());
            return u;

        } catch (IOException err) {
            Log.e(err);
            return null;
        }
    }

    public List<User> getUsers() {

        List<User> users = new ArrayList<>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/users/all");
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
                users.add(new User(obj.get("id").toString(), obj.get("username").toString(), obj.get("password").toString(),
                    EtatUser.valueOf(obj.get("etat").toString()), TypeUser.valueOf(obj.get("type").toString()),
                    obj.get("nom").toString(), obj.get("prenom").toString(), obj.get("dateNaissance").toString(),
                    obj.get("sexe").toString(), obj.get("email").toString(), obj.get("adresse").toString(),
                    obj.get("tel").toString(), obj.get("pathPhotoProfil").toString()));
            }

        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return users;
    }

    public User getUser(String id) {
        User u;
        try {
            ConnectionRequest r = new ConnectionRequest();
            if(id.indexOf(".")>0){
                id = id.substring(0, id.indexOf('.'));
            }
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/user/find/" + id);
            r.setPost(false);
            r.setHttpMethod("GET");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            
            u = new User(response.get("id").toString(), response.get("username").toString(), response.get("password").toString(),
                    EtatUser.valueOf(response.get("etat").toString()), TypeUser.valueOf(response.get("type").toString()),
                    response.get("nom").toString(), response.get("prenom").toString(), response.get("dateNaissance").toString(),
                    response.get("sexe").toString(), response.get("email").toString(), response.get("adresse").toString(),
                    response.get("tel").toString(), response.get("pathPhotoProfil").toString());
        } catch (IOException err) {
            Log.e(err);
            return null;
        }

        return u;
    }
    
    public User connect(String username,String password) {
        User u;
        try {
            ConnectionRequest r = new ConnectionRequest();
            
            r.setPost(false);
            r.setHttpMethod("GET");
            r.setUrl("http://localhost/pidev/WEB/web/app_dev.php/api/user/connect");

            r.addArgument("username", username);
            r.addArgument("password", password);

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            
            u = new User(response.get("id").toString(), response.get("username").toString(), response.get("password").toString(),
                    EtatUser.valueOf(response.get("etat").toString()), TypeUser.valueOf(response.get("type").toString()),
                    response.get("nom").toString(), response.get("prenom").toString(), response.get("dateNaissance").toString(),
                    response.get("sexe").toString(), response.get("email").toString(), response.get("adresse").toString(),
                    response.get("tel").toString(), response.get("pathPhotoProfil").toString());
        } catch (Exception err) {
            //Log.e(err);
            return null;
        }

        return u;
    }

}
