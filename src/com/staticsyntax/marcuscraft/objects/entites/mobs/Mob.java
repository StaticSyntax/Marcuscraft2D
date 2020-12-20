package com.staticsyntax.marcuscraft.objects.entites.mobs;

import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.marcuscraft.objects.entites.Entity;
import com.staticsyntax.marcuscraft.world.World;

import java.awt.image.BufferedImage;

public abstract class Mob extends Entity {

    protected DIRECTION dir = DIRECTION.RIGHT;
    protected float jumpDY, jumpHT;
    protected boolean walking, jumping;
    protected int blockDamage = 1;

    public Mob(float x, float y, BufferedImage texture) {
        super(x, y, texture);
    }

    protected abstract void animate();

    protected void move(DIRECTION dir) {
        if(dir == DIRECTION.LEFT) {
            walking = true;
            this.dir = dir;
            if (!isCollidingWithBlock(dir)) {
                if (x <= 0) {
                    x = 0;
                } else {
                    x -= 4;
                }
            }
        }
        if(dir == DIRECTION.RIGHT) {
            walking = true;
            this.dir = dir;
            if (!isCollidingWithBlock(dir)) {
                if (x + width >= World.WORLD_WIDTH * Block.SIZE) {
                    x = World.WORLD_WIDTH * Block.SIZE - width;
                } else {
                    x += 4;
                }
            }
        }
        collisionRect.x = (int)x;
    }

    @Override
    protected void fall() {
        if(!jumping) {
            super.fall();
        }
    }

    protected void jump() {
        final float MAX_DY = 9.0f;
        if(!isCollidingWithBlock(DIRECTION.UP) && jumping) {
            jumpDY += 0.5f;
            if(jumpDY >= MAX_DY) {
                jumpDY = MAX_DY;
                jumpHT += 0.5f;
            }
            if(!isCollidingWithBlock(DIRECTION.UP, this.x, this.y - jumpDY)) {
                if(jumpHT <= 0) {
                    y -= jumpDY;
                }
            } else {
                do { y -= 0.5f; }while(!isCollidingWithBlock(DIRECTION.UP, this.x, this.y - 0.5f));
                jumping = false;
                jumpDY = 0;
            }
        }
        if(jumpDY >= MAX_DY && jumpHT >= 3) {
            jumping = false;
            jumpDY = 0;
            jumpHT = 0;
        }
        collisionRect.y = (int)y;
    }

    public int getBlockDamage() {
        return blockDamage;
    }
}
