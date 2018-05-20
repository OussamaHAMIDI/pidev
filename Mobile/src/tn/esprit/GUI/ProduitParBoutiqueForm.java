/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.spinner.TimeSpinner;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.objects.NativeArray;
import tn.esprit.Services.BoutiqueService;
import tn.esprit.Services.EvaluationService;
import tn.esprit.Services.ProduitService;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Produit;

/**
 *
 * @author Firas
 */
public class ProduitParBoutiqueForm extends Form {
    static Resources res;

    public ProduitParBoutiqueForm(int idb) {
        super("Produits", new BorderLayout());
        this.res = Main.stheme;

     
        List<Produit> lb = ProduitService.getListbyBoutique(idb);
//        List<Evaluation> le = es.getEvaluations();
//        System.out.println(le);

        Container produits = new Container(BoxLayout.y());
        produits.setUIID("List");
        produits.setScrollableY(true);
        if (lb != null) {
            for (Produit b : lb) {
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2, this.getHeight() / 5, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, b.getPath(), "http://localhost/pidev/WEB/web/uploads/images/" + b.getPath(),
                        URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
                MultiButton mb = new MultiButton(b.getLibelle());
                mb.setUIID("ListItem");
                mb.setTextLine2(b.getReference());
                mb.setTextLine3(b.getDescription());
                mb.setTextLine4("Prix :" + String.valueOf(b.getPrix()));
                //mb.setIcon(img);
                mb.add(LEFT,img);
                mb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                       
                        ProduitDetailsForm.produitS = b;
                        Form bdf = new ProduitDetailsForm();
                        bdf.show();
                    }
                });
                produits.add(FlowLayout.encloseCenter(mb));
            }
            this.add(CENTER, produits);
        } else {
            //TO DO
        }

        
        
        
        this.addCommand(new Command("Retour") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.showBack();
            }
        });

    }

}
