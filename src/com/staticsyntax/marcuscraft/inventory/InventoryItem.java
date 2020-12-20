package com.staticsyntax.marcuscraft.inventory;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.marcuscraft.graphics.text.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryItem {

    private int id, amount;
    private BufferedImage texture;

    public InventoryItem(int id, BufferedImage texture, int amount) {
        this.id = id;
        this.texture = texture;
        this.amount = amount;
    }

    public void render(Graphics g, int x, int y, int size) {
        g.drawImage(texture, x, y, size, size, null);
        Text.drawString(g, Integer.toString(amount), x + size / 2, y + size / 2, true, Color.WHITE, Assets.slkscr26);
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BufferedImage getTexture() {
        return texture;
    }
}
