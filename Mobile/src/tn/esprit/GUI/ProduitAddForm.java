package tn.esprit.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.ComboBox;
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
import com.twilio.*;
import com.twilio.type.PhoneNumber;
//import com.twilio.rest.lookups.v1.PhoneNumber;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import tn.esprit.Services.BoutiqueService;
import static tn.esprit.GUI.TombolaAddEditShowForm.res;
import tn.esprit.Services.ProduitService;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Produit;

public class ProduitAddForm extends Form {

    static Resources res;
    String path = null;
    TextField ref = new TextField("", "Reference");
    TextField nom = new TextField("", "Libellé");
    TextArea description = new TextArea(4,2);
    TextField prix = new TextField("", "prix");
    TextField taille = new TextField("", "taille");
    TextField couleur = new TextField("", "couleur");
    TextField texture = new TextField("", "texture");
    TextField poids = new TextField("", "poids"); 
    TextField quantite = new TextField("", "quantite");
        // TextField datecreation = new TextField();
    TextArea adresse = new TextArea(4, 2);

    MultiButton photo = new MultiButton("");

    public ProduitAddForm(String idboutique) {
        super("Ajouter un produit", new BorderLayout());
        this.res = Main.stheme;

        this.setScrollableY(true);
        this.setLayout(new BorderLayout());

        
        
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        content.add(ref);
        content.add(nom);
        content.add(description);
        content.add(prix);
        content.add(taille);
        content.add(couleur);
        content.add(texture);
        content.add(poids);
        content.add(quantite);
        content.add(adresse);
        
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

       
        content.add(l3).add(photo);

        submit.addActionListener(e -> {
            if (path == null) {
                Dialog.show("Erreur", "Veuillez bien choisir une photo !", "  OK  ", null);
            } else {
//                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//                String d = s.format(new Date());
                Produit p = new Produit();
                p.setBoutique((int)Float.parseFloat(idboutique));
                p.setCouleur(couleur.getText());
                p.setReference(ref.getText());
                p.setLibelle(nom.getText());
                p.setDescription(description.getText());
                p.setPoids((int) Float.parseFloat(poids.getText()));
                p.setPrix((int) Float.parseFloat(prix.getText()));
                p.setTexture(texture.getText());
                p.setTaille((taille.getText()));
                p.setPath(path);
                ProduitService ps = new ProduitService();
                ps.ajoutProduit(p);
                //System.out.println(boutiqueAjoutee);
                //bs.ajouterBoutiquePhoto(boutiqueAjoutee.getId(), path);
            
            ////// SMS
    
            Twilio.init("ACf487e3bfe64ff0198a7661fa79e7766c", "28c397afe069c913ad540ea42ccb179a");
                        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber("+21654476969"),
                        new PhoneNumber("+15713417284"),"your Product :"+p.getLibelle()+" has been added succesfully").create();
           
            }
        });

        this.add(CENTER, content);
        this.add(SOUTH, submit);

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form pf = new ProduitForm();
                pf.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.addCommand(back);

    }
}
