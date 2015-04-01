
package cz.hartrik.ntg.io;

import cz.hartrik.common.Color;
import cz.hartrik.ntg.texture.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.stage.Window;

/**
 * Jednoduchý správce vstupu o výstupu.
 * 
 * @version 2015-04-01
 * @author Patrik Harag
 */
public class IOManager {
    
    protected final UIProvider uiProvider;
    protected final Window window;
    
    public IOManager(Window window, UIProvider uiProvider) {
        this.window = window;
        this.uiProvider = uiProvider;
    }
    
    public boolean save(Collection<Component> components) {
        Path file = uiProvider.saveFile(window);
        if (file == null) return false;
        
        Iterable<String> iterable = ()
                -> components.stream().map(this::format).iterator();
        
        OpenOption[] options = { StandardOpenOption.CREATE,
                                 StandardOpenOption.WRITE };
        try {
            Files.write(file, iterable, StandardCharsets.UTF_8, options);
            return true;
            
        } catch (IOException ex) {
            uiProvider.showOnSaveErrorMessage(ex, window);
            return false;
        }
    }
    
    protected String format(Component component) {
        final Color color = component.getColor();
        return String.format("%d\t%d\t%d\t%d", color.getRed(),
                color.getGreen(), color.getBlue(), component.getCount());
    }
    
    public Optional<Collection<Component>> load() {
        Path file = uiProvider.openFile(window);
        return (file == null) ? Optional.empty() : load(file);
    }
    
    public Optional<Collection<Component>> load(Path path) {
        try {
            return Optional.ofNullable(loadFile(path));
            
        } catch (Exception ex) {
            uiProvider.showOnLoadErrorMessage(ex, window);
            return Optional.empty();
        }
    }
    
    private Collection<Component> loadFile(Path path)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        
        if (path.getFileName().toString().endsWith(".txt")) {
            
            return Files.lines(path, StandardCharsets.UTF_8)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(this::parseComponent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } else {
            return new ImageLoader().loadSorted(path.toFile());
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