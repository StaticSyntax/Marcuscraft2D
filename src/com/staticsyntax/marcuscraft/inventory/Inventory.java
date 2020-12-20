package com.staticsyntax.marcuscraft.inventory;

import com.staticsyntax.marcuscraft.graphics.assets.Assets;
import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.objects.entites.grounditem.GroundItem;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Inventory {

    private boolean open, mouseInsideInventory;
    private int invWidth, invHeight, barWidth, barHeight;
    private final int firstSlotX = 30, firstSlotY = 362, spaceBetweenSlots = 45;
    private InventoryItem[] items = new InventoryItem[36];
    private InventoryItem mouseItem;
    private int selectedItemSlot, hoveredItemSlot = -1;

    public Inventory() {
        invWidth = 3 + Assets.inventory.getWidth() / 2;
        invHeight = Assets.inventory.getHeight() / 2;
        barWidth = Assets.inventoryBar.getWidth() / 2;
        barHeight = Assets.inventoryBar.getHeight() / 2;
    }

    public void update() {
        openCloseCheck();
        mouseInsideInventoryCheck();
        selectedItemSlotCheck();
        mouseHoveredItemSlotCheck();
        mouseClickedItemSlotCheck();
        removeEmptyItemSlots();
    }

    public void render(Graphics g) {
        g.drawImage(Assets.inventoryBar, Handler.getGame().getWidth() / 2 - barWidth / 2,
                Handler.getGame().getHeight() - barHeight - 10,
                barWidth, barHeight, null);
        renderInventoryBarItems(g);
        if(open) {
            g.drawImage(Assets.inventory, 10, 10, invWidth, invHeight, null);
            renderInventoryItems(g);
        }
        renderMouseItem(g);
    }

    private void renderMouseItem(Graphics g) {
        if(mouseItem == null) {
            return;
        }
        mouseItem.render(g, Handler.getMouseManager().getX(), Handler.getMouseManager().getY(), 40);
    }

    private void renderInventoryBarItems(Graphics g) {
        for(int x = 0; x < 9; x++) {
            if(items[x] != null) {
                items[x].render(g, 439 + x * 45, Handler.getGame().getHeight() - barHeight - 10, 45);
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(Handler.getGame().getWidth() / 2 - barWidth / 2, Handler.getGame().getHeight() - barHeight - 10, barWidth, barHeight);
        g.setColor(Color.YELLOW);
        g.drawRect(439 + selectedItemSlot * 45, Handler.getGame().getHeight() - barHeight - 10, 45, 45);
    }

    private void renderInventoryItems(Graphics g) {
        int i = 0;
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 9; x++) {
                if(items[i] != null) {
                    if(i < 9) {
                        items[i].render(g, firstSlotX + x * spaceBetweenSlots, firstSlotY - y * spaceBetweenSlots, 40);
                    } else {
                        items[i].render(g, firstSlotX + x * spaceBetweenSlots, firstSlotY - 9 - y * spaceBetweenSlots, 40);
                    }
                }
                g.setColor(Color.YELLOW);
                g.drawRect(firstSlotX + selectedItemSlot * spaceBetweenSlots, firstSlotY, 40, 40);
                i++;
            }
        }
    }

    private void openCloseCheck() {
        if(Handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
            open = !open;
        }
    }

    private void mouseInsideInventoryCheck() {
        int mouseX = Handler.getMouseManager().getX();
        int mouseY = Handler.getMouseManager().getY();
        if(mouseX >= 10 && mouseX <= 10 + invWidth && mouseY >= 10 && mouseY <= 10 + invHeight) {
            mouseInsideInventory = open;
        } else {
            mouseInsideInventory = false;
        }
    }

    private void selectedItemSlotCheck() {
        for(int k = 0; k < Handler.getKeyManager().numKeys.length; k++) {
            if(Handler.getKeyManager().numKeys[k]) {
                selectedItemSlot = k;
            }
        }
    }

    private void mouseClickedItemSlotCheck() {
        if(!open) {
            return;
        }
        if(Handler.getMouseManager().justLeftClicked()) {
            if(hoveredItemSlot >= 0) {
                if(mouseItem == null && items[hoveredItemSlot] != null) {
                    mouseItem = items[hoveredItemSlot];
                    items[hoveredItemSlot] = null;
                } else if(mouseItem != null & items[hoveredItemSlot] != null){
                    if(mouseItem.getId() == items[hoveredItemSlot].getId() && items[hoveredItemSlot].getAmount() < 64) {
                        while(items[hoveredItemSlot].getAmount() < 64 && mouseItem.getAmount() > 0) {
                            items[hoveredItemSlot].setAmount(items[hoveredItemSlot].getAmount() + 1);
                            mouseItem.setAmount(mouseItem.getAmount() - 1);
                        }
                        if(mouseItem.getAmount() <= 0) {
                            mouseItem = null;
                        }
                    } else {
                        InventoryItem tempItem = mouseItem;
                        mouseItem = items[hoveredItemSlot];
                        items[hoveredItemSlot] = tempItem;
                    }
                } else if(mouseItem != null && items[hoveredItemSlot] == null) {
                    items[hoveredItemSlot] = mouseItem;
                    mouseItem = null;
                }
            } else {
                if(mouseItem != null && !mouseInsideInventory) {
                    GroundItem groundItem = new GroundItem(mouseItem.getId(),
                            Handler.getMouseManager().getX() + Handler.getCamera().getxOffSet(),
                            Handler.getMouseManager().getY() + Handler.getCamera().getyOffSet(),
                            mouseItem.getTexture(), mouseItem.getAmount());
                    Handler.getWorld().getEntities().add(groundItem);
                    mouseItem = null;
                }
            }
        }
    }

    private void mouseHoveredItemSlotCheck() {
        hoveredItemSlot = -1;
        if(!open) {
            return;
        }
        int i = 0;
        Rectangle itemRect;
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 9; x++) {
                if(i < 9) {
                    itemRect = new Rectangle(firstSlotX + x * spaceBetweenSlots, firstSlotY - y * spaceBetweenSlots, 40, 40);
                } else {
                    itemRect = new Rectangle(firstSlotX + x * spaceBetweenSlots, firstSlotY - 9 - y * spaceBetweenSlots, 40, 40);
                }
                if(itemRect.contains(Handler.getMouseManager().getX(), Handler.getMouseManager().getY())) {
                    hoveredItemSlot = i;
                }
                i++;
            }
        }
    }

    private void removeEmptyItemSlots() {
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                if(items[i].getAmount() <= 0) {
                    items[i] = null;
                }
            }
        }
    }

    public boolean addItem(InventoryItem item) {
        if(containsStackToAppend(item)) {
            items[returnStackLocationToAppend(item)].setAmount(items[returnStackLocationToAppend(item)].getAmount() + item.getAmount());
            return true;
        } else if(containsEmptySlot()) {
            items[returnFirstEmptySlot()] = item;
            return true;
        } else {
            return false;
        }
    }

    private boolean containsStackToAppend(InventoryItem item) {
        for(InventoryItem inventoryItem : items) {
            if (inventoryItem != null) {
                if (inventoryItem.getId() == item.getId() && inventoryItem.getAmount() < 64) {
                    return true;
                }
            }
        }
        return false;
    }

    private int returnStackLocationToAppend(InventoryItem item) {
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                if(items[i].getId() == item.getId() && items[i].getAmount() < 64) {
                    return i;
                }
            }
        }
        return items.length;
    }

    private boolean containsEmptySlot() {
        for(InventoryItem item : items) {
            if(item == null) {
                return true;
            }
        }
        return false;
    }

    private int returnFirstEmptySlot() {
        for(int i = 0; i < items.length; i++) {
            if(items[i] == null) {
                return i;
            }
        }
        return items.length;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isMouseInsideInventory() {
        return mouseInsideInventory;
    }

    public InventoryItem[] getItems() {
        return items;
    }

    public int getSelectedItemSlot() {
        return selectedItemSlot;
    }
}
