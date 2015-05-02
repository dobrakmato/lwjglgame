package eu.matejkormuth.game.client.content;

import java.nio.file.Path;

public abstract class ContentLoader<T> {
    public abstract T load(Path path);
}
