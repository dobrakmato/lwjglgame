/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client.core.scene.nodetypes;

import org.lwjgl.opengl.Display;

import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.Property;
import eu.matejkormuth.game.client.gl.ICamera;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class Camera extends Node implements ICamera {

    @Property
    public float fov = 70f;
    @Property
    public float zNear = 0.1f;
    @Property
    public float zFar = 1000f;

    private eu.matejkormuth.game.client.gl.Camera internal;
    private Matrix4f projection = new Matrix4f().initPerspective(fov, Display.getWidth(), Display.getHeight(), zNear,
            zFar);

    // TODO: Properties.

    public Camera() {
        internal = new eu.matejkormuth.game.client.gl.Camera();
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    @Override
    public Matrix4f getViewMatrix() {
        return internal.getViewMatrix();
    }

    @Override
    public void update(float delta) {
        internal.doInput(delta);
    }

    @Override
    public Vector3f getRotation() {
        return this.internal.getForward();
    }
    
    @Override
    public Vector3f getPosition() {
        return internal.getPos();
    }
}
