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
    
    /**
     * Filechooser and associated window.
     */
    private FileChooser filechooser;
    private Stage windowStage;
    
    /**
     * Configure FileChooser object for loading XML.
     * @param window to be displayed on
     */
    protected CompositionFileChooser(Stage window) {
        windowStage = window;
        filechooser = new FileChooser();
        filechooser.setTitle("File Browser");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml*"));
    }
    
    /**
     * Open an XML file for loading.
     * @return File to be loaded
     */
    protected File openFile() {
        return filechooser.showOpenDialog(windowStage);
    }
    
    /**
     * Save to an XML file chosen through the FileChooser.
     * @return File to be saved to
     */
    protected File saveFile() {
        File file = filechooser.showSaveDialog(windowStage);
        if (file != null) {
            file = formatName(file);
        }
        return file;
    }
    
    /**
     * Add .xml tag to file name.
     * @param file to have its name formatted
     * @return File with formatted name
     */
    protected File formatName(File file) {
        return new File(cleanFileName(file) + ".xml");
    }
    
    /**
     * Check for illegal character '.' in file names.
     * @param file to have its name sanitized
     * @return File with sanitized name
     */
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
