
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Random;

/**
 * Rozhraní pro texturu.
 * 
 * @version 2014-06-01
 * @author Patrik Harag
 */
public interface Texture {
    
    public Color getColor(int x, int y, Random random);
    
}