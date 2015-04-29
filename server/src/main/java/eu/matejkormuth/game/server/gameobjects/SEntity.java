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
import eu.matejkormuth.game.shared.physics.PointMass;
import eu.matejkormuth.game.shared.physics.impl.PointMassImpl;

import eu.matejkormuth.game.shared.math.Vector3f;

public class SEntity implements Entity, PointMass {

    protected short id;
    protected World world;

    protected PointMassImpl physicsPointMass;

    public SEntity(short id, World world) {
        this.id = id;
        this.world = world;
        this.physicsPointMass = new PointMassImpl(world.getGravity());
    }

    @Override
    public void update(float delta) {
        // Update physics.
        this.physicsPointMass.update(delta);
    }

    @Override
    public short getId() {
        return this.id;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public void applyForce(Vector3f force) {
        physicsPointMass.applyForce(force);
    }

    @Override
    public Vector3f getPosition() {
        return physicsPointMass.getPosition();
    }

    @Override
    public Vector3f getVelocity() {
        return physicsPointMass.getVelocity();
    }

    @Override
    public void setPosition(Vector3f position) {
        physicsPointMass.setPosition(position);
    }

    @Override
    public void setVelocity(Vector3f velocity) {
        physicsPointMass.setVelocity(velocity);
    }

    @Override
    public float getWeight() {
        return physicsPointMass.getWeight();
    }

    @Override
    public Vector3f getMomentum() {
        return physicsPointMass.getMomentum();
    }
    
    

}
