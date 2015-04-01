
package cz.hartrik.ntg;

import cz.hartrik.ntg.texture.Texture;
import java.util.Random;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Stará se o vykreslování textury do {@link ImageView}.
 * 
 * @version 2014-06-04
 * @author Patrik Harag
 */
public class Renderer {
    
    private final ImageView imageView;
    private final Random random;
    private Texture texture;
    
    private WritableImage image;
    private int width;
    private int height;
    
    public Renderer(ImageView imageView, Texture texture) {
        this(imageView, texture, new Random());
    }
    
    public Renderer(ImageView imageView, Texture texture, Random random) {
        this.imageView = imageView;
        this.texture = texture;
        this.random = random;
        
        this.width  = (int) imageView.getFitWidth();
        this.height = (int) imageView.getFitHeight();
        this.image  = new WritableImage(width, height);
        
        repaint();
        imageView.setImage(image);
    }
    
    // vykreslování
    
    /**
     * Změní velikost obrázku v {@link ImageView} oříznutím nebo generováním
     * okrajů.
     * 
     * @param width nová šířka
     * @param height nová výška
     */
    public void resize(int width, int height) {
        if (this.width == width && this.height == height) return;
        
        final int cWidth  = (width  > this.width  ? this.width  : width);
        final int cHeight = (height > this.height ? this.height : height);
        
        WritableImage newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();
        
        // kopírování stávajících pixelů
        writer.setPixels(0, 0, cWidth, cHeight, image.getPixelReader(), 0, 0);
        
        // při zvětšení - generování nových
        if (width - cWidth > 0)
            for (int x = cWidth; x < width; x++)
                for (int y = 0; y < cHeight; y++) setAt(x, y, writer);
        
        if (height - cHeight > 0)
            for (int y = cHeight; y < height; y++)
                for (int x = 0; x < cWidth; x++) setAt(x, y, writer);
        
        this.width = width;
        this.height = height;
        image = newImage;
        imageView.setImage(newImage);
    }

    /**
     * Změní velikost a překreslí obrázek.
     * 
     * @param width nová šířka
     * @param height nová výška
     */
    public final void resizeAndRepaint(int width, int height) {
        if (this.width != width || this.height != height) {
            image = new WritableImage(width, height);
            imageView.setImage(image);
            
            this.width = width;
            this.height = height;
        }
        
        repaint();
    }
    
    /**
     * Překreslí celý obrázek.
     */
    public final void repaint() {
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                setAt(x, y, pixelWriter);
    }
    
    protected final void setAt(int x, int y, PixelWriter pixelWriter) {
        pixelWriter.setArgb(x, y, texture.getColor(x, y, random).getARGB());
    }
    
    // gettery, settery
    
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
    
}