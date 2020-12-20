package com.staticsyntax.marcuscraft.objects.entites;

import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.marcuscraft.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected enum DIRECTION {
        LEFT, RIGHT, UP, DOWN;
    }

    protected float x, y, fallDY;
    protected int width, height;
    protected BufferedImage texture;
    protected Rectangle collisionRect;
    protected int health = 1;

    public Entity(float x, float y, BufferedImage texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        width = texture.getWidth();
        height = texture.getHeight();
        collisionRect = new Rectangle(width, height);
        collisionRect.x = (int)x;
        collisionRect.y = (int)y;
    }

    public abstract void update();

    public abstract void render(Graphics g);

    protected void fall() {
        final float MAX_DY = 9.0f;
        if(!isCollidingWithBlock(DIRECTION.DOWN)) {
            fallDY += 0.5f;
            if(fallDY >= MAX_DY) {
                fallDY = MAX_DY;
            }
            if(!isCollidingWithBlock(DIRECTION.DOWN, this.x, this.y + fallDY)) {
                y += fallDY;
            } else {
                do {
                    y += 0.5f;
                } while(!isCollidingWithBlock(DIRECTION.DOWN, this.x, this.y + 0.5f));
                fallDY = 0;
            }
        }
        collisionRect.y = (int)y;
    }

    protected boolean isCollidingWithBlock(DIRECTION d) {
        return isCollidingWithBlock(d, this.x, this.y);
    }

    protected boolean isCollidingWithBlock(DIRECTION d, float x, float y) {
        for(int bx = 0; bx < World.WORLD_WIDTH; bx++) {
            for(int by = 0; by < World.WORLD_HEIGHT; by++) {

                Block b = Handler.getWorld().getWorldBlocks()[bx][by][0];

                if(b.isCollidable()) {
                    if(d == DIRECTION.LEFT) {
                        if(y + height > b.getY() && y < b.getY() + Block.SIZE) {
                            if(x <= b.getX() + Block.SIZE && x > b.getX()) {
                                return true;
                            }
                        }
                    } else if(d == DIRECTION.RIGHT) {
                        if(y + height > b.getY() && y < b.getY() + Block.SIZE) {
                            if(x + width >= b.getX() && x < b.getX() + Block.SIZE) {
                                return true;
                            }
                        }
                    } else if(d == DIRECTION.UP) {
                        if(x + width > b.getX() && x < b.getX() + Block.SIZE) {
                            if(y <= b.getY() + Block.SIZE && y + height > b.getY()) {
                                return true;
                            }
                        }
                    } else if(d == DIRECTION.DOWN) {
                        if(x + width > b.getX() && x < b.getX() + Block.SIZE) {
                            if(y < b.getY() + Block.SIZE && y + height >= b.getY()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    protected Entity getEnityCollision() {
        for(Entity e : Handler.getWorld().getEntities()) {
            if(e != this && collisionRect.intersects(e.getCollisionRect())) {
                return e;
            }
        }
        return null;
    }

    public boolean isInsideBlock(Block b) {
        if(x + width > b.getX() && x < b.getX() + Block.SIZE) {
            if(y < b.getY() + Block.SIZE && y + height > b.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInsideCollidableBlock(Block b) {
        if(isInsideBlock(b) && b.isCollidable()) {
            return true;
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public int getHealth() {
        return health;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
