package com.staticsyntax.marcuscraft.objects.entites.mobs;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.marcuscraft.inventory.Inventory;
import com.staticsyntax.marcuscraft.objects.entites.Entity;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.graphics.images.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Mob {

    private Inventory inventory = new Inventory();
    private Animation walkRightAnim = new Animation(100, Assets.playerWalkRight);
    private Animation walkLeftAnim = new Animation(100, Assets.playerWalkLeft);

    public Player(float x, float y, BufferedImage texture) {
        super(x, y, texture);
        width = 52;
        height = 52;
        collisionRect.width = width;
        collisionRect.height = height;
        blockDamage = 2;
    }

    @Override
    public void update() {
        playerJump();
        fall();
        playerMove();
        animate();
        inventory.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int) x - Handler.getCamera().getxOffSet(),
                             (int) y - Handler.getCamera().getyOffSet(),
                             width, height, null);
        inventory.render(g);
    }

    @Override
    protected void animate() {
        if(walking) {
            if(dir == Entity.DIRECTION.LEFT) {
                walkLeftAnim.update();
                texture = walkLeftAnim.getCurrentFrame();
            } else {
                walkRightAnim.update();
                texture = walkRightAnim.getCurrentFrame();
            }
        } else {
            if(dir == Entity.DIRECTION.LEFT) {
                texture = Assets.playerIdleLeft;
            } else {
                texture = Assets.playerIdleRight;
            }
        }
    }

    private void playerMove() {
        if(Handler.getKeyManager().a) {
            move(Entity.DIRECTION.LEFT);
        }
        if(Handler.getKeyManager().d) {
            move(Entity.DIRECTION.RIGHT);
        }
        if(!Handler.getKeyManager().a && !Handler.getKeyManager().d) {
            walking = false;
        }
    }

    private void playerJump() {
        if(Handler.getKeyManager().space && !jumping && isCollidingWithBlock(Entity.DIRECTION.DOWN) && !isCollidingWithBlock(Entity.DIRECTION.UP)) {
            jumping = true;
        }
        jump();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
