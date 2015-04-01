package cz.hartrik.ntg.dialogs;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.LargeInputDialog;
import cz.hartrik.ntg.FrameController;
import cz.hartrik.ntg.texture.Component;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Dialog na filtrování komponent.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class FilterDialog extends AbstractScriptDialog {

    static final String FILE_DEFAULT_CODE = "filter script.js";
    static final String FILE_ICON =
            Resources.absolutePath("icon - filter.png",FrameController.class);
    
    private final Supplier<List<Component>> supplier;
    private final Consumer<List<Component>> consumer;
    
    public FilterDialog(
            Supplier<Window> windowSupplier,
            Supplier<List<Component>> supplier,
            Consumer<List<Component>> consumer) {
        
        super(windowSupplier);
        this.supplier = supplier;
        this.consumer = consumer;
    }
    
    @Override
    protected LargeInputDialog initDialog() {
        String text = Resources.text(FILE_DEFAULT_CODE, getClass());
        
        LargeInputDialog dialog = super.initDialog();
        dialog.getTextArea().setText(text);
        dialog.setTitle("Filtr");
        dialog.setHeaderText("Filtrování barev");
        dialog.setContentText("Zadejte predikát, který bude použit k filtrování.");
        
        Window window = dialog.getDialogPane().getScene().getWindow();
        if (window instanceof Stage)
            ((Stage) window).getIcons().setAll(Resources.image(FILE_ICON));
        
        return dialog;
    }

    @Override
    protected void run(ScriptEngine engine, String code) throws ScriptException {
        engine.eval(code);
        Invocable invocable = (Invocable) engine;

        List<Component> list = supplier.get();
        List<Component> filtered = list.stream().filter(c -> {
            return (boolean) Exceptions.unchecked(() -> {
                Object color = c.getColor();
                int count = c.getCount();
                return invocable.invokeFunction("predicate", color, count);
            });
        }).collect(Collectors.toList());

        consumer.accept(filtered);
    }
    
}