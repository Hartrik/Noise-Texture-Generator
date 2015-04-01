
package cz.hartrik.ntg;

import cz.hartrik.common.io.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Vstupní třída.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        setUserAgentStylesheet(STYLESHEET_MODENA);
        
        Parent root = FXMLLoader.load(getClass().getResource("Frame.fxml"));
        Scene scene = new Scene(root);
        
        stage.setTitle("Noise Texture Generator");
        stage.setMinWidth(295);
        stage.setMinHeight(350);
        stage.getIcons().add(Resources.image("icon - frame.png", getClass()));
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}