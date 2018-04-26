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
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
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
import tn.esprit.app.Main;


public class BoutiqueDetailsForm extends Form {

    final Resources res;

    public BoutiqueDetailsForm() {
        super("Boutique", new BorderLayout());
        this.res = Main.stheme;

        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("BoutiqueNorth");

        Button photoButton = new Button(res.getImage("profile-mask-white.png"));
        photoButton.setUIID("PhotoButton");
        north.addComponent(photoButton);

        this.addComponent(BorderLayout.NORTH, north);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");

        Container row1 = new Container(new GridLayout(1,2));
        TextField firstName = new TextField();
        firstName.setUIID("SignUpField");
        firstName.setHint("First Name");
        firstName.getHintLabel().setUIID("SignupFieldHint");
        TextField lastName = new TextField();
        lastName.setUIID("SignUpField");
        lastName.setHint("Last Name");
        lastName.getHintLabel().setUIID("SignupFieldHint");
        row1.addComponent(firstName);
        row1.addComponent(lastName);
        center.addComponent(row1);
        center.setScrollableY(true);

        TextField email = new TextField();
        email.setUIID("SignUpField");
        center.addComponent(email);
        email.setHint("Email Address");
        email.getHintLabel().setUIID("SignupFieldHint");

        TextField password = new TextField();
        password.setUIID("SignUpField");
        password.setConstraint(TextField.PASSWORD);
        password.setHint("Choose Password");
        password.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(password);

        Container row4 = new Container(new BorderLayout());
        Label code = new Label("+1");
        code.setUIID("SignUpLabel");
        row4.addComponent(BorderLayout.WEST, code);

        TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Phone Number");
        phoneNumber.getHintLabel().setUIID("SignupFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);

        center.addComponent(row4);

        this.addComponent(BorderLayout.CENTER, center);
        
        Button map = new Button("Voir sur la Map", res.getImage("right-arrow.png"));
        map.setGap(map.getStyle().getFont().getHeight());
        map.setUIID("MapButton");
        map.setTextPosition(Component.LEFT);
        map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("nhellou el map");
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
                derive(Display.getInstance().convertToPixels(2, true), Font.STYLE_PLAIN);
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
