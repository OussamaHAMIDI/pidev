package tn.esprit.GUI;

import tn.esprit.entities.Tombola;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.Util;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import java.util.List;

import tn.esprit.app.Main;
import tn.esprit.Services.TombolaService;

/**
 *
 * @author Hamdi Megdiche
 */
public class TombolaAddForm extends Form {

    static Resources res;

    private void showOKForm(String name) {
        Form f = new Form("Ajoutée", BoxLayout.y());
        f.add(new SpanLabel("Thanks " + name + " for your submission. You can press the back arrow and try again"));
        TombolaForm tf = new TombolaForm();

        f.addCommand(new Command("Done") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                tf.showBack();
            }
        });
        //f.getToolbar().setBackCommand("", e -> tf.show());
        f.show();
    }

    public TombolaAddForm() {
        super("Ajouter tombola", new BorderLayout());
        this.res = Main.stheme;

        this.setScrollableY(true);
        TextModeLayout tm = new TextModeLayout(4, 2);

        Container content = new Container(tm);
        TextField titre = new TextField();

        TextComponent description = new TextComponent().multiline(true).rows(4);
        System.out.println(new Date().toString());
        //int h = Integer.parseInt(s.)
        //Sun Apr 29 14:41:29 WAT 2018

        Picker date = new Picker();
        date.setType(Display.PICKER_TYPE_DATE);
        date.setDate(new Date());
        date.setUIID("Container");
        date.setLabelForComponent(new Label("Date"));
        date.addActionListener(e -> {
            System.out.println(date.getDate());
        });

        Picker time = new Picker();
        time.setText("00:00");
        time.setType(Display.PICKER_TYPE_TIME);
        time.setLabelForComponent(new Label("Heure"));
        time.setUIID("Container");

        System.out.println(date.getDate());

        content.add(tm.createConstraint().horizontalSpan(2), new SpanLabel("Veuillez remplir tous les champs"));
        content.add(tm.createConstraint().horizontalSpan(2), titre);
        content.add(tm.createConstraint().horizontalSpan(2), description);
        content.add(tm.createConstraint().widthPercentage(60), date);
        content.add(tm.createConstraint().widthPercentage(40), time);

        this.setEditOnShow(titre);
        content.setScrollableY(true);

        Button submit = new Button("Ajouter");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {

            showOKForm(titre.getText());
        });

        this.add(CENTER, content);
        this.add(SOUTH, submit);

        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(titre,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracteres"),
                        new RegexConstraint("^([a-zA-Z ]*)$", "Veuillez saisir que des caracteres"))).
                addSubmitButtons(submit);

        val.addConstraint(description,new LengthConstraint(20, "Minimum 20 caracteres")).
                addSubmitButtons(submit);
        
       

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
