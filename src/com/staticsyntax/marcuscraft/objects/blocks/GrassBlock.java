package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class GrassBlock extends Block {

    public GrassBlock(int x, int y, int z) {
        super(BLOCK_DATA.GRASS.ID, x, y, z, Assets.grass);
    }
}
