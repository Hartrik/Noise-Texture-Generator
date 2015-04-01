
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Random;

/**
 * Úplně náhodná textura.
 * 
 * @version 2014-06-01
 * @author Patrik Harag
 */
public class RandomTexture implements Texture {

    @Override
    public final Color getColor(int x, int y, Random random) {
        return getColor(random);
    }
    
    public Color getColor(Random random) {
        return new Color(
                random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                1
        );
    }
    
}