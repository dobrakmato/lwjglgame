/**
 * shared - Multiplayer Java game engine.
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
package eu.matejkormuth.game.shared.physics.impl;

import eu.matejkormuth.game.shared.physics.PointMass;

import javax.vecmath.Vector3f;

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
