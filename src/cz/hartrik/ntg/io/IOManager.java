
package cz.hartrik.ntg.io;

import cz.hartrik.common.Color;
import cz.hartrik.common.ui.javafx.ExceptionDialog;
import cz.hartrik.ntg.texture.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.stage.Window;

/**
 * Jednoduchý správce vstupu o výstupu.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class IOManager {
    
    protected final NTGFileChooser fileChooser;
    protected final Window window;
    
    public IOManager(Window window) {
        this.window = window;
        this.fileChooser = new NTGFileChooser(
                new File(System.getProperty("user.dir")));
    }
    
    public boolean save(Collection<Component> components) {
        File file = fileChooser.saveFile(window);
        if (file == null) return false;
        
        Iterable<String> iterable = ()
                -> components.stream().map(this::format).iterator();
        
        OpenOption[] options = { StandardOpenOption.CREATE,
                                 StandardOpenOption.WRITE };
        try {
            Files.write(file.toPath(), iterable, StandardCharsets.UTF_8, options);
            
        } catch (IOException ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(window);
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Chyba při ukládání");
            dialog.setContentText("Došlo k neočekávané chybě během ukládání.");
            dialog.showAndWait();
            
            return true;
        }
        return false;
    }
    
    protected String format(Component component) {
        final Color color = component.getColor();
        return String.format("%o\t%o\t%o\t%d", color.getRed(),
                color.getGreen(), color.getBlue(), component.getCount());
    }
    
    public Collection<Component> load() {
        File file = fileChooser.openFile(window);
        if (file == null) return null;
        
        try {
            return load(file);
            
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.initOwner(window);
            dialog.setTitle("Chyba");
            dialog.setHeaderText("Chyba při načítání");
            dialog.setContentText("Došlo k neočekávané chybě při načítání souboru.");
            dialog.showAndWait();
        }
        return null;
    }
    
    protected Collection<Component> load(File file)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        
        if (file.getName().endsWith(".txt")) {
            
            return Files.lines(file.toPath(), StandardCharsets.UTF_8)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(this::parseComponent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return new ImageLoader().loadSorted(file);
        }
    }
    
    private Component parseComponent(String line) {
        String[] split = line.split("\\s+");
        if (split.length < 3) return null;
        
        try {
            int r = Integer.parseUnsignedInt(split[0]);
            int g = Integer.parseUnsignedInt(split[1]);
            int b = Integer.parseUnsignedInt(split[2]);
            
            Color color = new Color(r, g, b);
            
            if (split.length > 3) {
                int count = Integer.parseUnsignedInt(split[3]);
                return new Component(color, count);
            }
            return new Component(color);
            
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}