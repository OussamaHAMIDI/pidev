/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
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
import tn.esprit.app.Main;
import tn.esprit.entities.Boutique;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Evaluation;
import tn.esprit.entities.User;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class BoutiqueForm extends Form {

    static Resources res;

    public BoutiqueForm() {
        super("Boutiques",new BorderLayout());
        this.res = Main.stheme;

        BoutiqueService bs = new BoutiqueService();
        EvaluationService es = new EvaluationService();
        User user = new User();
        //user.setId("42");
        List<Boutique> lb = bs.getBoutiques();

//        List<Evaluation> le = es.getEvaluations();
//        System.out.println(le);
        Container boutiques = new Container(BoxLayout.y());
        boutiques.setUIID("List");
        boutiques.setScrollableY(true);
        if (lb != null) {
            for (Boutique b : lb) {

               
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, b.getPhoto(), "http://localhost/pidev/WEB/web/uploads/images/" + b.getPhoto(),
                        URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
                MultiButton mb = new MultiButton(b.getNom());
                mb.setUIID("ListItem");
                mb.setTextLine2(b.getAdresse());
                mb.setTextLine3(b.getDateCreation());
                //mb.setIcon(img);
                mb.add(LEFT, img);
                //Slider note = createStarRankSlider();
                mb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        System.out.println("clikina aal mb");
                        BoutiqueDetailsForm.boutiqueS = b;
                        Form bdf = new BoutiqueDetailsForm();
                        bdf.show();
                    }
                });
                boutiques.add(FlowLayout.encloseCenter(mb));
            }
            this.add(CENTER, boutiques);
        } else {
            //TO DO
        }

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command add = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form baf = new BoutiqueAddForm();
                baf.show();//ajout
            }
        };
        FontImage.setMaterialIcon(add, FontImage.MATERIAL_ADD, "TitleCommand", 5);
        if (Main.userConnected != null && Main.userConnected.getType() != Enumerations.TypeUser.Artisan) {
            this.addCommand(back);
        } else {
            this.addCommand(back);
            this.addCommand(add);
        }

    }

//    public Slider createStarRankSlider() {
//
//        Slider starRank = new Slider();
//        starRank.setEditable(true);
//        starRank.animate();
//        starRank.setGap(5);
//        starRank.setScrollVisible(false);
//        starRank.setSmoothScrolling(true);
//        starRank.setMaxValue(5);
//        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
//                derive(Display.getInstance().convertToPixels(2, true), Font.STYLE_PLAIN);
//        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
//        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
//        s.setOpacity(100);
//        s.setFgColor(0);
//        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
//        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
//        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
//        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
//        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
//        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
//        return starRank;
//    }
//
//    private void initStarRankStyle(Style s, Image star) {
//        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
//        s.setBorder(Border.createEmpty());
//        s.setBgImage(star);
//        s.setBgTransparency(0);
//    }

}
