package com.staticsyntax.marcuscraft.world;

import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.camera.Camera;
import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.marcuscraft.objects.entites.Entity;
import com.staticsyntax.marcuscraft.objects.entites.grounditem.GroundItem;
import com.staticsyntax.marcuscraft.objects.entites.mobs.Player;

import java.awt.*;
import java.util.ArrayList;

public class World {

    public static final int WORLD_WIDTH = 128, WORLD_HEIGHT = 128;
    private Block[][][] worldBlocks = new Block[WORLD_WIDTH][WORLD_HEIGHT][2];
    private WorldGenerator worldGenerator;
    private WorldEditor worldEditor;
    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player = new Player(680, 988, Assets.playerIdleRight); //x and y must be divisible by 4.

    public World() {
        Handler.setCamera(new Camera(0, 0));
        worldGenerator = new WorldGenerator(this, player);
        worldGenerator.generateWorld();
        worldEditor = new WorldEditor(this, player);
        entities.add(player);
    }

    public void update() {
        updateBlocks();
        updateEntities();
        Handler.getCamera().followEntity(player);
    }

    public void render(Graphics g) {
        int blocksRendered = 0;
        for(int bx = 0; bx < WORLD_WIDTH; bx++) {
            for(int by = 0; by < WORLD_HEIGHT; by++) {

                if(!(bx * Block.SIZE > Handler.getGame().getWidth() + Handler.getCamera().getxOffSet() - 1 ||
                        bx * Block.SIZE < 1 + Handler.getCamera().getxOffSet() - Block.SIZE)) {
                    if(!(by * Block.SIZE > Handler.getGame().getHeight() + Handler.getCamera().getyOffSet() - 1 ||
                            by * Block.SIZE < 1 + Handler.getCamera().getyOffSet() - Block.SIZE)) {

                        if((worldBlocks[bx][by][1].getId() != 0 || worldBlocks[bx][by][1].isSelected()) && worldBlocks[bx][by][0].getId() == 0) {
                            worldBlocks[bx][by][1].render(g);
                            blocksRendered++;
                        }
                        if(worldBlocks[bx][by][0].getId() != 0 || worldBlocks[bx][by][0].isSelected()) {
                            worldBlocks[bx][by][0].render(g);
                            blocksRendered++;
                        }
                    }
                }
            }
        }
        Handler.setBlocksRendered(blocksRendered);
        renderEntities(g);
    }

    private void updateEntities() {
        for(Entity e : new ArrayList<>(entities)) {
            e.update();
        }
        entities.removeIf(e -> e.getHealth() <= 0);
    }

    private void renderEntities(Graphics g) {
        for(Entity e : entities) {
            if(e instanceof Player == false) {
                e.render(g);
            }
        }
        player.render(g);
    }

    private void updateBlocks() {
        for(int bx = 0; bx < WORLD_WIDTH; bx++) {
            for(int by = 0; by < WORLD_HEIGHT; by++) {
                for(int bz = 0; bz <= 1; bz++) {
                    worldBlocks[bx][by][bz].update();
                }
                worldEditor.handleBlockChange(worldBlocks[bx][by][worldEditor.getzAxis()]);
                removeGroundItemsFromBlock(worldBlocks[bx][by][0]);
            }
        }
    }

    private void removeGroundItemsFromBlock(Block b) {
        for(Entity e : entities) {
            if(e instanceof GroundItem) {
                if(e.isInsideCollidableBlock(b)) {
                    ((GroundItem) e).leaveBlock(b);
                }
            }
        }
    }

    public Block[][][] getWorldBlocks() {
        return worldBlocks;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public WorldEditor getWorldEditor() {
        return worldEditor;
    }
}
