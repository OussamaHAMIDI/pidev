package tn.esprit.GUI;

import com.codename1.components.ImageViewer;

import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
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
import java.util.Date;
import java.util.List;

import tn.esprit.entities.Tombola;
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
        List<Tombola> tombos = null;

        if (Main.userConnected != null && Main.userConnected.getType() == Enumerations.TypeUser.Artisan) {
            tombos = ts.getTombolas(Main.userConnected.getId());
        } else {
            tombos = ts.getTombolas("none");
        }

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

                Label l7, l3;

                if (Main.userConnected != null && Main.userConnected.getType() == Enumerations.TypeUser.Artisan) {
                    l7 = new Label("Date Modification :");
                    l3 = new Label(t.getDateModif());
                    l7.setUIID("RestItemInfo");
                    l3.setUIID("RestItem");
                    row.add(l7);
                    row.add(l3);
                } else {
                    if (count(t.getDateTirage()) != null) {
                        l7 = new Label("Reste :");
                        l3 = new Label(count(t.getDateTirage()));
                        l7.setUIID("RestItemInfo");
                        l3.setUIID("RestItem");
                        row.add(l7);
                        row.add(l3);
                    }
                }

                center.add(BorderLayout.CENTER, row);

                mb.addComponent(BorderLayout.CENTER, center);

                Container south = new Container(new BorderLayout());
                Label l4 = new Label(t.getEtat());
                l4.setUIID("EtatTombola" + t.getEtat());
                south.add(BorderLayout.CENTER, l4);

                mb.addComponent(BorderLayout.SOUTH, south);
                mb.addActionListener(e -> {
                    new TombolaAddEditShowForm(t).show();
                });

                tombolas.add(mb);

            }
        }

        this.add(CENTER, tombolas);
        TombolaAddEditShowForm.tbf = this;

        Command arrowBack = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(arrowBack, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command ajout = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new TombolaAddEditShowForm(null).show();//ajout
            }
        };
        FontImage.setMaterialIcon(ajout, FontImage.MATERIAL_ADD, "TitleCommand", 5);

        Command none = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
            }
        };
        FontImage.setMaterialIcon(none, ' ', "TitleCommand", 5);
        
        Command refresh = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new TombolaForm().show();
            }
        };
        FontImage.setMaterialIcon(refresh, FontImage.MATERIAL_REFRESH, "TitleCommand", 5);

        if (Main.userConnected != null) {
            if (Main.userConnected.getType() != Enumerations.TypeUser.Artisan) {
                this.addCommand(arrowBack);// <-
                this.addCommand(refresh);
            } else {
                this.addCommand(arrowBack);// <-
                this.addCommand(ajout);// plus
            }
        } else {
            this.addCommand(arrowBack);// <-
            this.addCommand(refresh);
        }
    }

    private String count(String dateTirage) {

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date tirage = new Date();
        try {
            tirage = s.parse(dateTirage);
        } catch (ParseException ex) {
            Log.e(ex);
        }

        long sec = (tirage.getTime() - new Date().getTime()) / 1000;
        int j = (int) sec / 86400;
        int h = (int) (sec % 86400) / 3600;
        int mm = (int) ((sec % 86400) % 3600) / 60;
        int se = (int) ((sec % 86400) % 3600) % 60;
        String count = "";
        if (j > 0) {
            if (j == 1) {
                count += j + " jour ";
            } else {
                count += j + " jours ";
            }
        }
        if (h > 0) {
            if (h == 1) {
                count += h + " heure ";
            } else {
                count += h + " heures ";
            }

        }
        if (mm > 0) {
            if (mm == 1) {
                count += mm + " minute ";
            } else {
                count += mm + " minutes ";
            }
        }
        if (se > 0) {
            if (se == 1) {
                count += se + " seconde";
            } else {
                count += se + " secondes";
            }
        }
        if (tirage.getTime() > new Date().getTime()) {
            return count;
        } else {
            return null;
        }
    }
}
