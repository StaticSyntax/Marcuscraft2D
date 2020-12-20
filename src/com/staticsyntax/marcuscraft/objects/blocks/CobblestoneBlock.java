package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class CobblestoneBlock extends Block {

    public CobblestoneBlock(int x, int y, int z) {
        super(BLOCK_DATA.COBBLESTONE.ID, x, y, z, Assets.cobblestone);
    }
}
