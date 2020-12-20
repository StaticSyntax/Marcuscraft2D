package com.staticsyntax;

import com.staticsyntax.marcuscraft.camera.Camera;
import com.staticsyntax.marcuscraft.input.KeyManager;
import com.staticsyntax.marcuscraft.input.MouseManager;
import com.staticsyntax.marcuscraft.world.World;

public class Handler {

    private static Game game;
    private static KeyManager keyManager;
    private static MouseManager mouseManager;
    private static World world;
    private static int blocksRendered = 0;
    private static Camera camera;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        Handler.game = game;
    }

    public static KeyManager getKeyManager() {
        return keyManager;
    }

    public static void setKeyManager(KeyManager keyManager) {
        Handler.keyManager = keyManager;
    }

    public static MouseManager getMouseManager() {
        return mouseManager;
    }

    public static void setMouseManager(MouseManager mouseManager) {
        Handler.mouseManager = mouseManager;
    }

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        Handler.world = world;
    }

    public static int getBlocksRendered() {
        return blocksRendered;
    }

    public static void setBlocksRendered(int blocksRendered) {
        Handler.blocksRendered = blocksRendered;
    }

    public static Camera getCamera() {
        return camera;
    }

    public static void setCamera(Camera camera) {
        Handler.camera = camera;
    }
}
