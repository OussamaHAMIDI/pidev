/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

/**
 *
 * @author Imen BenAbderrahmen
 */
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import tn.esprit.Services.EvaluationService;
import tn.esprit.Services.ReclamationService;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Evaluation;
import tn.esprit.entities.Produit;
import tn.esprit.entities.ProduitPanier;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;

public class ProduitDetailsForm extends Form {

    final Resources res;
    static Produit produitS;
    private Produit produit;

    public ProduitDetailsForm() {
        super("Details produit", new BorderLayout());
        this.res = Main.stheme;
        this.produit = produitS;
        System.out.println(produit);

        
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("BoutiqueNorth");

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, produit.getPath(), "http://localhost/pidev/WEB/web/uploads/" + produit.getPath(),
                URLImage.RESIZE_SCALE_TO_FILL);
        north.add(img);

//        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getWidth()), true);
//        Image img = URLImage.createToStorage(placeholder, boutique.getPhoto(),
//                "http://localhost/pidev/WEB/web/uploads/images" + boutique.getPhoto());
//        Style stitle = this.getUnselectedStyle();
//        stitle.setBgImage(img);
//        stitle.setBackgroundType(Style.BACKGROUND_IMAGE_ALIGNED_TOP);
//
//        Button photoButton = new Button(res.getImage("profile-mask-white.png"));
//        photoButton.setUIID("PhotoButton");

        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("ProduitCentre");

        Label reference = new Label("Reference: "+produit.getReference());
        reference.setUIID("ProduitInfo");
        center.addComponent(reference);

        Label libelle = new Label("Libelle: "+produit.getLibelle());
        libelle.setUIID("ProduitInfo");
        center.addComponent(libelle);

        Label desc = new Label("Description: "+produit.getDescription());
        desc.setUIID("ProduitInfo");
        center.addComponent(desc);
        
         Label prix = new Label("Prix: "+String.valueOf(produit.getPrix()));
        prix.setUIID("ProduitInfo");
        center.addComponent(prix);

        Button btnModifier = new Button("Ajouter au panier");
        center.addComponent(btnModifier);
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ProduitPanier p = new ProduitPanier(produit);
                Main.monpanier.ajouter(p);
            }
        });
        
        this.addComponent(BorderLayout.CENTER, center);
          this.addCommand(new Command("Retour") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.showBack();
            }
        });

    }


}
