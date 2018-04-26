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
import static com.codename1.ui.ComponentSelector.$;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import tn.esprit.app.Main;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class BoutiqueForm extends Form {

    static Resources res;

    public BoutiqueForm() {
        super("Boutiques", new BorderLayout());
        this.res = Main.stheme;

                Container boutiques = new Container(BoxLayout.y());
        boutiques.setUIID("List");
        boutiques.setScrollableY(true);
        for (int i = 0; i < 5; i++) {
            MultiButton mb = new MultiButton("boutique numero " + i);
            mb.setUIID("ListItem");
            mb.setNameLine1(i + "hedhi name lbeki text");
            mb.setTextLine1(i + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            mb.setTextLine2(i + " bbbbbb");
            mb.setTextLine3(i + " ccccccc");
            mb.setTextLine4(i + " odddddddddd");
            mb.setIcon(res.getImage("camera.png"));
            Slider note = createStarRankSlider();
            mb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("clikina aal mb");
                    Form bdf = new BoutiqueDetailsForm();
                    bdf.show();
                }
            });
            boutiques.add(FlowLayout.encloseCenter(mb, note));
        }
        this.add(CENTER, boutiques);
        
        this.setBackCommand(new Command("", res.getImage("back-arrow.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });
        
        this.addCommand(new Command("Done") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                  Main.shome.showBack();
            }
        });

        

//        this.add(new Label("This is Boutique"));
//
//        Button slideUp = $(new Button("Boutton mezyen"))
//                .setIcon(FontImage.MATERIAL_EXPAND_LESS)
//                .addActionListener(e -> {
//                    $(e)
//                            .getParent()
//                            .find(">*")
//                            .slideUpAndWait(1000);
//                })
//                .asComponent(Button.class);
//        this.add(slideUp);
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
