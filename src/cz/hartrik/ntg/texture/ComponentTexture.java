
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Collection;
import java.util.Random;

/**
 * Textura složená z jedné či více barev o určitém množství. Barvy jsou vybírány
 * náhodně na základě poměru.
 * 
 * @version 2015-03-31
 * @author Patrik Harag
 */
public class ComponentTexture implements Texture {

    protected final Color[] colors;
    protected final int length;
    
    public ComponentTexture(Collection<Component> components) {
        this(components.toArray(new Component[0]));
    }

    public ComponentTexture(Component... components) {
        this.colors = toColorArray(components);
        this.length = colors.length;
    }
    
    private Color[] toColorArray(Component[] components) {
        int length = 0;
        for (Component component : components)
            length += component.getCount();
        
        int i = 0;
        Color[] colorArray = new Color[length];
        for (Component component : components)
            for (int j = 0; j < component.getCount(); j++)
                colorArray[i++] = component.getColor();
        
        return colorArray;
    }
    
    @Override
    public Color getColor(int x, int y, Random random) {
        if (length == 0)
            return Color.WHITE;
        
        return colors[random.nextInt(length)];
    }
    
}