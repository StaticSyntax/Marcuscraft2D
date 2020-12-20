package com.staticsyntax.marcuscraft.graphics.images;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height) {
        return ImageReviser.returnCompatibleImage(sheet.getSubimage(x, y, width, height));
    }
}