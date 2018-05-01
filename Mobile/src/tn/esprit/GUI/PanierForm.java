/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import com.codename1.components.Ads;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.notifications.LocalNotification;
import com.codename1.payment.Purchase;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
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
import tn.esprit.Services.PanierService;
import tn.esprit.Services.ProduitService;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Panier;
import tn.esprit.entities.Produit;
import tn.esprit.entities.ProduitPanier;

/**
 *
 * @author Firas
 */
public class PanierForm extends Form {
    static Resources res;

    public PanierForm() {
        super("Panier", new BorderLayout());
        this.res = Main.stheme;

     
        List<ProduitPanier> lb = Main.monpanier.getContenu();
//        List<Evaluation> le = es.getEvaluations();
//        System.out.println(le);

        Container produits = new Container(BoxLayout.y());
        produits.setUIID("List");
        produits.setScrollableY(true);
        if (lb != null) {
            for (ProduitPanier b : lb) {
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2, this.getHeight() / 5, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, b.getPath(), "http://localhost/pidev/WEB/web/uploads/images/" + b.getPath(),
                        URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
                MultiButton mb = new MultiButton(b.getLibelle());
                mb.setUIID("ListItem");
                mb.setTextLine1(b.getReference());
                mb.setTextLine2(b.getDescription());
                mb.setTextLine3("Prix :" + String.valueOf(b.getPrix()));
                  mb.setTextLine4("Quantité :" + String.valueOf(b.getQuantiteVendue()));
                Button plus = new Button("+");
                Button moins = new Button("-");
                Button supprimer = new Button("Supprimer");
                   moins.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                      if(b.getQuantiteVendue()>1)
                      {
                           b.setQuantiteVendue(b.getQuantiteVendue()-1);
                    mb.setTextLine4("Quantité :" + String.valueOf(b.getQuantiteVendue()));
                    Main.monpanier.getContenu().get(Main.monpanier.getContenu().indexOf(b)).setQuantiteVendue(b.getQuantiteVendue());
                    Main.monpanier.recalculer();
                      }
                    }
                });
                    plus.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                           b.setQuantiteVendue(b.getQuantiteVendue()+1);
                    mb.setTextLine4("Quantité :" + String.valueOf(b.getQuantiteVendue()));
                    Main.monpanier.getContenu().get(Main.monpanier.getContenu().indexOf(b)).setQuantiteVendue(b.getQuantiteVendue());
                    Main.monpanier.recalculer();
                    }
                });
                
                mb.add(LEFT,img);
                Container btns = new Container();
                btns.add(plus);
                btns.add(moins);
                btns.add(supprimer);
                Container cn = new Container();
                cn.add(mb);
                 supprimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                      Main.monpanier.getContenu().remove(b);
                      Main.monpanier.recalculer();
                      PanierForm pf = new PanierForm();
            pf.show();
                    }
                });
                cn.add(btns);
//             mb.add(BOTTOM,btns);
             
                produits.add(FlowLayout.encloseCenter(cn));
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
          this.addCommand(new Command("Commander") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if(Main.monpanier.getContenu().size()>0)
                {
                       LocalNotification ln = new LocalNotification();
            ln.setId("LnMessage");
            ln.setAlertTitle("Commande");
            ln.setAlertBody("Votre commande est validé! Merci!");
            try
            {
//                            Purchase.getInAppPurchase().purchase("Commande souk lemdina : " + String.valueOf(Main.monpanier.getTotalTTC())+ " TND");
            }
            catch(Exception e)
            {
                
            }
            Display.getInstance().scheduleLocalNotification(ln,   System.currentTimeMillis() + 10 * 1000, LocalNotification.REPEAT_NONE);
            PanierService ps = new PanierService();

            ps.addPanier(Main.monpanier);
            for(ProduitPanier p : Main.monpanier.getContenu())
            {
                ps.addProduitPanier(p);
            }
            Main.monpanier=new Panier();
             Main.shome.showBack();
                }
          
            }
        });

    }

}
