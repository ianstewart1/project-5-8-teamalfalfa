/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 *
 * @author stewarim
 */
public class CompositionAlert {
    
    private static Alert alert = new Alert(AlertType.NONE);
    
//    private static CompositionAlert instance = new CompositionAlert();
    
//    public static CompositionAlert instance(){
//        return instance;
//    }
    
    protected static void aboutAlert() {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About our glorious TuneComposer");
        alert.setContentText("Authored by Gavin James-Beckham, "
                + "Ian Stewart, \nNathaniel Larson and Paul Miloy");
        alert.showAndWait();
    }
    
    protected static int newAlert() {
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle("New");
        alert.setHeaderText("Changes have been made since last save");
        alert.setContentText("Do you want to save changes?");
        
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(yes, no, cancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            return 0;
        } else if (result.get() == no) {
            return 1;
        } else {
            return 2;
        }
    }
}
