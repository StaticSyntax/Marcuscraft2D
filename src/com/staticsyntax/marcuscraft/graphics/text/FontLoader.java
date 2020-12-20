package com.staticsyntax.marcuscraft.graphics.text;

import java.awt.*;
import java.io.InputStream;

public class FontLoader {

    public static Font loadFont(String path, float size) {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
