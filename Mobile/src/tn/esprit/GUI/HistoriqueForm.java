/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.List;
import tn.esprit.Services.BoutiqueService;
import tn.esprit.Services.EvaluationService;
import tn.esprit.Services.ReclamationService;
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Evaluation;
import tn.esprit.entities.Reclamation;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class HistoriqueForm extends Form {

    static Resources res;

    public HistoriqueForm() {
        super("Historiques", new BorderLayout());
        this.res = Main.stheme;

        ReclamationService rs = new ReclamationService();
        List<Reclamation> lr = rs.getReclamationsUser("40");

        Container reclamations = new Container(BoxLayout.y());
        reclamations.setUIID("List");
        reclamations.setScrollableY(true);
        if (lr != null) {
            for (Reclamation r : lr) {
                
                MultiButton mb = new MultiButton(r.getDateCreation());
                Button delete = new Button("supprimer");
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println(r.getId());
                        rs.deleteReclamation(r.getId());
                        System.out.println(r.getId() + "supprimééééééééééé");
                        HistoriqueForm hf = new HistoriqueForm();
                        hf.show();
                    }
                });
                
                mb.setUIID("ListItem");
                mb.setTextLine3(r.getDescription());
                if(r.getBoutique()!=null){
                    mb.setTextLine2(r.getBoutique().getNom());
                }
                reclamations.add(FlowLayout.encloseCenter(mb, delete));
            }
            this.add(CENTER, reclamations);
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
