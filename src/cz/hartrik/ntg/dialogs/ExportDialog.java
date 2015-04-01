package cz.hartrik.ntg.dialogs;

import cz.hartrik.common.io.ClipboardUtil;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.LargeInputDialog;
import cz.hartrik.ntg.FrameController;
import cz.hartrik.ntg.texture.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.script.Invocable;
import javax.script.ScriptEngine;

/**
 * Exportní dialog.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class ExportDialog extends AbstractScriptDialog {

    static final String FILE_DEFAULT_CODE = "export script.js";
    static final String FILE_ICON =
            Resources.absolutePath("icon - export.png", FrameController.class);
    
    private final Supplier<List<Component>> supplier;
    
    public ExportDialog(
            Supplier<Window> windowSupplier,
            Supplier<List<Component>> supplier) {
        
        super(windowSupplier);
        this.supplier = supplier;
    }
    
    @Override
    protected LargeInputDialog initDialog() {
        String text = Resources.text(FILE_DEFAULT_CODE, getClass());
        
        LargeInputDialog dialog = super.initDialog();
        dialog.getTextArea().setText(text);
        dialog.setTitle("Export");
        dialog.setHeaderText("Export barev");
        dialog.setContentText("Script, který vytvoří výsledný text. "
                + "Výstup bude automaticky zkopírován do schánky.");
        
        Window window = dialog.getDialogPane().getScene().getWindow();
        if (window instanceof Stage)
            ((Stage) window).getIcons().setAll(Resources.image(FILE_ICON));
        
        return dialog;
    }

    @Override
    protected void run(ScriptEngine engine, String code) throws Exception {
        List<Component> list = Collections.unmodifiableList(supplier.get());
        
        StringWriter out = new StringWriter();
        engine.getContext().setWriter(new PrintWriter(out));
        
        engine.eval(code);
        Invocable invocable = (Invocable) engine;
        invocable.invokeFunction("out", list);
        
        String outString = out.getBuffer().toString();
        ClipboardUtil.copy(outString);
    }
    
}