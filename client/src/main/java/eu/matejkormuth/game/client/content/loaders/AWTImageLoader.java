package eu.matejkormuth.game.client.content.loaders;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.client.content.ContentLoader;
import eu.matejkormuth.game.client.gl.Texture2D;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class AWTImageLoader extends ContentLoader<Texture2D> {

    @Override
    public Texture2D load(Path path) {
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer texData = BufferUtils.createByteBuffer(pixels.length * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            int currentPixel;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    currentPixel = pixels[x * image.getWidth() + y];

                    texData.put((byte) ((currentPixel >> 16) & 0xFF));
                    texData.put((byte) ((currentPixel >> 8) & 0xFF));
                    texData.put((byte) ((currentPixel) & 0xFF));
                    if (hasAlpha) {
                        texData.put((byte) ((currentPixel >> 24) & 0xFF));
                    } else {
                        texData.put((byte) 0xFF);
                    }
                }
            }
            texData.flip();
            return new Texture2D(image.getWidth(), image.getHeight(), texData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
