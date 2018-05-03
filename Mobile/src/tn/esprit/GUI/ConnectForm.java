package tn.esprit.GUI;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import tn.esprit.Services.UserService;
import tn.esprit.app.Main;
import tn.esprit.entities.User;

/**
 *
 * @author Hamdi Megdiche
 */
public class ConnectForm extends Form {

    static Resources res;
    UserService us = new UserService();

    public ConnectForm() {
        super("");
        this.res = Main.stheme;

        this.setLayout(new BorderLayout());
        this.setUIID("SignUpForm");
        this.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        this.setScrollableY(true);

        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("ConnectNorth");
//        north.getStyle().setPadding(TOP, 300);
        Label logo = new Label("", res.getImage("soukLogo.png"));
        north.addComponent(logo);

        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container row1 = new Container(new BorderLayout());
        Container row2 = new Container(new BorderLayout());

        center.setUIID("ConnectCenter");

        Label l = new Label(res.getImage("user2.png"));
        l.setUIID("ConnectIconField");

        TextField username = new TextField();
        username.setUIID("ConnectField");
        username.setHint("Nom d'utilisateur");
        username.getHintLabel().setUIID("ConnectFieldHint");

        row1.setUIID("RowConnect");
        row1.addComponent(BorderLayout.WEST, l);
        row1.addComponent(BorderLayout.CENTER, username);

        Label l2 = new Label(res.getImage("lock2.png"));
        l2.setUIID("ConnectIconField");

        TextField password = new TextField();
        password.setUIID("ConnectField");
        password.setConstraint(TextArea.PASSWORD);
        password.setHint("Mot de passe");
        password.getHintLabel().setUIID("ConnectFieldHint");

        row2.setUIID("RowConnect");
        row2.addComponent(BorderLayout.WEST, l2);
        row2.addComponent(BorderLayout.CENTER, password);

        center.add(row1).add(row2);

        Button connect = new Button("Se Connecter");
        connect.setUIID("ConnectButton");
        connect.addActionListener(e -> {
            User u = us.connect(username.getText(), password.getText());
            if (u != null) {
                Main.userConnected = u;
                Command show = Dialog.show("Connexion", "La connexion est établie.\n"
                        + "Vous êtes desormais connecté en tant que : "+u.getType(),
                        new Command[]{new Command("Ok")},
                        Dialog.TYPE_WARNING, null, 0);

                if (show.getCommandName().equals("Ok")) {
                    Main.shome.show();
                }
            } else {
                Command show = Dialog.show("Echec", "Username/Mot de passe invalide(s) !",
                        new Command[]{new Command("Réessayer"),new Command("Annuler")},
                        Dialog.TYPE_WARNING, null, 0);
                 if (show.getCommandName().equals("Annuler")) {
                    Main.shome.show();
                }
            }
        });

        center.add(connect);

        this.add(BorderLayout.NORTH, north);
        this.add(BorderLayout.CENTER, center);

        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(username,
                new GroupConstraint(
                        new LengthConstraint(1, "Username vide !"),
                        new RegexConstraint("^([a-zA-Z]*)$", "Veuillez saisir que des caracteres")));
        val.addConstraint(password, new LengthConstraint(1, "Mot de passe vide !"));

        val.addSubmitButtons(connect);
    }
}
