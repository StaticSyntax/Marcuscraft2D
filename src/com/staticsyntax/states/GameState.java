package com.staticsyntax.states;

import com.staticsyntax.Handler;
import com.staticsyntax.marcuscraft.world.World;

import java.awt.*;

public class GameState extends State {

    private World world;

    public GameState() {
        newWorld();
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }

    private void newWorld() {
        world = new World();
        Handler.setWorld(world);
    }
}
