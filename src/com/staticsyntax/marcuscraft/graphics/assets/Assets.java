package com.staticsyntax.marcuscraft.graphics.assets;

import com.staticsyntax.marcuscraft.graphics.images.ImageLoader;
import com.staticsyntax.marcuscraft.graphics.images.ImageReviser;
import com.staticsyntax.marcuscraft.graphics.images.SpriteSheet;
import com.staticsyntax.marcuscraft.graphics.text.FontLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    public static final int BLOCKSIZE = 16;

    private static SpriteSheet playerSheet;
    public static BufferedImage playerIdleRight;
    public static BufferedImage playerIdleLeft;
    public static BufferedImage[] playerWalkRight = new BufferedImage[4];
    public static BufferedImage[] playerWalkLeft = new BufferedImage[4];
    public static BufferedImage inventory, inventoryBar;

    private static SpriteSheet blockSheet;
    public static BufferedImage air;
    public static BufferedImage dirt;
    public static BufferedImage grass;
    public static BufferedImage stone;
    public static BufferedImage cobblestone;
    public static BufferedImage oakWood;
    public static BufferedImage oakLeaves;
    public static BufferedImage bedrock;
    public static BufferedImage[] blockBreaking = new BufferedImage[10];

    public static Font slkscr26 = FontLoader.loadFont("fonts/slkscr.ttf", 26);

    public static void init() {
        playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Player.png"));
        playerIdleRight = playerSheet.crop(0, 0, 17, 16);
        playerIdleLeft = ImageReviser.flip(playerIdleRight, ImageReviser.AXIS.HORIZONTAL);
        playerWalkRight[0] = playerSheet.crop(20, 0, 17, 16);
        playerWalkRight[1] = playerSheet.crop(40, 0, 17, 16);
        playerWalkRight[2] = playerSheet.crop(60, 0, 17, 16);
        playerWalkRight[3] = playerSheet.crop(80, 0, 17, 16);
        playerWalkLeft[0] = ImageReviser.flip(playerWalkRight[0], ImageReviser.AXIS.HORIZONTAL);
        playerWalkLeft[1] = ImageReviser.flip(playerWalkRight[1], ImageReviser.AXIS.HORIZONTAL);
        playerWalkLeft[2] = ImageReviser.flip(playerWalkRight[2], ImageReviser.AXIS.HORIZONTAL);
        playerWalkLeft[3] = ImageReviser.flip(playerWalkRight[3], ImageReviser.AXIS.HORIZONTAL);
        inventory = ImageLoader.loadImage("/textures/Inventory.png");
        inventoryBar = new SpriteSheet(inventory).crop(34, 699, 805, 90);

        blockSheet = new SpriteSheet(ImageLoader.loadImage("/textures/Blocks.png"));
        air = blockSheet.crop(368, 688, BLOCKSIZE, BLOCKSIZE);
        dirt = blockSheet.crop(BLOCKSIZE * 2, 0, BLOCKSIZE, BLOCKSIZE);
        grass = blockSheet.crop(BLOCKSIZE * 3, 0, BLOCKSIZE, BLOCKSIZE);
        stone = blockSheet.crop(BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE);
        cobblestone = blockSheet.crop(0, BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
        oakWood = blockSheet.crop(BLOCKSIZE * 4, BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);
        oakLeaves = blockSheet.crop(BLOCKSIZE * 5, BLOCKSIZE * 3, BLOCKSIZE, BLOCKSIZE);
        bedrock = blockSheet.crop(BLOCKSIZE, BLOCKSIZE, BLOCKSIZE, BLOCKSIZE);

        for(int i = 0; i < blockBreaking.length; i++) {
            blockBreaking[i] = blockSheet.crop(i * BLOCKSIZE, 240, BLOCKSIZE, BLOCKSIZE);
        }
    }
}
