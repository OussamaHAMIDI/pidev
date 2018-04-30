package tn.esprit.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Date;
import tn.esprit.Services.TombolaService;
import tn.esprit.Services.UserService;

import tn.esprit.app.Main;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Tombola;
import tn.esprit.entities.User;

/**
 *
 * @author Hamdi Megdiche
 */
public class TombolaAddOrEditForm extends Form {

    static Resources res;
    public static TombolaForm tbf;
    String path = null;
    String dateTirage = "";
    User userConnected = null;

    TextField titre = new TextField();
    TextArea description = new TextArea(4, 2);
    Picker date = new Picker();
    Picker time = new Picker();
    MultiButton photo = new MultiButton("");

    private void showOKForm(String t, String count, String etat) {
        Form f = new Form("Ajoutée", BoxLayout.y());

        SpanLabel l = new SpanLabel("Tombola \"" + t + "\" est " + etat + " avec succés.");
        l.setHeight(10);
        l.setUIID("SpanLabel");
        SpanLabel l2 = new SpanLabel("Reste : " + count + " avant la cloturation.");
        l2.setUIID("SpanLabel2");
        f.add(l);
        f.add(l2);
        TombolaForm tf = new TombolaForm();

        f.addCommand(new Command("Tombolas") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                tf.showBack();
            }
        });
        f.show();
    }

    public TombolaAddOrEditForm(Tombola t) {

        super("Ajout Tombola", new BorderLayout());
        userConnected = Main.userConnected;
        this.res = Main.stheme;

        this.setScrollableY(true);
        this.setLayout(new BorderLayout());

        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label l1 = new Label("Choisir une photo :");
        l1.setUIID("RestItemInfo");
        Label l2 = new Label("Titre :");
        l2.setUIID("RestItemInfo");
        Label l3 = new Label("Description :");
        l3.setUIID("RestItemInfo");
        Label l4 = new Label("Date Tirage :");
        l4.setUIID("RestItemInfo");

        titre.setUIID("Titre");

        description.setUIID("Titre");

        Container row = new Container(new GridLayout(1, 2));

        date.setType(Display.PICKER_TYPE_DATE);
        date.setDate(new Date());
        date.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        date.setUIID("Date");

        time.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        time.setFormatter(new SimpleDateFormat("HH:mm:ss"));
        time.setUIID("Time");

        date.addActionListener(e -> {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            String d = s.format(date.getDate());
            date.setText(d);
            dateTirage = date.getText() + " " + time.getText();
            System.out.println(dateTirage);
        });

        time.addActionListener(e -> {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = s.format(time.getDate());
            String sec = s.format(new Date());
            time.setText(d.substring(d.indexOf(":") - 2, d.lastIndexOf(":")) + sec.substring(sec.lastIndexOf(":")));
            dateTirage = date.getText() + " " + time.getText();
            System.out.println(dateTirage);
        });

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = s.format(new Date());
        date.setText(d.substring(0, d.indexOf(":") - 3));
        time.setText(d.substring(d.indexOf(":") - 2));
        dateTirage = date.getText() + " " + time.getText();

        ImageViewer iv = new ImageViewer(res.getImage("tombola.png"));

        photo.setUIID("PreviewPhoto");
        iv.setUIID("PreviewPhoto");

        photo.addComponent(BorderLayout.CENTER, iv);
        Button submit = new Button("Ajouter");

        photo.addActionListener(e -> {
            path = Capture.capturePhoto();
            if (t != null) {
                if (path != null) {
                    t.setPhoto(path);
                }
            }
            try {
                Image img = Image.createImage(path);
                iv.setImage(img);
                iv.refreshTheme();
                photo.refreshTheme();
                iv.setUIID("PreviewPhoto");
                photo.setUIID("PreviewPhoto");
                this.revalidate();
            } catch (IOException ex) {
                Log.e(ex);
            }

        });
        content.setScrollableY(true);

        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            boolean chk = true;
            Date tirage = null;
            try {
                tirage = s.parse(dateTirage);
            } catch (ParseException ex) {
            }

            if (t != null && path == null) {
                path = t.getPhoto();
                chk = true;
            } else if (path == null) {
                Dialog.show("Erreur", "Veuillez bien choisir une photo !", "  OK  ", null);
                chk = false;

            }
            if (new Date().getTime() - tirage.getTime() > 0) {
                Dialog.show("Erreur", "Date de tirage doit être superieure à celle d'aujourd'hui", "  OK  ", null);
                chk = false;
            }
            if (chk) {

                Tombola tomb = new Tombola("", titre.getText().trim(), description.getText().trim(), "", dateTirage, "", userConnected, null, path);

                if (t != null) {
                    tomb.setId(t.getId());
                    tomb = new TombolaService().addOrEditTombola(tomb, "e");
                } else {
                    tomb = new TombolaService().addOrEditTombola(tomb, "a");
                }
                String count = (tirage.getTime() - new Date().getTime()) / 1000 + " secondes";

                if (tomb.getId().length() > 0) {
                    if (tomb.getTitre() != null) {
                        showOKForm(tomb.getTitre(), count, "modifiée");
                    } else {
                        Dialog.show("Erreur", "La modification a échouée.", "  OK  ", null);
                    }
                } else {
                    if (tomb.getTitre() != null) {
                        showOKForm(tomb.getTitre(), count, "ajoutée");
                    } else {
                        Dialog.show("Erreur", "L'ajout a échoué.", "  OK  ", null);
                    }
                }

            }
        });

        //** ******************************** MODIFICATION******************************
        if (t != null) {
            this.setTitle("Modifier Tombola");
            titre.setText(t.getTitre());
            description.setText(t.getDescription());
            String dd = t.getDateTirage();

            date.setText(dd.substring(0, dd.indexOf(":") - 3));
            time.setText(dd.substring(dd.indexOf(":") - 2));
            dateTirage = date.getText() + " " + time.getText();
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 3, 0xFFFFFFFF), true);
            Image img = URLImage.createToStorage(placeholder, t.getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + t.getPhoto(),
                    URLImage.RESIZE_SCALE_TO_FILL);

            iv.setImage(img);
            iv.refreshTheme();
            photo.refreshTheme();
            iv.setUIID("PreviewPhoto");
            photo.setUIID("PreviewPhoto");
            submit.setText("Modifier");
            this.revalidate();
        }
        row.add(date).add(time);

        content.add(l2).add(titre);
        content.add(l3).add(description);
        content.add(l4).add(row);
        content.add(l1).add(photo);

        //** ******************************** SHOW GAGNANT IF EXIST ******************************
        if (t != null && t.getGagnant() != null) {
            Label l = new Label("Gagnant :");
            l.setUIID("RestItemInfo");

            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 3, 0xFFFFFFFF), true);
            Image img = URLImage.createToStorage(placeholder, t.getGagnant().getPhoto(), "http://localhost/pidev/WEB/web/uploads/" + t.getGagnant().getPhoto(), URLImage.RESIZE_SCALE_TO_FILL);

            ImageViewer ivv = new ImageViewer(img);

            Container image = new Container();

            ivv.setUIID("PreviewPhoto");
            image.add(ivv);
            String np = t.getGagnant().getNom() + " " + t.getGagnant().getPrenom();
            MultiButton mb = new MultiButton(np);
            mb.setUIID("PreviewPhoto");
            this.revalidate();

            Label title = new Label(np);
            title.setUIID("Title2");

            Container center = new Container(new BorderLayout());
            center.add(BorderLayout.SOUTH, image);
            center.add(BorderLayout.NORTH, title);
            mb.addComponent(BorderLayout.CENTER, center);
            mb.addActionListener(e -> {
                System.out.println("redirect to user profile");
            });
            content.add(l).add(mb);
        }
        this.add(CENTER, content);

        //** ******************************** GESTION BUTTONS ******************************
        if (t != null) {
            Container south = new Container(new GridLayout(2));

            Button delete = new Button("Supprimer");
            delete.setUIID("Delete");
            FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE_FOREVER);

            //** ******************************** SHOW CONFIRMAATION DELETE DIALOG ******************************
            Dialog dlg = new Dialog("Suppression");
            Style dlgStyle = dlg.getDialogStyle();
            dlgStyle.setBorder(Border.createEmpty());
            dlgStyle.setBgTransparency(255);
            dlgStyle.setBgColor(0xffffff);

            TextArea ta = new TextArea("Voulez vous vraiment supprimer cette tombola ?");
            ta.setUIID("DBody");
            ta.setEditable(false);
            ta.setUIID("DialogBody");
            ta.getAllStyles().setFgColor(0);
            ta.getAllStyles().setMarginBottom(110);

            Button close = new Button("Annuler");
            Button conf = new Button("Confirmer");

            close.addActionListener((ee) -> dlg.dispose());
            conf.addActionListener(e -> {

            });
            Container south2 = new Container(new GridLayout(2));
            south2.add(conf).add(close);

            Dimension pre = dlg.getContentPane().getPreferredSize();
            delete.addActionListener(e -> dlg.show(500, 500, 90, 90));

            Container cc = new Container(new BorderLayout());
            cc.add(BorderLayout.NORTH, ta);
            cc.add(BorderLayout.SOUTH, south2);
            dlg.add(cc);
            if (userConnected != null && userConnected.getType() == Enumerations.TypeUser.Artisan) {
                //** ******************************** GESTION BUTTONS ******************************
                if (t.getEtat().equals("Fermée")) {
                    Button lancer = new Button("Lancer Tirage");
                    FontImage.setMaterialIcon(lancer, FontImage.MATERIAL_LOUPE);
                    lancer.addActionListener(e -> {
                        System.out.println("lancer tirage");
                    });
                    disable();;
                    south.add(lancer);
                    south.add(delete);
                    this.add(SOUTH, south);

                } else if (t.getEtat().equals("Ouverte")) {
                    south.add(submit);
                    south.add(delete);
                    this.add(SOUTH, south);
                } else {// Cloturée 
                    disable();
                    this.add(SOUTH, delete);
                }

                // *********************************** show tombola for  user Connected ***********************************
            } else if (userConnected != null && userConnected.getType() == Enumerations.TypeUser.Client) {
                disable();
                this.setTitle("Tombola");
                if (!t.getEtat().equals("Cloturée")) {

                    // *********************************** Check participation of user Connected ***********************************
                    if (!verifPart(t) && t.getEtat().contains("Ouverte")) { // can participate
                        Button participer = new Button("Participer");
                        FontImage.setMaterialIcon(participer, FontImage.MATERIAL_PLUS_ONE);
                        participer.addActionListener(e -> {
                            System.out.println("participer " + userConnected.getNom());
                        });
                        this.add(SOUTH, participer);
                    } else if (verifPart(t)) {// annuler part
                        Button delPart = new Button("Annuler Participation");
                        delPart.setUIID("Delete");
                        FontImage.setMaterialIcon(delPart, FontImage.MATERIAL_DELETE_SWEEP);
                        delPart.addActionListener(e -> {
                            System.out.println("del participation " + userConnected.getNom());
                        });
                        this.add(SOUTH, delPart);
                    }
                }
            } else {// *********************************** If user NOT  Connected ***********************************
                disable();
                this.setTitle("Tombola");
                Button connect = new Button("Se connecter");
                FontImage.setMaterialIcon(connect, FontImage.MATERIAL_HIGHLIGHT);
                connect.addActionListener(e -> {
                    System.out.println("redirect to connect " + userConnected.getNom());
                });
                this.add(SOUTH, connect);
            }

        } else {// ajouuuuuuuuuuuuuuuut
            this.add(SOUTH, submit);
        }

        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(titre,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracteres"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Veuillez saisir que des caracteres")));

        val.addConstraint(description, new LengthConstraint(20, "Minimum 20 caracteres"));

        val.addSubmitButtons(submit);

        Command c1 = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tbf.showBack();
            }
        };
        FontImage.setMaterialIcon(c1, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command c = new Command("Annuler") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (t != null) {
                    new TombolaAddOrEditForm(t).show();
                } else {
                    new TombolaAddOrEditForm(null).show();
                }

            }
        };
        FontImage.setMaterialIcon(c, ' ', "TitleCommand", 5);

        if (t != null) {
            this.addCommand(c1);
            if (userConnected != null && userConnected.getType() != Enumerations.TypeUser.Artisan) {
                Command part = new Command("Participants") {
                    @Override
                    public void actionPerformed(ActionEvent evt) {

                    }
                };
                FontImage.setMaterialIcon(part, ' ', "TitleCommand", 5);
                this.addCommand(part);
            }
            if (userConnected != null && userConnected.getType() == Enumerations.TypeUser.Artisan) {
                this.addCommand(c);
            }

        } else {
            this.addCommand(c1);
            this.addCommand(c);
        }

    }

    private boolean verifPart(Tombola t) {
        for (User u : t.getParticipants()) {
            if (u.getId().equals(userConnected.getId())) {
                return true;
            }
        }
        return false;
    }

    private void disable() {
        titre.setEditable(false);
        description.setEditable(false);
        date.setEnabled(false);
        time.setEnabled(false);
        photo.setEnabled(false);
    }
}

//tn.addActionListener(e -> {
//            String htmlBody = "";
//            InputStream in = Display.getInstance().getResourceAsStream(Form.class, "/gagnant.html");
//            if (in != null) {
//                try {
//                    htmlBody = Util.readToString(in);
//                    in.close();
//                } catch (IOException ex) {
//                    System.out.println(ex);
//                    htmlBody = "Read Error";
//                }
//            }
//            Message m = new Message(htmlBody);
//            m = new Message("<html><body>Check out <a href=\"https://www.codenameone.com/\">Codename One</a>"
//                    + "</body></html>");
//            m.setMimeType(Message.MIME_HTML);
//
//            Display.getInstance().sendMessage(new String[]{"hamdi.megdiche@esprit.tn"}, "Souk lemdina : Gagnant Tombola", m);
//        });
