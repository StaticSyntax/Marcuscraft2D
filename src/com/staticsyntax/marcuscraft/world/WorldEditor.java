package com.staticsyntax.marcuscraft.world;

import com.staticsyntax.marcuscraft.inventory.InventoryItem;
import com.staticsyntax.marcuscraft.objects.blocks.Block;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.objects.blocks.AirBlock;
import com.staticsyntax.marcuscraft.objects.entites.mobs.Player;

public class WorldEditor {

    private World world;
    private Player player;
    private int zAxis = 0;

    public WorldEditor(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void handleBlockChange(Block b) {
        zAxisCheck();
        placeBlock(b);
        breakBlock(b);
        repairBlock(b);
    }

    private void zAxisCheck() {
        if(Handler.getKeyManager().shift) {
            zAxis = 1;
        } else {
            zAxis = 0;
        }
    }

    private void placeBlock(Block b) {
        if(b.isSelected()) {
            if(Handler.getMouseManager().isRightClicked()) {
                if(player.getInventory().getItems()[player.getInventory().getSelectedItemSlot()] != null && !player.getInventory().isMouseInsideInventory()) {
                    InventoryItem currentItem = player.getInventory().getItems()[player.getInventory().getSelectedItemSlot()];
                    if(zAxis == 0 && b.getId() == Block.BLOCK_DATA.AIR.ID && !player.isInsideBlock(b)) {
                        placeInventoryItem(b, currentItem);
                    } else if(zAxis == 1 && b.getId() != currentItem.getId()) {
                        if(Handler.getWorld().getWorldBlocks()[b.getX() / Block.SIZE][b.getY() / Block.SIZE][0].getId() == Block.BLOCK_DATA.AIR.ID) {
                            placeInventoryItem(b, currentItem);
                        }
                    }
                }
            }
        }
    }

    private void placeInventoryItem(Block b, InventoryItem item) {
        item.removeAmount(1);
        world.getWorldBlocks()[b.getX() / Block.SIZE][b.getY() / Block.SIZE][zAxis] = new Block(item.getId(), b.getX(), b.getY(), zAxis, item.getTexture());
    }

    private void breakBlock(Block b) {
        if(b.isSelected() && b.isBreakable() && !player.getInventory().isMouseInsideInventory()) {
            if(Handler.getMouseManager().isLeftClicked() && b.getId() != Block.BLOCK_DATA.AIR.ID) {
                if(zAxis == 1 && b.getY() >= 20 * Block.SIZE) {
                    return;
                } else {
                    b.takeDamageFrom(player);
                    if(b.getHealth() <= 0) {
                        b.dropItem(world);
                        world.getWorldBlocks()[b.getX() / Block.SIZE][b.getY() / Block.SIZE][zAxis] = new AirBlock(b.getX(), b.getY(), zAxis);
                    }
                }
            }
        }
    }

    private void repairBlock(Block b) {
        if((!b.isSelected() || !Handler.getMouseManager().isLeftClicked()) && b.getHealth() < b.getMaxHealth()) {
            b.setHealth(b.getMaxHealth());
        }
    }

    public int getzAxis() {
        return zAxis;
    }
}
