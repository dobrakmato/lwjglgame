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
