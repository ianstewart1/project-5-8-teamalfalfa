/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author stewarim
 */
public class CompositionFileChooser {
    
    private FileChooser filechooser;
    private Stage windowStage;
    
    protected CompositionFileChooser(Stage window) {
        windowStage = window;
        filechooser = new FileChooser();
//        filechooser.setTitle("");
//        filechooser.setInitialDirectory(File );
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml*"));
    }
    
    protected File openFile() {
        return filechooser.showOpenDialog(windowStage);
    }
    
    protected File saveFile() {
        return filechooser.showSaveDialog(windowStage);
    }
    
}
