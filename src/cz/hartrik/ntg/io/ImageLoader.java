
package cz.hartrik.ntg.io;

import cz.hartrik.common.Color;
import cz.hartrik.ntg.texture.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Převádí obrázek na barevné komponenty.
 * 
 * @version 2014-06-06
 * @author Patrik Harag
 */
public class ImageLoader {
    
    public List<Component> load(File file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file);
        
        Map<Integer, Integer> colors = countColors(bufferedImage);
        List<Component> components = toComponentList(colors);

        return components;
    }
    
    public Collection<Component> loadSorted(File file) throws IOException {
        List<Component> components = load(file);

        // seřazení podle počtu výskytů od největší po nejmenší
        components.sort((first, second)
                -> Integer.compare(second.getCount(), first.getCount()));
        
        return components;
    }
    
    protected Map<Integer, Integer> countColors(BufferedImage bufferedImage) {
        final int width  = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();

        //   argb     count
        Map<Integer, Integer> colors = new LinkedHashMap<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Integer argb  = bufferedImage.getRGB(x, y);
                Integer count = colors.get(argb);
                colors.put(argb, (count == null ? 1 : count + 1));
            }
        }
        return colors;
    }
    
    protected List<Component> toComponentList(Map<Integer, Integer> colors) {
        List<Component> components = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : colors.entrySet()) {
            Integer argb  = entry.getKey();
            Integer count = entry.getValue();

            components.add(new Component(Color.createARGB(argb), count));
        }

        return components;
    }
    
}