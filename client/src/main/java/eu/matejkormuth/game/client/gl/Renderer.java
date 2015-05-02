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
package eu.matejkormuth.game.client.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.Scene;
import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.core.scene.SceneNode;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderer {

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    private static final FloatBuffer quadVertices = BufferUtils.createFloatBuffer(4 * 4).put(new float[] { -1, 1, 0, 1, //
            -1, -1, 0, 0, //
            1, -1, 1, 0, //
            1, 1, 1, 1 });
    private static final IntBuffer quadIndices = BufferUtils.createIntBuffer(6).put(new int[] { 0, 1, 2, 2, 3, 0 });
    private Program quadShader = new Program(Content.importShader(ShaderType.VERTEX, "shaders", "quad.vs"),
            Content.importShader(ShaderType.FRAGMENT, "shaders", "quad.fs"));
    private int quadVAO;
    private int quadVBO;
    private int quadEBO;

    private FrameBuffer buffer3D = new FrameBuffer(Display.getWidth(), Display.getHeight());

    // Currently rendered scene.
    private Scene scene;
    private SceneNode rootNode;

    public static void clear() {
        // TODO: Stencil buffer.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void init() {
        glClearColor(0, 0, 0, 0);

        // Enable face culling.
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        // Enable depth test.
        glEnable(GL_DEPTH_TEST);

        // Enable texturing.
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_DEPTH_CLAMP);

        // glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public Renderer() {
        // Output GL info to log.
        log.info("Initializing renderer...");
        log.info(" Vendor: {}", glGetString(GL_VENDOR));
        log.info(" Version: {}", glGetString(GL_VERSION));

        init();
        initScene();
        initQuadRender();
    }

    private void initQuadRender() {
        quadIndices.flip();
        quadVertices.flip();

        quadVAO = glGenVertexArrays();
        glBindVertexArray(quadVAO);
        quadVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, quadVBO);
        glBufferData(GL_ARRAY_BUFFER, quadVertices, GL_STATIC_DRAW);
        quadEBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quadEBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, quadIndices, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 4, 0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 4, Float.BYTES * 2);

        glBindVertexArray(0);
    }

    private void initScene() {
        scene = new Scene();
        scene.init();
        
    }

    public void render() {
        //buffer3D.bind();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        scene.render();
        //FrameBuffer.SCREEN.bind();
        //glClear(GL_COLOR_BUFFER_BIT);
        //this.quadRender();
    }

    private void quadRender() {
        quadShader.use();
        quadShader.setUniformi("sampler", 0);
        glBindVertexArray(quadVAO);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, buffer3D.getTextureId());
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void update() {
        scene.update();
    }
}
