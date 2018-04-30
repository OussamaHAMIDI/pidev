package tn.esprit.GUI;

import com.codename1.components.ImageViewer;
import tn.esprit.entities.Tombola;
import com.codename1.components.MultiButton;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.Date;

import java.util.List;

import tn.esprit.app.Main;
import tn.esprit.Services.TombolaService;
import tn.esprit.entities.Enumerations;

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

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 3, this.getHeight() / 4, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, t.getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + t.getPhoto(),
                        URLImage.RESIZE_SCALE_TO_FILL);

                ImageViewer iv = new ImageViewer(img);

                Container image = new Container();

                iv.setUIID("PhotoItem");
                image.add(iv);

                MultiButton mb = new MultiButton(t.getTitre());
                mb.setUIID("ListItem");

                Label title = new Label(t.getTitre());
                title.setUIID("TitleItem");
                Container north = new Container(new FlowLayout(Component.CENTER));
                north.addComponent(title);

                mb.addComponent(BorderLayout.NORTH, north);

                Container center = new Container(new BorderLayout());
                center.add(BorderLayout.WEST, image);

                Container row = new Container(new GridLayout(6, 1));
                row.getStyle().setPaddingTop(15);

                Label l5 = new Label("Date Ajout :");
                l5.setUIID("RestItemInfo");
                row.add(l5);

                Label l1 = new Label(t.getDateAjout());
                l1.setUIID("RestItem");
                row.add(l1);

                Label l6 = new Label("Date Tirage :");
                l6.setUIID("RestItemInfo");
                row.add(l6);

                Label l2 = new Label(t.getDateTirage());
                l2.setUIID("RestItem");
                row.add(l2);

                Label l7 = new Label("Date Modification :");
                l7.setUIID("RestItemInfo");
                row.add(l7);

                Label l3 = new Label(t.getDateModif());
                l3.setUIID("RestItem");
                row.add(l3);
                center.add(BorderLayout.CENTER, row);

                mb.addComponent(BorderLayout.CENTER, center);

                Container south = new Container(new BorderLayout());
                Label l4 = new Label(t.getEtat());
                l4.setUIID("EtatTombola" + t.getEtat());
                south.add(BorderLayout.CENTER, l4);

                mb.addComponent(BorderLayout.SOUTH, south);
                mb.addActionListener(e -> {
                    new TombolaAddOrEditForm(t).show();
                });

                tombolas.add(mb);

            }
        }

        this.add(CENTER, tombolas);
        TombolaAddOrEditForm.tbf = this;

        Command c1 = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeForm().show();
            }
        };
        FontImage.setMaterialIcon(c1, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command c = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new TombolaAddOrEditForm(null).show();//ajout
            }
        };
        FontImage.setMaterialIcon(c, FontImage.MATERIAL_ADD, "TitleCommand", 5);

        if (Main.userConnected != null && Main.userConnected.getType() != Enumerations.TypeUser.Artisan) {
            Command cc = new Command("Retour ") {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    new HomeForm().show();
                }
            };
            FontImage.setMaterialIcon(c, ' ', "TitleCommand", 5);

            this.addCommand(cc);
        } else {
            this.addCommand(c1);
            this.addCommand(c);// plus
        }

    }

}
