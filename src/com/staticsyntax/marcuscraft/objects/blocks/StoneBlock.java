package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class StoneBlock extends Block {

    public StoneBlock(int x, int y, int z) {
        super(BLOCK_DATA.STONE.ID, x, y, z, Assets.stone);
    }
}
