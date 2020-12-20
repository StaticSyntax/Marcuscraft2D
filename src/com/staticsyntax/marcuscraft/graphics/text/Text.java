package com.staticsyntax.marcuscraft.graphics.text;

import java.awt.*;

public class Text {

    public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {
        g.setColor(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos;

        if(center) {
            FontMetrics fontMetrics = g.getFontMetrics(font);
            x = xPos - fontMetrics.stringWidth(text) / 2;
            y = (yPos - fontMetrics.getHeight() / 2 ) + fontMetrics.getAscent();
        }

        g.drawString(text, x, y);
    }
}
