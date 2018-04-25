/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class HomeForm extends Form{
   
        
    public HomeForm(){
        super("Home");
        
        Resources css;
        try {
            css = Resources.openLayered("/theme.css");
            UIManager.getInstance().addThemeProps(css.getTheme(css.getThemeResourceNames()[0]));
        } catch (IOException ex) {}
        
        this.setUIID("HomeForm");
        
    }
    
}
