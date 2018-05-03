/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;

import com.codename1.components.OnOffSwitch;
import com.codename1.media.MediaManager;
import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.app.Main;
import static tn.esprit.app.Main.m;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class SettingsForm extends Form {

    static Resources res;

    public SettingsForm() {
        super("Réglages", new BorderLayout());
        this.res = Main.stheme;

        Label hint = new Label("Arrêter ou démarrer la musique");
        hint.setUIID("SingUpLabel");
        OnOffSwitch pause = new OnOffSwitch();

        if (Main.m != null) {
            pause.setOff("Arrêter");
            pause.setOn("Démarrer");
        } else {
            pause.setOff("Démarrer");
            pause.setOn("Arrêter");
        }

        pause.setValue(true);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Main.m != null) {
                    if (pause.isValue() == true) {
                        m.play();
                    } else {
                        m.pause();
                    }
                } else {
                    try {
                        m = MediaManager.createMedia("C:\\xampp\\htdocs\\pidev\\Mobile\\song.mp3", false);
                        if (m != null) {
                            m.play();
                        }

                    } catch (IOException ex) {

                    }
                }

            }
        });
        this.add(BOTTOM, pause);
        this.add(CENTER, hint);

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);

        this.addCommand(back);

    }

}
