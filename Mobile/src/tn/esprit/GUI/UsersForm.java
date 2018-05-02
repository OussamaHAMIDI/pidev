package tn.esprit.GUI;

import com.codename1.components.ImageViewer;
import tn.esprit.entities.Tombola;
import com.codename1.components.MultiButton;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
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
import com.codename1.ui.util.Resources;

import java.util.List;

import tn.esprit.app.Main;
import tn.esprit.Services.TombolaService;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.User;

/**
 *
 * @author Hamdi Megdiche
 */
public class UsersForm extends Form {

    static Resources res;
    public static TombolaAddEditShowForm taesf;

    public UsersForm(List<User> users) {
        super("Participants", new BorderLayout());
        this.res = Main.stheme;

        Container participants = new Container(BoxLayout.y());
        participants.setUIID("List");
        participants.setScrollableY(true);

        if (users != null && users.size() > 0) {
            for (User u : users) {

                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 3, this.getHeight() / 4, 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, u.getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + u.getPhoto(),
                        URLImage.RESIZE_SCALE_TO_FILL);

                ImageViewer iv = new ImageViewer(img);

                Container image = new Container();

                iv.setUIID("PhotoItem");
                image.add(iv);

                MultiButton mb = new MultiButton(u.getPrenom() + " " + u.getNom());
                mb.setUIID("ListItem");

                Label title = new Label(u.getPrenom() + " " + u.getNom());
                title.setUIID("TitleItem");
                Container north = new Container(new FlowLayout(Component.CENTER));
                north.addComponent(title);

                mb.addComponent(BorderLayout.NORTH, north);

                Container center = new Container(new BorderLayout());
                center.add(BorderLayout.WEST, image);

                Container row = new Container(new GridLayout(3, 1));
                row.getStyle().setPaddingTop(15);

                Label l5 = new Label("Adresse :");
                l5.setUIID("RestItemInfo");
                row.add(l5);

                Label l1 = new Label(u.getAdresse());
                l1.setUIID("RestItem");
                row.add(l1);

                Label l6 = new Label("Email :");
                l6.setUIID("RestItemInfo");
                row.add(l6);

                Label l2 = new Label(u.getEmail());
                l2.setUIID("RestItem");
                row.add(l2);

                Label l7 = new Label("Télephone :");
                l7.setUIID("RestItemInfo");
                row.add(l7);

                Label l3 = new Label(u.getTel());
                l3.setUIID("RestItem");
                row.add(l3);
                center.add(BorderLayout.CENTER, row);

                mb.addComponent(BorderLayout.CENTER, center);

//                Container south = new Container(new BorderLayout());
//                Label l4 = new Label(t.getEtat());
//                l4.setUIID("EtatTombola" + t.getEtat());
//                south.add(BorderLayout.CENTER, l4);
//                mb.addComponent(BorderLayout.SOUTH, south);
                mb.addActionListener(e -> {
//                    new TombolaAddOrEditForm(t).show();
                });

                participants.add(mb);

            }
        }

        this.add(CENTER, participants);

        Command c1 = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                taesf.showBack();
            }
        };
        FontImage.setMaterialIcon(c1, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command c = new Command("Menu") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(c, ' ', "TitleCommand", 5);
        this.addCommand(c1);
        this.addCommand(c);// show singhle tombola back

    }

}
