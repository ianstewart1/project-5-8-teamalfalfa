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
import javafx.scene.layout.Region;

/**
 * Creates and alert dialog to prompt the user about saving and about
 * the TuneComposer.
 * @author stewarim
 */
public class CompositionAlert {
    
    /**
     * Alert object to be modified.
     */
    private static Alert alert = new Alert(AlertType.NONE);
    
    /**
     * Configures alert object to display about information.
     */
    protected static void aboutAlert() {
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About our glorious TuneComposer");
        alert.setContentText("This here application is intended for only \n"
                + "the most keen of composer to craft their fine symphonies. \n"
                + "Authored by Gavin James-Beckham, Ian Stewart, "
                + "Nathaniel Larson and Paul Milloy");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    /**
     * Configures alert object to display a prompt about saving.
     * @return int value representing user choice
     */
    protected static int saveAlert() {
        alert.setAlertType(AlertType.CONFIRMATION);
        alert.setTitle("Save?");
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
