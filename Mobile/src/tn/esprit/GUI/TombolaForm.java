package tn.esprit.GUI;

import tn.esprit.entities.Tombola;
import com.codename1.components.MultiButton;
import com.codename1.ui.Calendar;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.DateTimeSpinner;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.Date;

import java.util.List;

import tn.esprit.app.Main;
import tn.esprit.Services.TombolaService;

/**
 *
 * @author Hamdi Megdiche
 */
public class TombolaForm extends Form {

    static Resources res;

    public TombolaForm() {
        super("Tombolas", new BorderLayout());
        this.res = Main.stheme;

        Container tombolas = new Container(BoxLayout.y());
        tombolas.setUIID("List");
        tombolas.setScrollableY(true);

        TombolaService ts = new TombolaService();
        List<Tombola> tombos = ts.getTombolas();

        if (tombos != null) {
            for (Tombola t : tombos) {

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 3, this.getHeight() / 5, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, t.getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + t.getPhoto(),
                        URLImage.RESIZE_SCALE_TO_FILL);
                
                Container image = new Container();
                image.setUIID("PhotoItem");
                image.add(img);

                MultiButton mb = new MultiButton(t.getTitre());
                mb.setUIID("ListItem");
//                mb.setNameLine1(t.getTitre());
////                mb.setTextLine1(t.getDescription());
//                mb.setTextLine2("Date Ajout : \n" + t.getDateAjout());
//                mb.setTextLine3("Date Modification \n: " + t.getDateModif());
//                mb.setTextLine4("Date Tirage : \n" + t.getDateTirage());

                Label title = new Label(t.getTitre());
                title.setUIID("TitleItem");
                Container north = new Container(new FlowLayout(Component.CENTER));
                north.addComponent(title);

                mb.addComponent(BorderLayout.NORTH, north);

                Container center = new Container(new BorderLayout());
                center.add(BorderLayout.WEST, image);
                
                Container row = new Container(new GridLayout(7, 1));
                row.add(new Label("Date Ajout :"));
                Label l1 = new Label(t.getDateAjout());
                l1.setUIID("RestItem");
                row.add(l1);
                row.add(new Label("Date Tirage :"));
                Label l2 = new Label(t.getDateTirage());
                l2.setUIID("RestItem");
                row.add(l2);
                row.add(new Label("Date Modification :"));
                Label l3 = new Label(t.getDateModif());
                l3.setUIID("RestItem");
                row.add(l3);
                row.add(new Label("Etat :"));
                Label l4 = new Label(t.getEtat());
                l4.setUIID("RestItem");
                row.add(l4);
                center.add(BorderLayout.CENTER, row);
                
                 mb.addComponent(BorderLayout.CENTER, center);

                Picker dateTimePicker = new Picker();
                dateTimePicker.setType(Display.PICKER_TYPE_DATE);
                dateTimePicker.setDate(new Date());

                //tombolas.add(dateTimePicker);
                tombolas.add(mb);//, new Label(t.getEtat())));//.add(dateTimePicker);

            }
        }

        this.add(CENTER, tombolas);

        this.setBackCommand(new Command("", res.getImage("back-arrow.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {

            }
        });

        this.addCommand(new Command("Retour") {

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
