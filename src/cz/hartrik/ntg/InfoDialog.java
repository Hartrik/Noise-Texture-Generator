
package cz.hartrik.ntg;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Dialog s informacemi o aplikaci a autorovi.
 * 
 * @version 2015-04-01
 * @author Patrik Harag
 */
public class InfoDialog extends Dialog<Void> {

    private final String VERSION = "1.10 (2015-04-01)";
    private final int YEAR = 2015;
    
    public InfoDialog() {
        setTitle("Informace");
        getDialogPane().setContent(createContent());
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        setResultConverter((b) -> null);
    }
    
    private Parent createContent() {
        Label title = new Label("Noise Texture Generator");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        
        Label version = new Label("Verze: " + VERSION);
        
        Label author = new Label(
                "© " + YEAR + " Patrik Harag\n" +  "patrik.harag@gmail.com");
        
        VBox box = new VBox(title, version, author);
        box.setSpacing(10);
        box.setPrefSize(400, 300);
        box.setAlignment(Pos.CENTER);
        return box;
    }
    
}