package tn.esprit.app;

import tn.esprit.GUI.BoutiqueForm;
import tn.esprit.GUI.HomeForm;
import com.codename1.components.MultiButton;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.GUI.BoutiqueForm;
import tn.esprit.GUI.TombolaForm;


public class Main {

    public static Form shome;
    public static Resources stheme;
    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initNamedTheme("/theme", "Theme");
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(false);
        this.stheme = theme;

    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        
        //Styling buttons ac java :D
        Button.setCapsTextDefault(true);
        Button.setButtonRippleEffectDefault(true);
        Button btn = new Button();
        btn.setText("boutiques");
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
        topBar.add(BorderLayout.SOUTH, new Label("Souk Lemdina", "SidemenuTagline"));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);

        tb.addMaterialCommandToSideMenu("Boutiques", FontImage.MATERIAL_WEB, e -> {

            BoutiqueForm bf = new BoutiqueForm();
            bf.show();

        });
        
        tb.addMaterialCommandToSideMenu("Tombolas", FontImage.MATERIAL_WEB, e -> {

            TombolaForm tf = new TombolaForm();
            tf.show();

        });
        
        tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {
        });
        tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {
        });

//        Iterable<Command> commands = tb.getSideMenuCommands();
//        MenuBar mb = tb.getMenuBar();
//        mb.setUIID("MenuBar");
        
       
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