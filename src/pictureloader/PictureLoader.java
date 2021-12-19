package pictureloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class PictureLoader {

    public static BufferedImage loadImageFromRes(String path) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("res/images/" + path));
        } catch (IOException e) {
            System.err.println("Cannot read image file!");
        }
        return bi;
    }
}
