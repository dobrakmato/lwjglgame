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
package eu.matejkormuth.game.shared.physics.impl;

import eu.matejkormuth.game.shared.physics.PointMass;

import eu.matejkormuth.game.shared.math.Vector3f;

public class PointMassImpl implements PointMass {

    private Vector3f pos;
    private Vector3f vel;
    private float weight;
    private final Vector3f gravity;

    public PointMassImpl(Vector3f gravity) {
        this.gravity = gravity;
    }

    @Override
    public void applyForce(Vector3f force) {
        vel.x += force.x / weight;
        vel.y += force.y / weight;
        vel.z += force.z / weight;
    }

    @Override
    public void update(float delta) {
        // Apply gravity.
        vel.x += gravity.x * delta;
        vel.y += gravity.y * delta;
        vel.z += gravity.z * delta;
        // Move point by its velocity.
        pos.x += vel.x;
        pos.y += pos.y;
        pos.z += pos.z;
    }

    @Override
    public Vector3f getMomentum() {
        return new Vector3f(vel.x * weight, vel.y * weight, vel.z * weight);
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public Vector3f getPosition() {
        return pos;
    }

    @Override
    public Vector3f getVelocity() {
        return vel;
    }

    @Override
    public void setPosition(Vector3f position) {
        this.pos = position;
    }

    @Override
    public void setVelocity(Vector3f velocity) {
        this.vel = velocity;
    }

}
