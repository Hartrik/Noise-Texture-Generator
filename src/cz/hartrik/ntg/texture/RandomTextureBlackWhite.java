
package cz.hartrik.ntg.texture;

import cz.hartrik.common.Color;
import java.util.Random;

/**
 * Náhodná textura složená jen z černých a bílých pixelů.
 * 
 * @version 2014-06-01
 * @author Patrik Harag
 */
public class RandomTextureBlackWhite extends RandomTexture {

    @Override
    public Color getColor(Random random) {
        return random.nextBoolean() ? Color.BLACK : Color.WHITE;
    }
    
}