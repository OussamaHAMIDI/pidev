package tn.esprit.app;

import com.codename1.components.FloatingActionButton;
import com.codename1.io.Util;
import tn.esprit.GUI.HomeForm;
import com.codename1.messaging.Message;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.GUI.BoutiqueForm;
import tn.esprit.GUI.HistoriqueForm;
import tn.esprit.GUI.PanierForm;
import tn.esprit.GUI.ProduitForm;
import tn.esprit.GUI.SignUpForm;
import tn.esprit.GUI.StatistiqueForm;
import tn.esprit.GUI.TombolaForm;
<<<<<<< HEAD
import tn.esprit.entities.Panier;
=======
import tn.esprit.Services.EvaluationService;
import tn.esprit.Services.TombolaService;
import tn.esprit.Services.UserService;
import tn.esprit.entities.Evaluation;
import tn.esprit.entities.User;
>>>>>>> c42e4965bf31c6eb46272ff054587fd314d459aa

public class Main {

    public static Form shome;
    public static Resources stheme;
    private Form current;
    private Resources theme;
<<<<<<< HEAD
    public static Panier monpanier;
=======
    public static User userConnected = null;
>>>>>>> c42e4965bf31c6eb46272ff054587fd314d459aa

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initNamedTheme("/theme", "Theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(false);
        this.stheme = theme;
        this.userConnected = new UserService().getUser("41");// 40 client 41 artisan

    }

    public void start() {
          monpanier = new Panier(5,"1/5/2018");
        if (current != null) {
            current.show();
            return;
        }

        //Styling buttons ac java :D
        //Button.setCapsTextDefault(true);
        Button.setButtonRippleEffectDefault(true);
        Button btn = new Button();
        btn.setText("Envoyer mail");
        btn.setUIID("RaisedButton");

//        EvaluationService es = new EvaluationService();
//        List<Evaluation> le = es.getTopBoutiques();
//        for (Evaluation e : le) {
//            System.out.println(e.getBoutique());
//            System.out.println(e.getNote());
//        }
        
        
        
        btn.addActionListener(e -> {
            String htmlBody = "";
            InputStream in = Display.getInstance().getResourceAsStream(Form.class, "/gagnant.html");
            if (in != null) {
                try {
                    htmlBody = Util.readToString(in);
                    in.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                    htmlBody = "Read Error";
                }
            }
            Message m = new Message(htmlBody);
//            m = new Message("<html><body>Check out <a href=\"https://www.codenameone.com/\">Codename One</a>"
//                    + "</body></html>");
//            
            m.setMimeType(Message.MIME_HTML);

            Display.getInstance().sendMessage(new String[]{"hamdi.megdiche@esprit.tn"}, "Souk lemdina : Gagnant Tombola", m);
        });

        //Styling fi wost el theme hashtable
//        Hashtable h = new Hashtable();
//        h.put("fgColor", "ffffff");
//        UIManager.getInstance().addThemeProps(h);
//        Display.getInstance().getCurrent().refreshTheme();
        current = new HomeForm();
        Toolbar toolBar = new Toolbar();

        current.setToolbar(toolBar);

        current.add(btn);

        this.shome = current;

        Toolbar tb = current.getToolbar();

        Image icon = theme.getImage("profile-mask-white.png");
        Container topBar = BorderLayout.center(new Label(icon));

        topBar.add(BorderLayout.SOUTH,
                new Label("Souk Lemdina", "SidemenuTagline"));
        topBar.setUIID(
                "SideCommand");
        tb.addComponentToSideMenu(topBar);

        tb.addMaterialCommandToSideMenu("Mon profil", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
        });
        tb.addMaterialCommandToSideMenu("Boutiques", FontImage.MATERIAL_STORE, e -> {
            BoutiqueForm bf = new BoutiqueForm();
            bf.show();
        });
        tb.addMaterialCommandToSideMenu("Statistiques", FontImage.MATERIAL_SETTINGS, e -> {
            StatistiqueForm sf = new StatistiqueForm();
            sf.show();
        });
        tb.addMaterialCommandToSideMenu("Historiques", FontImage.MATERIAL_HISTORY, e -> {

            HistoriqueForm hf = new HistoriqueForm();
            hf.show();

        });
        tb.addMaterialCommandToSideMenu("Tombolas", FontImage.MATERIAL_STARS, e -> {
            TombolaForm tf = new TombolaForm();
            tf.show();
        });

        tb.addMaterialCommandToSideMenu("Produits", FontImage.MATERIAL_ALBUM, e -> {
              ProduitForm pf = new ProduitForm();
            pf.show();
        });

        tb.addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_ACCOUNT_BALANCE_WALLET, e -> {
          PanierForm pf = new PanierForm();
            pf.show();
        });
        tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {
        });
        tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {
            new SignUpForm().show();
        });

//        Iterable<Command> commands = tb.getSideMenuCommands();
//        MenuBar mb = tb.getMenuBar();
////        mb.setUIID("MenuBar");
//        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
//        fab.createSubFAB(FontImage.MATERIAL_PEOPLE, "");
//        fab.createSubFAB(FontImage.MATERIAL_IMPORT_CONTACTS, "");
//        fab.bindFabToContainer(current.getContentPane());
        current.show();
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
