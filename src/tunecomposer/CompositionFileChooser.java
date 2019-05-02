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
    private File currentFile = null;
    
    protected CompositionFileChooser(Stage window) {
        windowStage = window;
        filechooser = new FileChooser();
        filechooser.setTitle("File Browser");
//        filechooser.setInitialDirectory(new File(File.separator + "Compositions/") );
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml*"));
    }
    
    protected File openFile() {
        return filechooser.showOpenDialog(windowStage);
    }
    
    protected File saveFile() {
        File file = filechooser.showSaveDialog(windowStage);
        if (file != null) {
            file = formatName(filechooser.showSaveDialog(windowStage));
        }
        return file;
    }
    
    protected File formatName(File file) {
        return new File(cleanFileName(file) + ".xml");
    }
    
    protected String cleanFileName(File file) {
        String oldName = file.getName();
        String cleanName = "";
        for (char ch : oldName.toCharArray()) {
            if (ch == '.') break;
            cleanName += ch;
        }
        return cleanName;
    }
    
}
