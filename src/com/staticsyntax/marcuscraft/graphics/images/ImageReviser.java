package com.staticsyntax.marcuscraft.graphics.images;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class ImageReviser {

    public enum AXIS {
        HORIZONTAL, VERTICAL;
    }

    public static BufferedImage returnCompatibleImage(BufferedImage image) {
        GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        if (image.getColorModel().equals(graphicsConfig.getColorModel())) {
            return image;
        }

        BufferedImage newImage = graphicsConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return newImage;
    }

    public static BufferedImage transform(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage flip(BufferedImage image, AXIS axis) {
        AffineTransform at = new AffineTransform();
        if(axis == AXIS.HORIZONTAL) {
            at.concatenate(AffineTransform.getScaleInstance(-1, 1));
            at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        } else if(axis == AXIS.VERTICAL) {
            at.concatenate(AffineTransform.getScaleInstance(1, -1));
            at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        }
        return transform(image, at);
    }

    public static BufferedImage shade(BufferedImage image, float scaleFactor) {
        GraphicsConfiguration graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage newImage = graphicsConfig.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.OPAQUE);

        Graphics graphics = newImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        RescaleOp op = new RescaleOp(scaleFactor, 0, null);
        newImage = op.filter(newImage, null);
        return newImage;
    }

    public static BufferedImage recolor(BufferedImage image, int rgb) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < newImage.getWidth(); x++) {
            for(int y = 0; y < newImage.getHeight(); y++) {
                newImage.setRGB(x, y, image.getRGB(x, y) + rgb);
            }
        }
        return newImage;
    }
}
