package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class AirBlock extends Block {

    public AirBlock(int x, int y, int z) {
        super(BLOCK_DATA.AIR.ID, x, y, z, Assets.air);
    }
}
