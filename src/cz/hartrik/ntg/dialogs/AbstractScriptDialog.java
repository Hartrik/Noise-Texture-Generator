package cz.hartrik.ntg.dialogs;

import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.common.ui.javafx.LargeInputDialog;
import java.util.function.Supplier;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Window;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Základ pro scriptovací dialogy.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
abstract class AbstractScriptDialog {
    
    static final String FILE_JS_LOGO = "icon - js.png";

    protected final Supplier<Window> ownerSupplier;
    protected Dialog<String> inputDialog;
    
    public AbstractScriptDialog(Supplier<Window> windowSupplier) {
        this.ownerSupplier = windowSupplier;
    }
    
    protected LargeInputDialog initDialog() {

        LargeInputDialog dialog = new LargeInputDialog();
        dialog.initOwner(ownerSupplier.get());
        
        dialog.getTextArea().setFont(Font.font("monospaced", 12));
        dialog.getTextArea().setPrefSize(550, 300);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setGraphic(new ImageView(Resources.image(FILE_JS_LOGO, getClass())));
        
        return dialog;
    }
    
    public synchronized void showAndWait() {
        if (inputDialog == null) inputDialog = initDialog();
        
        inputDialog.showAndWait()
                .filter(s -> !s.trim().isEmpty())
                .ifPresent(this::execute);
    }
    
    protected void execute(String code) {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        try {
            run(engine, code);
            
        } catch (Throwable ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(ownerSupplier.get());
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Chyba při spuštění scriptu");
            dialog.setContentText("Došlo k chybě při spouštění scriptu.");
            dialog.showAndWait();
        }
    }
    
    protected abstract void run(ScriptEngine engine, String code)
            throws Exception;
    
}