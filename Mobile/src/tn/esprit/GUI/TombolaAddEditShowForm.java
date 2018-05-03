package tn.esprit.GUI;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.Command;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import tn.esprit.Services.TombolaService;
import tn.esprit.app.Main;
import tn.esprit.entities.Enumerations;
import tn.esprit.entities.Tombola;
import tn.esprit.entities.User;

/**
 *
 * @author Hamdi Megdiche
 */
public class TombolaAddEditShowForm extends Form {

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
    TombolaService ts = new TombolaService();

    private void showOKForm(String msg) {
        Form f = new Form("Tombola", BoxLayout.y());

        TextArea l = new TextArea(4, 2);
        l.setEditable(false);
        l.setUIID("Titre");

        l.setText(msg);
        f.add(l);

        f.addCommand(new Command("Tombolas") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                TombolaForm tf = new TombolaForm();
                tf.showBack();
            }
        });
        f.show();
    }

    public TombolaAddEditShowForm(Tombola t) {

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

                String action = "e";
                if (t != null) {
                    tomb.setId(t.getId());
                    tomb = ts.addOrEditTombola(tomb, action);
                } else {
                    action = "a";
                    tomb = ts.addOrEditTombola(tomb, action);
                }
                long sec = (tirage.getTime() - new Date().getTime()) / 1000;

                String count = count(sec);

                if (action.equals("e")) {
                    if (tomb.getTitre() != null) {
                        showOKForm("Tombola " + tomb.getTitre() + " est modifiée avec sucées.\nIl reste " + count + " avant la fermeture.");
                    } else {
                        Dialog.show("Erreur", "La modification a échouée.", "  OK  ", null);
                    }
                } else {
                    if (tomb.getTitre() != null) {
                        showOKForm("Tombola " + tomb.getTitre() + " est ajoutée avec sucées.\nIl reste " + count + " avant la fermeture.");
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

        if (t != null && userConnected != null) {
            if (userConnected.getType() == Enumerations.TypeUser.Artisan) {
                UsersForm.taesf = this;
                Button participants = new Button("Voir les participants");
                participants.setUIID("ButtonPart");
                FontImage.setMaterialIcon(participants, FontImage.MATERIAL_GROUP);
                participants.addActionListener(e -> new UsersForm(t.getParticipants()).show());
                content.add(participants);
            }
        }

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
                //new ConnectForm().show();
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
            delete.addActionListener(e -> {
                Command show = Dialog.show("Suppression", "Voulez-vous vraiment supprimer cette tombola ?",
                        new Command[]{new Command("Confirmer"), new Command("Annuler")},
                        Dialog.TYPE_WARNING, null, 0);

                if (show.getCommandName().equals("Confirmer")) {
                    String msg = ts.deleteTombola(t.getId());
                    showOKForm(msg);
                }
            });

            if (userConnected != null && userConnected.getType() == Enumerations.TypeUser.Artisan) {
                //** ******************************** GESTION BUTTONS ******************************
                if (t.getEtat().equals("Fermée")) {
                    Button lancer = new Button("Lancer Tirage");
                    FontImage.setMaterialIcon(lancer, FontImage.MATERIAL_LOUPE);
                    lancer.addActionListener(e -> {
                        User gagnant = ts.lancerTirageTombola(t.getId());
                        String msg = "L'utilisateur " + gagnant.getNom() + " " + gagnant.getPrenom() + " est le gagnant du tombola " + t.getTitre()
                                + ".\nUne fenetre s'ouvrira pour vous permetre de lui envoyer un mail.";
                        showOKForm(msg);
                        envoyerMail(gagnant, t);
                    });
                    disable();
                    if (t.getParticipants().size() < 1) {
                        lancer.setEnabled(false);
                    }
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
                            String msg = ts.participerTombola(userConnected.getId(), t.getId());
                            showOKForm(msg);
                        });
                        this.add(SOUTH, participer);
                    } else if (verifPart(t)) {// annuler part
                        Button delPart = new Button("Annuler Participation");
                        delPart.setUIID("Delete");
                        FontImage.setMaterialIcon(delPart, FontImage.MATERIAL_DELETE_SWEEP);
                        delPart.addActionListener(e -> {
                            String msg = ts.annulerParticipationTombola(userConnected.getId(), t.getId());
                            showOKForm(msg);
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
                    new ConnectForm().show();
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

        Command arrowBack = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                tbf.showBack();
            }
        };
        FontImage.setMaterialIcon(arrowBack, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        Command annuler = new Command("Annuler") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (t != null) {
                    new TombolaAddEditShowForm(t).show();
                } else {
                    new TombolaAddEditShowForm(null).show();
                }
            }
        };
        FontImage.setMaterialIcon(annuler, ' ', "TitleCommand", 5);

        Command none = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
            }
        };
        FontImage.setMaterialIcon(none, ' ', "TitleCommand", 5);

        if (t != null) {
            if (userConnected != null) {
                if (userConnected.getType() != Enumerations.TypeUser.Artisan) {
                    UsersForm.taesf = this;
                    Command part = new Command("Participants") {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            new UsersForm(t.getParticipants()).show();
                        }
                    };
                    FontImage.setMaterialIcon(part, ' ', "TitleCommand", 5);

                    this.addCommand(arrowBack);// <-
                    this.addCommand(part);
                } else {
                    this.addCommand(arrowBack);// <-
                    if (t.getEtat().equals("Cloturée")) {
                        this.addCommand(none);
                    } else {
                        this.addCommand(annuler);
                    }
                }
            } else {
                this.addCommand(arrowBack);
                this.addCommand(none);
            }

//            this.addCommand(c1);
//
//            if (userConnected != null && userConnected.getType() == Enumerations.TypeUser.Artisan) {
//                this.addCommand(c);
//            }
        } else {
            this.addCommand(arrowBack);// <-
            this.addCommand(annuler);// Annuler
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

    private String count(long sec) {

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
        return count;
    }

    private void envoyerMail(User u, Tombola t) {
        String htmlBody = "";
        InputStream in = Display.getInstance().getResourceAsStream(Form.class, "/gagnant.html");
        if (in != null) {
            try {
                htmlBody = Util.readToString(in);
                in.close();
            } catch (IOException ex) {
                System.out.println(ex);
                htmlBody = "Read Error";
            }
        }
        Message m = new Message(htmlBody);
        m = new Message("<html><body>Félicitations <b>" + u.getPrenom() + "</b>,<br><br>"
                + "<p>Vous etes gagnant au tirage du tombola <b>" + t.getTitre() + "</b> lancé le <b>" + t.getDateTirage() + "</b>.</p><br>"
                + "<p> </p> </body></html>");
        m.setMimeType(Message.MIME_HTML);

        Display.getInstance().sendMessage(new String[]{u.getEmail()}, "Souk lemdina : Gagnant Tombola", m);
    }

}
