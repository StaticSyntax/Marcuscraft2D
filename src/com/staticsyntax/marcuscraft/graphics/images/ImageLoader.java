package com.staticsyntax.marcuscraft.graphics.images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageReviser.returnCompatibleImage(ImageIO.read(ImageLoader.class.getResource(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
