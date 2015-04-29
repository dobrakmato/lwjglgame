/**
 * server - Multiplayer Java game engine.
 * Copyright (C) 2015 Matej Kormuth <http://www.github.com/dobrakmato>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.matejkormuth.game.server.gameobjects;

import eu.matejkormuth.game.shared.gameobjects.Entity;
import eu.matejkormuth.game.shared.gameobjects.World;
import eu.matejkormuth.game.shared.physics.DynamicsWorld;
import eu.matejkormuth.game.shared.physics.impl.DynamicsWorldImpl;
import gnu.trove.map.hash.TShortObjectHashMap;

import java.util.Collection;

import eu.matejkormuth.game.shared.math.Vector3f;

public class SWorld implements World, DynamicsWorld {

    // Physics implementation.
    private DynamicsWorldImpl physicsWorld;
    // Entities.
    private TShortObjectHashMap<Entity> entites;

    public SWorld() {
        this.physicsWorld = new DynamicsWorldImpl();
        this.entites = new TShortObjectHashMap<>();
    }

    @Override
    public void update(float delta) {
        physicsWorld.update(delta);
    }

    @Override
    public Vector3f getGravity() {
        return physicsWorld.getGravity();
    }

    @Override
    public void setGravity(Vector3f gravity) {
        physicsWorld.setGravity(gravity);
    }

    @Override
    public Collection<Entity> getEntities() {
        return this.entites.valueCollection();
    }

    @Override
    public Entity getEntity(short id) {
        return this.entites.get(id);
    }

    @Override
    public short addEntity(Entity entity) {
        short key = (short) this.entites.size();
        if (key >= Short.MAX_VALUE) {
            throw new IllegalStateException("Can't add another entity to world. Maximum amount: " + Short.MAX_VALUE);
        }
        this.entites.put(key, entity);
        return key;
    }

    @Override
    public void removeEntity(short key) {
        this.entites.remove(key);
    }

}
