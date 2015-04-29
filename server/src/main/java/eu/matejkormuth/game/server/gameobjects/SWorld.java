/**
 * server - Multiplayer Java game engine.
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
