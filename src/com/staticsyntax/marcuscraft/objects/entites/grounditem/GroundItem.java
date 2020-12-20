package com.staticsyntax.marcuscraft.objects.entites.grounditem;

import com.staticsyntax.marcuscraft.inventory.InventoryItem;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.marcuscraft.objects.entites.Entity;
import com.staticsyntax.marcuscraft.objects.entites.mobs.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GroundItem extends Entity {

    public static final int SIZE = 24;
    private int id, amount;

    public GroundItem(int id, float x, float y, BufferedImage texture, int amount) {
        super(x, y, texture);
        this.id = id;
        this.width = SIZE;
        this.height = SIZE;
        collisionRect.width = SIZE;
        collisionRect.height = SIZE;
        this.amount = amount;
    }

    @Override
    public void update() {
        fall();
        stackCheck();
        pickUpCheck();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int)x - Handler.getCamera().getxOffSet(), (int)y - Handler.getCamera().getyOffSet(), SIZE, SIZE,null);
    }

    private void stackCheck() {
        GroundItem gi = getGroundItemCollision();
        if(gi != null) {
            if(gi.health > 0 && this.health > 0 && gi.id == this.id) {
                this.amount += gi.amount;
                gi.health = 0;
            }
        }
    }

    private GroundItem getGroundItemCollision() {
        Entity e = getEnityCollision();
        if(e != null) {
            if(e instanceof GroundItem) {
                return (GroundItem)e;
            }
        }
        return null;
    }

    private void pickUpCheck() {
        Player p = getPlayerCollision();
        if(p != null && health > 0) {
            while(amount > 0) {
                if(p.getInventory().addItem(new InventoryItem(id, texture, 1))) {
                    amount--;
                    if(amount <= 0) {
                        health = 0;
                    }
                } else {
                    break;
                }
            }
        }
    }

    private Player getPlayerCollision() {
        Entity e = getEnityCollision();
        if(e != null) {
            if(e instanceof Player) {
                return (Player)e;
            }
        }
        return null;
    }

    public void leaveBlock(Block b) {
        if(isInsideCollidableBlock(b)) {
            try {
                if(Handler.getWorld().getWorldBlocks()[b.getX() / Block.SIZE - 1][b.getY() / Block.SIZE][0].getId() == Block.BLOCK_DATA.AIR.ID) {
                    x -= 4;
                } else if(Handler.getWorld().getWorldBlocks()[b.getX() / Block.SIZE + 1][b.getY() / Block.SIZE][0].getId() == Block.BLOCK_DATA.AIR.ID) {
                    x += 4;
                } else if(Handler.getWorld().getWorldBlocks()[b.getX() / Block.SIZE][b.getY() / Block.SIZE + 1][0].getId() == Block.BLOCK_DATA.AIR.ID) {
                    y += 4;
                } else {
                    y -= 4;
                }
                collisionRect.x = (int)x;
                collisionRect.y = (int)y;
            } catch (IndexOutOfBoundsException e) {
                y -= 4;
                collisionRect.y = (int)y;
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
