
package cz.hartrik.ntg;

import cz.hartrik.common.Color;
import cz.hartrik.ntg.texture.Component;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * @version 2014-06-01
 * @author Patrik Harag
 */
public class ComponentCell extends ListCell<Component> {
    
    @Override
    public void updateItem(Component component, boolean empty) {
        super.updateItem(component, empty);
        if (!empty)
            setGraphic(createContent(component));
        else
            setGraphic(new VBox());
    }

    private Node createContent(Component component) {
        Color color = component.getColor();
        
        Rectangle rectangle = new Rectangle(16, 16);
        rectangle.setFill(color.toJavaFXColor());
        
        Label cLabel = new Label(
                "(" +
                colorComponent(color.getRed())   + ", " +
                colorComponent(color.getGreen()) + ", " +
                colorComponent(color.getBlue())  +
                ")"
        );
        cLabel.setFont(Font.font("Courier New"));
        
        Label vLabel = new Label(component.getCount() + "x");
        
        // složení
        final HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(10);
        box.getChildren().addAll(rectangle, cLabel, vLabel);
        return box;
    }

    private String colorComponent(int value) {
        String out = "" + value;
        if (out.length() < 3) out = " " + out;
        if (out.length() < 3) out = " " + out;
        return out;
    }
    
}