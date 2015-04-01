
package cz.hartrik.ntg.io;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * Přizpůsobený file chooser.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class NTGFileChooser {
    
    private static final String SAVE_TITLE = "Uložit soubor";
    private static final String OPEN_TITLE = "Načíst soubor";
    
    private static final String ALL_FILTER = "Všechny podporované formáty";
    private static final String TXT_FILTER = "Textové dokumenty";
    private static final String IMG_FILTER = "Obrázky";
    
    private final FileChooser chooserSave;
    private final FileChooser chooserOpen;
    
    public NTGFileChooser(File initalDir) {
        this.chooserSave = new FileChooser();
        this.chooserOpen = new FileChooser();
        
        chooserSave.setTitle(SAVE_TITLE);
        chooserOpen.setTitle(OPEN_TITLE);
        
        chooserSave.setInitialDirectory(initalDir);
        chooserOpen.setInitialDirectory(initalDir);
        
        ExtensionFilter txtFilter = new ExtensionFilter(
                TXT_FILTER, "*.txt");
        
        ExtensionFilter imgFilter = new ExtensionFilter(
                IMG_FILTER, "*.jpg", "*.jpeg", "*.png");
        
        ExtensionFilter allFilter = new ExtensionFilter(
                ALL_FILTER, "*.txt", "*.jpg", "*.jpeg", "*.png");
        
        chooserSave.getExtensionFilters().add(txtFilter);
        chooserOpen.getExtensionFilters().addAll(allFilter, txtFilter, imgFilter);
    }
    
    public File openFile(Window window) {
        File file = chooserOpen.showOpenDialog(window);
        updateInitalDirectory(file);
        return file;
    }
    
    public File saveFile(Window window) {
        File file = chooserSave.showSaveDialog(window);
        updateInitalDirectory(file);
        return file;
    }
    
    protected void updateInitalDirectory(File lastFile) {
        if (lastFile == null) return;
        
        File lastDir = lastFile.getParentFile();
        chooserOpen.setInitialDirectory(lastDir);
        chooserSave.setInitialDirectory(lastDir);
    }
    
}