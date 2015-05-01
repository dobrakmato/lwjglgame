/**
 * shared - Multiplayer Java game engine.
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.game.shared;

import eu.matejkormuth.game.shared.gameobjects.World;

import java.util.Map;

public abstract class GameObject implements Updatable {
    private Map<Class<? extends GameComponent>, GameComponent> components;
    private World world;
    private Game game;

    public GameObject(Game game, World world) {
        this.game = game;
        this.world = world;
    }

    public void add(GameComponent component) {
        component.world = this.world;
        component.game = this.game;
        component.parent = this;
        components.put(component.getClass(), component);
        component.initialize();
    }

    public <T> T get(Class<? extends GameComponent> component) {
        Class<?> clazz = component.getClass();
        if (components.containsKey(clazz)) {
            return cast(components.get(clazz));
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cast(Object obj) {
        return (T) obj;
    }

    public void initialize() {
        for (GameComponent component : components.values()) {
            component.initialize();
        }
    }

    @Override
    public void update(float delta) {
        for (GameComponent component : components.values()) {
            component.update(delta);
        }
    }

    public void render() {
        for (GameComponent component : components.values()) {
            component.render();
        }
    }
}
