package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;

public class BedrockBlock extends Block {

    public BedrockBlock(int x, int y, int z) {
        super(BLOCK_DATA.BEDROCK.ID, x, y, z, Assets.bedrock);
    }
}
