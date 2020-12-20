package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class OakWoodBlock extends Block {

    public OakWoodBlock(int x, int y, int z) {
        super(BLOCK_DATA.OAK_WOOD.ID, x, y, z, Assets.oakWood);
    }
}
