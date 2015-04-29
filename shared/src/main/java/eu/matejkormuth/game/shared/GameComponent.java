package eu.matejkormuth.game.shared;

import eu.matejkormuth.game.shared.gameobjects.World;

public abstract class GameComponent implements Updatable {
    protected Game game;
    protected World world;

    public Game getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }
}
