package com.staticsyntax.marcuscraft.world;

import com.staticsyntax.marcuscraft.objects.blocks.*;
import com.staticsyntax.marcuscraft.objects.entites.mobs.Player;

public class WorldGenerator {

    private World world;
    private Player player;

    public WorldGenerator(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void generateWorld() {
        generateTerrain();
        generateTrees(0, 0.1f);
    }

    private void generateTerrain() {
        for(int bx = 0; bx < World.WORLD_WIDTH; bx++) {
            for(int by = 0; by < 20; by++) {
                world.getWorldBlocks()[bx][by][0] = new AirBlock(bx * Block.SIZE, by * Block.SIZE, 0);
                world.getWorldBlocks()[bx][by][1] = new AirBlock(bx * Block.SIZE, by * Block.SIZE, 1);
            }
            world.getWorldBlocks()[bx][20][0] = new GrassBlock(bx * Block.SIZE, 20 * Block.SIZE, 0);
            world.getWorldBlocks()[bx][20][1] = new DirtBlock(bx * Block.SIZE, 20 * Block.SIZE, 1);
            for(int y2 = 21; y2 < 29; y2++) {
                world.getWorldBlocks()[bx][y2][0] = new DirtBlock(bx * Block.SIZE, y2 * Block.SIZE, 0);
                world.getWorldBlocks()[bx][y2][1] = new DirtBlock(bx * Block.SIZE, y2 * Block.SIZE, 1);
            }
            for(int y3 = 29; y3 < World.WORLD_HEIGHT - 1; y3++) {
                world.getWorldBlocks()[bx][y3][0] = new StoneBlock(bx * Block.SIZE, y3 * Block.SIZE, 0);
                world.getWorldBlocks()[bx][y3][1] = new StoneBlock(bx * Block.SIZE, y3 * Block.SIZE, 1);
            }
            world.getWorldBlocks()[bx][World.WORLD_HEIGHT - 1][0] = new BedrockBlock(bx * Block.SIZE, (World.WORLD_HEIGHT - 1) * Block.SIZE, 0);
            world.getWorldBlocks()[bx][World.WORLD_HEIGHT - 1][1] = new BedrockBlock(bx * Block.SIZE, (World.WORLD_HEIGHT - 1) * Block.SIZE, 1);
        }
    }

    private void generateTrees(int bz, float chance) {
        int exceptionsCaught = 0;
        for(int bx = 0; bx < World.WORLD_WIDTH; bx++) {
            for(int by = 0; by < World.WORLD_HEIGHT; by++) {
                if(Math.random() < chance && world.getWorldBlocks()[bx][by][bz].getId() == Block.BLOCK_DATA.GRASS.ID) {
                    try {
                        if(world.getWorldBlocks()[bx - 1][by - 1][bz].getId() != Block.BLOCK_DATA.OAK_WOOD.ID
                        && !player.isInsideBlock(world.getWorldBlocks()[bx][by - 1][bz])) {
                            try {
                                for(int woodY = 1; woodY <= 3; woodY++) {
                                    try {
                                        world.getWorldBlocks()[bx][by - woodY][bz] = new OakWoodBlock(bx * Block.SIZE, (by - woodY) * Block.SIZE, bz);
                                    } catch (IndexOutOfBoundsException e) {
                                        exceptionsCaught++;
                                    }
                                }
                            } catch (IndexOutOfBoundsException e) {
                                exceptionsCaught++;
                            }
                            for(int leafY = 4; leafY <= 5; leafY++) {
                                for(int leafX = 0; leafX < 5; leafX++) {
                                    try {
                                        world.getWorldBlocks()[bx - 2 + leafX][by - leafY][bz] = new OakLeavesBlock((bx - 2 + leafX) * Block.SIZE, (by - leafY) * Block.SIZE, bz);
                                    } catch (IndexOutOfBoundsException e) {
                                        exceptionsCaught++;
                                    }
                                }
                            }
                            for(int leafX = 0; leafX < 3; leafX++) {
                                try {
                                    world.getWorldBlocks()[bx - 1 + leafX][by - 6][bz] = new OakLeavesBlock((bx - 1 + leafX) * Block.SIZE, (by - 6) * Block.SIZE, bz);
                                } catch (IndexOutOfBoundsException e) {
                                    exceptionsCaught++;
                                }
                            }
                            try {
                                world.getWorldBlocks()[bx][by - 7][bz] = new OakLeavesBlock(bx * Block.SIZE, (by - 7) * Block.SIZE, bz);
                            } catch (IndexOutOfBoundsException e) {
                                exceptionsCaught++;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        exceptionsCaught++;
                    }
                }
            }
        }
        if(exceptionsCaught > 0) {
            System.out.println("generateTrees() IndexOutOfBoundsException caught. (" + exceptionsCaught + ")");
        }
    }
}
