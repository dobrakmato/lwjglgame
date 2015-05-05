package eu.matejkormuth.game.client.content;

import eu.matejkormuth.game.client.core.scene.Node;

import java.lang.reflect.Field;

public class ResourceInjectLocation {
    public Resource annotation;
    public Field location;
    public Node target;
    public String resourceKey;

    public ResourceInjectLocation(Field location, Node target) {
        if (!location.isAnnotationPresent(Resource.class)) {
            throw new RuntimeException("Provided field is not a @Resource field.");
        }

        this.annotation = location.getAnnotation(Resource.class);
        this.location = location;
        this.target = target;

        Object key;
        try {
            key = location.get(target);
            if (key instanceof String) {
                this.resourceKey = (String) key;
            } else {
                throw new RuntimeException("Provided field does not contains a resource loaction key.");
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void inject(Object obj) {
        if (obj.getClass() != annotation.value()) {
            throw new RuntimeException("Tried to inject worng resource type to " + this.target.toString()
                    + "! Expected type was: " + annotation.value().getSimpleName() + ". Provided type: "
                    + obj.getClass().getSimpleName() + ".");
        }

        try {
            location.set(target, obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
