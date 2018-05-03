package tn.esprit.app;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.io.Util;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import tn.esprit.GUI.HomeForm;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.GUI.BoutiqueForm;
import tn.esprit.GUI.HistoriqueForm;
import tn.esprit.GUI.SignUpForm;
import tn.esprit.GUI.StatistiqueForm;
import tn.esprit.GUI.StatistiquePieForm;
import tn.esprit.GUI.TombolaForm;
import tn.esprit.Services.EvaluationService;
import tn.esprit.Services.TombolaService;
import java.util.Date;
import tn.esprit.GUI.*;

import tn.esprit.Services.UserService;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Enumerations.TypeUser;
import tn.esprit.entities.Evaluation;
import tn.esprit.entities.Panier;
import tn.esprit.entities.User;

public class Main {

    public static Form shome;
    public static Resources stheme;
    private Form current;
    private Resources theme;
    public static User userConnected = null;
    public static Media m;
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
        String nowString = formater.format(new Date());
        monpanier = new Panier(Integer.valueOf(userConnected.getId()), nowString);
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

        EvaluationService es = new EvaluationService();
        List<Evaluation> le = es.getTopBoutiques();
        for (Evaluation e : le) {
            System.out.println(e.getBoutique());
            System.out.println(e.getNote());
            System.out.println(e.getBoutique().getPhoto());
            System.out.println(e.getBoutique().getNom());
            System.out.println(e.getBoutique().getAdresse());
        }

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
        Container boutiques = createBoutiquesContainer(le, current);
        current.addComponent(boutiques);
        current.revalidate();
        Toolbar toolBar = new Toolbar();

        current.setToolbar(toolBar);

        //current.add(btn);
        this.shome = current;

        m = null;
        try {
            m = MediaManager.createMedia("C:\\xampp\\htdocs\\pidev\\Mobile\\song.mp3", false);
            m.play();
        } catch (IOException ex) {

        }

        Toolbar tb = current.getToolbar();

        Image icon = theme.getImage("profile-mask-white.png");
        Container topBar = BorderLayout.center(new Label(icon));

        topBar.add(BorderLayout.SOUTH,
                new Label("Souk Lemdina", "SidemenuTagline"));
        topBar.setUIID(
                "SideCommand");
        tb.addComponentToSideMenu(topBar);

        if (Main.userConnected != null && Main.userConnected.getType() == Enumerations.TypeUser.Artisan) {
            tb.addMaterialCommandToSideMenu("Mes Boutiques", FontImage.MATERIAL_STORE, e -> {
                MesBoutiqueForm bf = new MesBoutiqueForm();
                bf.show();
            });
            tb.addMaterialCommandToSideMenu("Statistiques", FontImage.MATERIAL_SETTINGS, e -> {
                StatistiquePieForm sf = new StatistiquePieForm();
                sf.show();
            });
        } else if (Main.userConnected != null && Main.userConnected.getType() == Enumerations.TypeUser.Client) {
            tb.addMaterialCommandToSideMenu("Mon profil", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
                new ConnectForm().show();
            });

            tb.addMaterialCommandToSideMenu("Historiques", FontImage.MATERIAL_HISTORY, e -> {
                HistoriqueForm hf = new HistoriqueForm();
                hf.show();
            });
            tb.addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_ACCOUNT_BALANCE_WALLET, e -> {
                new PanierForm().show();
            });
        }
        //if (userConnected == null) {
        tb.addMaterialCommandToSideMenu("Connexion", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            new ConnectForm().show();
        });
        //}

        tb.addMaterialCommandToSideMenu("Boutiques", FontImage.MATERIAL_STORE, e -> {
            BoutiqueForm bf = new BoutiqueForm();
            bf.show();
        });

        tb.addMaterialCommandToSideMenu("Tombolas", FontImage.MATERIAL_STARS, e -> {
            TombolaForm tf = new TombolaForm();
            tf.show();
        });

        tb.addMaterialCommandToSideMenu("Produits", FontImage.MATERIAL_ALBUM, e -> {
            new ProduitForm().show();
        });

        tb.addMaterialCommandToSideMenu("Réglages", FontImage.MATERIAL_SETTINGS, e -> {
            SettingsForm sf = new SettingsForm();
            sf.show();
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

    private void showBoutiquesContainer(List<Evaluation> le, Form current) {
        Container dishes = createBoutiquesContainer(le, current);
        current.getContentPane().replace(current.getContentPane().getComponentAt(0), dishes, CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 300));
    }

    private Container createBoutiquesContainer(List<Evaluation> le, Form current) {
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.setScrollableY(true);

        // allows elements to slide into view
        for (Evaluation e : le) {
            Component dish = createBoutiqueComponent(e, current);
            cnt.addComponent(dish);
        }
        return cnt;
    }

    private Container createBoutiqueComponent(Evaluation e, Form current) {
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(current.getWidth(), current.getHeight(), 0xFFFFFFFF), true);
        URLImage img = URLImage.createToStorage(placeholder, e.getBoutique().getPhoto(), "http://localhost/pidev/WEB/web/uploads/images/" + e.getBoutique().getPhoto());
//        ImageViewer iv = new ImageViewer();
//        iv.setImage(img);
//        Image img = URLImage.createToStorage(placeholder, e.getBoutique().getPhoto(), "http://localhost/pidev/WEB/web/uploads/images/", URLImage.RESIZE_SCALE); 
        //Image img = theme.getImage(e.getBoutique().getPhoto());
        img.fetch();
        Container mb = new Container(new BorderLayout());
        mb.getUnselectedStyle().setBgImage(img);
        mb.getSelectedStyle().setBgImage(img);
        mb.getPressedStyle().setBgImage(img);
//        mb.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        mb.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        mb.getSelectedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//        mb.getPressedStyle().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Container box = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button nom = new Button(e.getBoutique().getNom());
        nom.setUIID("TopTitle");
        Label adresse = new Label(e.getBoutique().getAdresse());
        TextArea date = new TextArea("Cette boutique fait partie des top ten boutiques bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        date.setUIID("TopBody");
        adresse.setUIID("TopBody");
        Label note = new Label("" + e.getNote());
        note.setUIID("TopNote");
        box.addComponent(nom);
        box.addComponent(adresse);

        Container boxAndPrice = new Container(new BorderLayout());
        boxAndPrice.addComponent(BorderLayout.CENTER, box);
        boxAndPrice.addComponent(BorderLayout.EAST, note);
        mb.addComponent(BorderLayout.SOUTH, boxAndPrice);

        mb.setLeadComponent(nom);

        nom.addActionListener((e1) -> {
            if (adresse.getParent() != null) {
                box.removeComponent(adresse);
                box.addComponent(date);
            } else {
                box.removeComponent(date);
                box.addComponent(adresse);
            }
            mb.getParent().animateLayout(300);
        });
        return mb;
    }

}
