package cz.hartrik.ntg.io;

import java.nio.file.Path;
import javafx.stage.Window;

/**
 * Rozhraní pro poskytovatele GUI.
 * 
 * @version 2015-04-01
 * @author Patrik Harag
 */
public interface UIProvider {
    
    /**
     * Zprostředkuje výběr souboru k uložení. 
     * 
     * @param window nadřazené okno
     * @return cesta k souboru, může být <code>null</code>
     */
    public Path saveFile(Window window);
    
    /**
     * Zprostředkuje výběr souboru k načtení.
     * 
     * @param window nadřazené okno
     * @return cesta k souboru, může být <code>null</code>
     */
    public Path openFile(Window window);
    
    /**
     * Zobrazí chybový dialog při výjimce způsobené ukládáním do souboru.
     * 
     * @param e výjimka
     * @param window nadřazené okno
     */
    public void showOnLoadErrorMessage(Exception e, Window window);
    
    /**
     * Zobrazí chybový dialog při výjimce způsobené načítáním ze souboru.
     * 
     * @param e výjimka
     * @param window nadřazené okno
     */
    public void showOnSaveErrorMessage(Exception e, Window window);
    
}
