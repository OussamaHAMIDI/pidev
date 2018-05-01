package tn.esprit.GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.CN1Constants;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Date;
import tn.esprit.app.Main;

public class BoutiqueAddForm extends Form {

    static Resources res;



    public BoutiqueAddForm() {
        super("Ajout Boutique");
        this.setUIID("SignUpForm");

        res = Main.stheme;
        
        this.setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("SignUpNorth");

//        String path;
//        MultiButton photo = new MultiButton("");
//        ImageViewer iv = new ImageViewer(res.getImage("tombola.png"));
//
//        photo.setUIID("PreviewPhoto");
//        iv.setUIID("PreviewPhoto");
//
//        photo.addComponent(BorderLayout.CENTER, iv);
//        Button submit = new Button("Ajouter");
//
//        photo.addActionListener(e -> {
//            path = Capture.capturePhoto();
//            if (b != null) {
//                if (path != null) {
//                    b.setPhoto(path);
//                }
//            }
//            try {
//                Image img = Image.createImage(path);
//                iv.setImage(img);
//                iv.refreshTheme();
//                photo.refreshTheme();
//                iv.setUIID("PreviewPhoto");
//                photo.setUIID("PreviewPhoto");
//                this.revalidate();
//            } catch (IOException ex) {
//            }
//
//        });
        Button photoButton = new Button(res.getImage("profile-mask-white.png"));
        photoButton.setUIID("PhotoButton");
        north.addComponent(photoButton);

        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");

       // Container row1 = new Container(new GridLayout(1, 2));

        TextField nom = new TextField();
        nom.setUIID("SignUpField");
//        nom.setHint("Nom de la boutique");
        nom.getHintLabel().setUIID("SignupFieldHint");
        TextField adresse = new TextField();
        adresse.setUIID("SignUpField");
        adresse.setHint("Adresse de la boutique");
        adresse.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(nom);
        center.addComponent(adresse);
        center.setScrollableY(true);
        this.addComponent(BorderLayout.CENTER, center);

        Button getStarted = new Button("Creer boutique", res.getImage("right_arrow.png"));
        getStarted.setGap(getStarted.getStyle().getFont().getHeight());
        getStarted.setUIID("SignUpButton");
        getStarted.setTextPosition(Component.LEFT);
//        getStarted.addActionListener();

        this.addComponent(BorderLayout.SOUTH, getStarted);
        
        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form bf = new BoutiqueForm();
                bf.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.addCommand(back);

    }
}
