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
package eu.matejkormuth.game.shared.physics;

import eu.matejkormuth.game.shared.math.Vector3f;

public interface PointMass extends Updatable {
    Vector3f getPosition();

    Vector3f getVelocity();

    void setPosition(Vector3f position);

    void setVelocity(Vector3f velocity);

    void applyForce(Vector3f force);

    float getWeight();
    
    Vector3f getMomentum();
}
