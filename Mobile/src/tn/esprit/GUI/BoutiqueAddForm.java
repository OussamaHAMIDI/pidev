package tn.esprit.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Date;
import tn.esprit.Services.BoutiqueService;
import static tn.esprit.GUI.TombolaAddEditShowForm.res;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;

public class BoutiqueAddForm extends Form {

    static Resources res;
    String path = null;
    TextField nom = new TextField();
    TextArea adresse = new TextArea(4, 2);

    MultiButton photo = new MultiButton("");

    public BoutiqueAddForm() {
        super("Ajout Boutique", new BorderLayout());
        this.res = Main.stheme;

        this.setScrollableY(true);
        this.setLayout(new BorderLayout());

        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label l1 = new Label("Nom :");
        l1.setUIID("RestItemInfo");
        Label l2 = new Label("Adresse :");
        l2.setUIID("RestItemInfo");
        Label l3 = new Label("Choisir une photo :");
        l3.setUIID("RestItemInfo");

        nom.setUIID("Titre");
        adresse.setUIID("Titre");

        ImageViewer iv = new ImageViewer(res.getImage("tombola.png"));

        photo.setUIID("PreviewPhoto");
        iv.setUIID("PreviewPhoto");

        photo.addComponent(BorderLayout.CENTER, iv);

        photo.addActionListener(e -> {
            path = Capture.capturePhoto();
            try {
                Image img = Image.createImage(path);
                iv.setImage(img);
                iv.refreshTheme();
                photo.refreshTheme();
                iv.setUIID("PreviewPhoto");
                photo.setUIID("PreviewPhoto");
                this.revalidate();
            } catch (IOException ex) {
                Log.e(ex);
            }

        });
        content.setScrollableY(true);

        Button submit = new Button("Ajouter");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);

        content.add(l1).add(nom);
        content.add(l2).add(adresse);
        content.add(l3).add(photo);

        submit.addActionListener(e -> {
            if (path == null) {
                Dialog.show("Erreur", "Veuillez bien choisir une photo !", "  OK  ", null);
            } else {
//                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//                String d = s.format(new Date());
                Boutique b = new Boutique(adresse.getText(), path, nom.getText(), Main.userConnected);
                BoutiqueService bs = new BoutiqueService();
                Boutique boutiqueAjoutee = bs.add(b);
                //System.out.println(boutiqueAjoutee);
                //bs.ajouterBoutiquePhoto(boutiqueAjoutee.getId(), path);
            }
        });

        this.add(CENTER, content);
        this.add(SOUTH, submit);

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form bf = new BoutiqueForm();
                bf.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.addCommand(back);
        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(nom,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracteres"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Veuillez saisir que des caracteres")));

    }
}
