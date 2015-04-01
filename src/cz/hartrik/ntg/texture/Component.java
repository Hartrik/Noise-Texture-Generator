
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Color;
import java.io.Serializable;

/**
 * Immutable kontejner s barvou a číslem představujícím její množství.
 * 
 * @version 2014-06-05
 * @author Patrik Harag
 */
public class Component implements Cloneable, Serializable {
    
    private static final long serialVersionUID = 6486835764726642158L;

    private final Color color;
    private final int count;

    public Component(Color color) {
        this(color, 1);
    }

    public Component(Color color, int count) {
        if (count < 0)
            throw new IllegalArgumentException("count < 0");
        
        this.color = Checker.requireNonNull(color);
        this.count = count;
    }

    // gettery

    public Color getColor() {
        return color;
    }

    public int getCount() {
        return count;
    }

    // tovární metody

    @Override
    public Component clone() {
        return new Component(color, count);
    }
    
    public Component changeCount(int count) {
        return new Component(color, count);
    }

    public Component changeColor(Color color) {
        return new Component(color, count);
    }

    // statické tovární metody
    
    public static Component[] create(Color... colors) {
        final Component[] components = new Component[colors.length];
        for (int i = 0; i < colors.length; i++)
            components[i] = new Component(colors[i]);
        
        return components;
    }
    
}