package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class DirtBlock extends Block {

    public DirtBlock(int x, int y, int z) {
        super(BLOCK_DATA.DIRT.ID, x, y, z, Assets.dirt);
    }
}
