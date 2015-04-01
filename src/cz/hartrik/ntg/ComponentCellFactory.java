
package cz.hartrik.ntg;

import cz.hartrik.ntg.texture.Component;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @version 2014-06-01
 * @author Patrik Harag
 */
public class ComponentCellFactory
        implements Callback<ListView<Component>, ListCell<Component>> {
    
    @Override 
    public ListCell<Component> call(ListView<Component> list) {
        return new ComponentCell();
    }
    
}