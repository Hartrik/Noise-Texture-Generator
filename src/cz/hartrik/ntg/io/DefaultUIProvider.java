package cz.hartrik.ntg.io;

import cz.hartrik.common.ui.javafx.ExceptionDialog;
import java.io.File;
import java.nio.file.Path;
import javafx.application.Platform;
import javafx.stage.Window;

/**
 * 
 * 
 * @version 2015-04-01
 * @author Patrik Harag
 */
public class DefaultUIProvider implements UIProvider {
    
    private final NTGFileChooser fileChooser;

    public DefaultUIProvider() {
        File userDir = new File(System.getProperty("user.dir"));
        this.fileChooser = new NTGFileChooser(userDir);
    }

    @Override
    public Path saveFile(Window window) {
        File file = fileChooser.saveFile(window);
        return (file == null) ? null : file.toPath();
    }

    @Override
    public Path openFile(Window window) {
        File file = fileChooser.openFile(window);
        return (file == null) ? null : file.toPath();
    }

    @Override
    public void showOnLoadErrorMessage(Exception e, Window window) {
        Platform.runLater(() -> {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.initOwner(window);
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Chyba při načítání");
            dialog.setContentText("Došlo k neočekávané chybě při načítání souboru.");
            dialog.showAndWait();
        });
    }

    @Override
    public void showOnSaveErrorMessage(Exception e, Window window) {
        Platform.runLater(() -> {
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.initOwner(window);
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Chyba při ukládání");
            dialog.setContentText("Došlo k neočekávané chybě během ukládání.");
            dialog.showAndWait();
        });
    }
    
}