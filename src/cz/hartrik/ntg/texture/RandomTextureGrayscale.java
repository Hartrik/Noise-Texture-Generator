
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Random;

/**
 * Náhodná textura složená z šedých pixelů.
 * 
 * @version 2014-06-01
 * @author Patrik Harag
 */
public class RandomTextureGrayscale extends RandomTexture {

    @Override
    public Color getColor(Random random) {
        return Color.createGray(random.nextDouble());
    }
    
}