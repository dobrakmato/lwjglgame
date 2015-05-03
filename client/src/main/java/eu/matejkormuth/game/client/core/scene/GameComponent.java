package eu.matejkormuth.game.client.core.scene;

import eu.matejkormuth.game.shared.Updatable;

public abstract class GameComponent implements Updatable {
    protected SceneNode parent;

    public abstract void render();
}
