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
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;

public class BoutiqueDetailsForm extends Form {

    final Resources res;
    static Boutique boutiqueS;
    private Boutique boutique;

    public BoutiqueDetailsForm() {
        super("", new BorderLayout());
        this.res = Main.stheme;
        this.boutique = boutiqueS;
        System.out.println(boutique);

        
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("BoutiqueNorth");

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, boutique.getPhoto(), "http://localhost/pidev/WEB/web/uploads/images/" + boutique.getPhoto(),
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
        center.setUIID("BoutiqueCenter");

        Label name = new Label(boutique.getNom());
        name.setUIID("BoutiqueInfo");
        center.addComponent(name);

        Label adresse = new Label(boutique.getAdresse());
        adresse.setUIID("BoutiqueInfo");
        center.addComponent(adresse);

        Label date = new Label(boutique.getDateCreation());
        date.setUIID("BoutiqueInfo");
        center.addComponent(date);
        
         //TO DO Yetbadel statique ki yahdher el user
        User user = new User();
        user.setId("40");
        
        Label feedback = new Label("Feedback");
        feedback.setUIID("Label");
        center.addComponent(feedback);
        
        Slider rating = createStarRankSlider();
        center.add(FlowLayout.encloseCenter(rating));
        rating.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                EvaluationService es = new EvaluationService();
                Evaluation ev = new Evaluation(user,boutique,rating.getProgress());
                es.addEvaluationBoutique(ev);
                System.out.println("rate =====> " + rating.getProgress());
            }
        });

        
        TextField reclamation = new TextField("Donnez votre reclamation");
        reclamation.setUIID("SignUpField");
        center.addComponent(reclamation);
        Button btnReclamation = new Button("Reclamer");
        center.addComponent(btnReclamation);
        btnReclamation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(!reclamation.getText().equals("")){
                    ReclamationService rs = new ReclamationService();
                    Reclamation rec = new Reclamation(user, boutique, reclamation.getText());
                    rs.addReclamation(rec);
                    reclamation.setText("");
                }
            }
        });
        
        this.addComponent(BorderLayout.CENTER, center);

        Button map = new Button("Voir sur Google Maps", res.getImage("right-arrow.png"));
        map.setGap(map.getStyle().getFont().getHeight());
        map.setTextPosition(Component.LEFT);
        map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("nhellou el map");
                MapForm mf = new MapForm();
                mf.show();
            }
        });
        this.addComponent(BorderLayout.SOUTH, map);
    }

    public Slider createStarRankSlider() {

        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.animate();
        starRank.setGap(5);
        starRank.setScrollVisible(false);
        starRank.setSmoothScrolling(true);
        starRank.setMaxValue(5);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(3, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

}
