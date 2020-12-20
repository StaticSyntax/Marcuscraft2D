package com.staticsyntax.marcuscraft.camera;

import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.marcuscraft.objects.entites.Entity;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.world.World;

public class Camera {

    private int xOffSet, yOffSet;

    public Camera(int xOffSet, int yOffSet) {
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
    }

    public void followEntity(Entity e) {
        xOffSet = (int) e.getX() - Handler.getGame().getWidth() / 2 + e.getWidth() / 2;
        yOffSet = (int) e.getY() - Handler.getGame().getHeight() / 2 + e.getHeight() / 2;

        checkBounds();
    }

    private void checkBounds() {
        if(xOffSet < 0) {
            xOffSet = 0;
        } else if(xOffSet > World.WORLD_WIDTH * Block.SIZE - Handler.getGame().getWidth()) {
            xOffSet = World.WORLD_WIDTH * Block.SIZE - Handler.getGame().getWidth();
        }
        if(yOffSet < 0) {
            yOffSet = 0;
        } else if(yOffSet > World.WORLD_HEIGHT * Block.SIZE - Handler.getGame().getHeight()) {
            yOffSet = World.WORLD_HEIGHT * Block.SIZE - Handler.getGame().getHeight();
        }
    }

    public int getxOffSet() {
        return xOffSet;
    }

    public void setxOffSet(int xOffSet) {
        this.xOffSet = xOffSet;
    }

    public int getyOffSet() {
        return yOffSet;
    }

    public void setyOffSet(int yOffSet) {
        this.yOffSet = yOffSet;
    }
}
