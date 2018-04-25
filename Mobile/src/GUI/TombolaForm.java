package GUI;

import Entities.Tombola;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.ComponentSelector.$;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.List;
import java.util.Map;
import Main.Main;
import Services.TombolaService;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class TombolaForm extends Form {

    static Resources res;

    public TombolaForm() {
        super("Tombolas");
        this.res = Main.stheme;

        Container tombolas = new Container(BoxLayout.y());
        tombolas.setUIID("Tombolas");
        tombolas.setScrollableY(true);

        TombolaService ts = new TombolaService();
        List<Tombola> tombos = ts.getTombolas();

        if (tombos != null) {
            for (Tombola t : tombos) {

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/3, this.getHeight()/ 5, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder,t.getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + t.getPhoto(), 
                        URLImage.RESIZE_SCALE_TO_FILL);

                MultiButton mb = new MultiButton("Tombola " + t.getId());
                mb.setUIID("Item");
                mb.setNameLine1(t.getTitre());
                mb.setTextLine1(t.getDescription());
                mb.setTextLine1(t.getDateAjout());
                mb.setTextLine1(t.getDateModif());
                mb.setTextLine1(t.getDateTirage());
                mb.setIcon(img);
           
//                tombolas.add(mb);
            tombolas.add(FlowLayout.encloseCenter(mb));

            }
        }
//        
        this.add(CENTER, tombolas);

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

//        this.add(new Label("This is Tombola"));
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

}
