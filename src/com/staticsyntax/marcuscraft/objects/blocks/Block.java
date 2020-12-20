package com.staticsyntax.marcuscraft.objects.blocks;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.marcuscraft.objects.entites.grounditem.GroundItem;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.graphics.images.ImageReviser;
import com.staticsyntax.marcuscraft.objects.entites.mobs.Mob;
import com.staticsyntax.marcuscraft.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {

    public static final int SIZE = 52;
    private int id;
    private int x, y, z;
    private BufferedImage texture;
    private boolean collidable = true, breakable = true;
    private boolean selected;
    private int health;

    public enum BLOCK_DATA {

        AIR(0,1),
        DIRT(1,40),
        GRASS(2,40),
        STONE(3,80),
        COBBLESTONE(4, 80),
        OAK_WOOD(5, 60),
        OAK_LEAVES(6, 20),
        BEDROCK(7, 999);

        public final int ID, MAX_HEALTH;

        BLOCK_DATA(int id, int maxHealth) {
            this.ID = id;
            this.MAX_HEALTH = maxHealth;
        }

        public static int returnMaxHealthForId(int id) {
            for(BLOCK_DATA b : BLOCK_DATA.values()) {
                if(b.ID == id) {
                    return b.MAX_HEALTH;
                }
            }
            return 0;
        }
    }

    public Block(int id, int x, int y, int z, BufferedImage texture) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        if(z == 0) {
            this.texture = texture;
        } else if(z == 1 && id != BLOCK_DATA.AIR.ID) {
            this.texture = ImageReviser.shade(texture, 0.5f);
        }
        if(id == BLOCK_DATA.AIR.ID) {
            collidable = false;
        }
        if(id == BLOCK_DATA.BEDROCK.ID) {
            breakable = false;
        }
        this.health = getMaxHealth();
    }

    public void update() {
        checkMousePosition();
    }

    public void render(Graphics g) {
        g.drawImage(texture, x - Handler.getCamera().getxOffSet(), y - Handler.getCamera().getyOffSet(), SIZE, SIZE, null);
        drawDamage(g);
        drawSelected(g);
    }

    private void checkMousePosition() {
        int mouseX = Handler.getMouseManager().getX();
        int mouseY = Handler.getMouseManager().getY();
        int xOffSet = Handler.getCamera().getxOffSet();
        int yOffSet = Handler.getCamera().getyOffSet();
        if(mouseX > x - xOffSet && mouseX < x - xOffSet + Block.SIZE && mouseY > y - yOffSet && mouseY < y - yOffSet + Block.SIZE) {
            selected = true;
        } else {
            selected = false;
        }
    }

    private void drawSelected(Graphics g) {
        if(selected) {
            if(Handler.getWorld().getWorldEditor().getzAxis() == 0) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.GRAY);
            }
            g.drawRect(x - Handler.getCamera().getxOffSet(), y - Handler.getCamera().getyOffSet(), SIZE - 1, SIZE - 1);
        }
    }

    private void drawDamage(Graphics g) {
        if(health < getMaxHealth()) {
            float damagePercent = (1.0f - ((float)health / (float)getMaxHealth()));
            int damageIndex = (int)Math.floor(damagePercent * 10);
            if(damageIndex > Assets.blockBreaking.length - 1) {
                damageIndex = Assets.blockBreaking.length - 1;
            }
            g.drawImage(Assets.blockBreaking[damageIndex], x - Handler.getCamera().getxOffSet(), y - Handler.getCamera().getyOffSet(), Block.SIZE, Block.SIZE, null);
        }
    }

    public void takeDamageFrom(Mob mob) {
        health -= mob.getBlockDamage();
    }

    public void dropItem(World world) {
        if(z == 1) {
            texture = ImageReviser.shade(texture, 2.0f);
        }
        GroundItem item = new GroundItem(id,x + SIZE / 2 - GroundItem.SIZE / 2, y + SIZE / 2 - GroundItem.SIZE / 2, texture, 1);
        switch (id) {
            case 2: //Grass to Dirt
                item.setId(1);
                item.setTexture(Assets.dirt);
                break;
            case 3: //Stone to Cobblestone
                item.setId(4);
                item.setTexture(Assets.cobblestone);
                break;
            default:
        }
        world.getEntities().add(item);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return BLOCK_DATA.returnMaxHealthForId(id);
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
