package tn.esprit.app;

import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import tn.esprit.GUI.HomeForm;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import java.util.Date;
import tn.esprit.GUI.*;

import tn.esprit.Services.UserService;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Enumerations.TypeUser;
import tn.esprit.entities.Panier;
import tn.esprit.entities.User;

public class Main {

    public static Form shome;
    public static Resources stheme;
    private Form current;
    private Resources theme;
    public static User userConnected = null;
    public static Panier monpanier;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initNamedTheme("/theme", "Theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(false);
        this.stheme = theme;
        this.userConnected = new UserService().getUser("40");// 40 client 41 artisan

    }

    public void start() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowString=formater.format(new Date());
        monpanier = new Panier(Integer.valueOf(userConnected.getId()),nowString);
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
            new ConnectForm().show();
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
             new ProduitForm().show();
        });
        if(userConnected.getType()==TypeUser.Client)
        {
            tb.addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_ACCOUNT_BALANCE_WALLET, e -> {
            new PanierForm().show();
        });
        }
        
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
