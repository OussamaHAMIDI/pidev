package tn.esprit.GUI;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.app.Main;
public class SignUpForm extends Form {
    static Resources res;
    
    public static void init() {
        if (res == null) {
            //res = Resources.openLayered("/SignUpForm.css");
            res = Main.stheme;
            //UIManager.getInstance().
            UIManager.getInstance().addThemeProps(res.getTheme("Theme"));
        }
    }
    
    public SignUpForm() {
        super("Sign Up");
        this.setUIID("SignUpForm");
        
        res = Main.stheme;
        
        this.setLayout(new BorderLayout());
        Container north = new Container(new FlowLayout(Component.CENTER));
        north.setUIID("SignUpNorth");
        
        Button photoButton = new Button(res.getImage("profile-mask-white.png"));
        photoButton.setUIID("PhotoButton");
        north.addComponent(photoButton);
        
        this.addComponent(BorderLayout.NORTH, north);
        
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("SignUpCenter");
        
        Container row1 = new Container(new GridLayout(1,2));
        
        TextField firstName = new TextField();
        firstName.setUIID("SignUpField");
        firstName.setHint("Prénom");
        firstName.getHintLabel().setUIID("SignupFieldHint");
        TextField lastName = new TextField();
        lastName.setUIID("SignUpField");
        lastName.setHint("Nom");
        lastName.getHintLabel().setUIID("SignupFieldHint");
        row1.addComponent(firstName);
        row1.addComponent(lastName);
        center.addComponent(row1);
        center.setScrollableY(true);
        
        TextField email = new TextField();
        email.setUIID("SignUpField");
        center.addComponent(email);
        email.setHint("Email");
        email.getHintLabel().setUIID("SignupFieldHint");
        
        TextField password = new TextField();
        password.setUIID("SignUpField");
        password.setConstraint(TextField.PASSWORD);
        password.setHint("Mot de passe");
        password.getHintLabel().setUIID("SignupFieldHint");
        center.addComponent(password);
        
        Container row4 = new Container(new BorderLayout());
        Label code = new Label("+216");
        code.setUIID("SignUpLabel");
        row4.addComponent(BorderLayout.WEST, code);
        
        TextField phoneNumber = new TextField();
        phoneNumber.setUIID("SignUpField");
        phoneNumber.setHint("Télephone");
        phoneNumber.getHintLabel().setUIID("SignupFieldHint");
        row4.addComponent(BorderLayout.CENTER, phoneNumber);
        
        center.addComponent(row4);
        
        this.addComponent(BorderLayout.CENTER, center);
        
        Button getStarted = new Button("Creer compte", res.getImage("right_arrow.png"));
        getStarted.setGap(getStarted.getStyle().getFont().getHeight());
        getStarted.setUIID("SignUpButton");
        getStarted.setTextPosition(Component.LEFT);
        
        this.addComponent(BorderLayout.SOUTH, getStarted);
        this.addCommand(new Command("Retour") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
            
        });
        
        final Form backForm = Display.getInstance().getCurrent();
        
        this.setBackCommand(new Command("", res.getImage("back-arrow.png")) {

            @Override
            public void actionPerformed(ActionEvent evt) {
                backForm.showBack();
            }
            
        });
          
        
    }
}
