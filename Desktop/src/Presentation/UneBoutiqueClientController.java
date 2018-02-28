/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import static Presentation.UneBoutiqueArtisanController.contenu;
import static Presentation.UneBoutiqueArtisanController.index;
import static Presentation.UneBoutiqueController.index;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class UneBoutiqueClientController implements Initializable {
    Boutique boutiqueB;
    Boutique bou;
    static public int index;
    static public List<Boutique> contenu;
    static public MenuController mc;
    @FXML
    private AnchorPane boutique;
    @FXML
    private Label userB;
    @FXML
    private Label adresseB;
    @FXML
    private Label nomB;
    @FXML
    private Label dateB;
    public AnchorPane getThis()
    {
        return boutique;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         boutiqueB = contenu.get(index);
        bou= boutiqueB;
        dateB.setText(boutiqueB.getDateCreation().toString().replace("T", " "));
        nomB.setText(boutiqueB.getNom());
        
        adresseB.setText(boutiqueB.getAdresse());
    }    

    @FXML
    private void test(MouseEvent event) {
        System.out.println(boutiqueB.toString() + index);

    }
    
}
