package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class OakLeavesBlock extends Block {

    public OakLeavesBlock(int x, int y, int z) {
        super(BLOCK_DATA.OAK_LEAVES.ID, x, y, z, Assets.oakLeaves);
    }
}
