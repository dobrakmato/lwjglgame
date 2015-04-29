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
package eu.matejkormuth.game.client;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.Program;
import eu.matejkormuth.game.client.gl.Shader;
import eu.matejkormuth.game.client.gl.ShaderType;
import eu.matejkormuth.game.shared.math.Matrix4f;

public class Renderer {

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    private Shader basicVertex = new Shader(ShaderType.VERTEX, Content.readText("shaders", "basic.vs"));
    private Shader basicFragment = new Shader(ShaderType.FRAGMENT, Content.readText("shaders", "basic.fs"));
    private Program basicShader = new Program(basicVertex, basicFragment);

    private Mesh triangle = Content.loadObj("models", "box.obj");

    private float time;

    public static void clear() {
        // TODO: Stencil buffer.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void init() {
        glClearColor(0.3f, 0.5f, 1f, 0);

        // Enable face culling.
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        // Enable depth test.
        glEnable(GL_DEPTH_TEST);

        // TODO: Depth clamp for later.

        glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public Renderer() {
        // Output GL info to log.
        log.info("Initializing renderer...");
        log.info(" Vendor: {}", glGetString(GL_VENDOR));
        log.info(" Version: {}", glGetString(GL_VERSION));

        init();
    }

    public void render() {
        clear();
        time += 0.013f;

        float rx = 0;
        float ry = (float) Math.sin(time) * 180;
        float rz = 0;

        float sx = 1;
        float sy = 1;
        float sz = 1;

        float tx = (float) Math.sin(time);
        float ty = 0;
        float tz = 0;

        float zNear = 0.1f;
        float zFar = 1000;
        float width = Application.get().getWindow().getWidth();
        float height = Application.get().getWindow().getHeight();
        float fov = 70f;

        Matrix4f translation = new Matrix4f().initTranslation(tx, ty, tz);
        Matrix4f rotation = new Matrix4f().initRotation(rx, ry, rz);
        Matrix4f scale = new Matrix4f().initScale(sx, sy, sz);

        Matrix4f transform = translation.multiply(rotation.multiply(scale));
        
        Matrix4f projection = new Matrix4f().initProjection(fov, width, height, zNear, zFar);

        Matrix4f mp = projection.multiply(transform);

        basicShader.use();
        basicShader.setUniform("transform", transform);
        triangle.draw();
    }
}
