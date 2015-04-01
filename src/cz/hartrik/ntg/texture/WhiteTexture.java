
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Random;

/**
 * Bílá textura.
 * 
 * @version 2014-06-04
 * @author Patrik Harag
 */
public final class WhiteTexture implements Texture {

    @Override
    public Color getColor(int x, int y, Random random) {
        return Color.WHITE;
    }
    
}