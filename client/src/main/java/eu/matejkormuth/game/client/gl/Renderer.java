/**
 * client - Multiplayer Java game engine. Copyright (c) 2015, Matej Kormuth
 * <http://www.github.com/dobrakmato> All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.nodetypes.DirectionalLight;
import eu.matejkormuth.game.client.core.scene.nodetypes.Model;
import eu.matejkormuth.game.client.core.scene.nodetypes.PointLight;
import eu.matejkormuth.game.client.core.scene.nodetypes.SpotLight;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.shared.math.Color3f;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Renderer {

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    private static final FloatBuffer quadVertices = BufferUtils.createFloatBuffer(4 * 4).put(new float[] { -1, 1, 0, 1, //
            -1, -1, 0, 0, //
            1, -1, 1, 0, //
            1, 1, 1, 1 });
    private static final IntBuffer quadIndices = BufferUtils.createIntBuffer(6).put(new int[] { 0, 1, 2, 2, 3, 0 });
    private Program quadShader = new Program(Content.provideShader(ShaderType.VERTEX, "quad.vs"),
            Content.provideShader(ShaderType.FRAGMENT, "quad.fs"));
    private int quadVAO;
    private int quadVBO;
    private int quadEBO;

    private FrameBuffer buffer3D;

    // Currently rendered scene.
    private Scene scene;
    private Node rootNode;

    public void setupScene() {
        Texture2D texture = Content.provideTexture2D("texture.png");
        Texture2D normalMap = Content.provideTexture2D("texture_n.png");
        Texture2D specularMap = Content.provideTexture2D("texture_s.png");
        Material basicMaterial = new Material(texture, normalMap, specularMap, new Vector3f(1, 1, 1), 1, 32);

        FloatVertex[] vertices = new FloatVertex[] { //
        new FloatVertex(new Vector3f(-40, 0, -40), new Vector2f(0, 0)),// 0
                new FloatVertex(new Vector3f(-40, 0, 40), new Vector2f(0, 4)), // 1
                new FloatVertex(new Vector3f(40, 0, 40), new Vector2f(4, 4)), // 2
                new FloatVertex(new Vector3f(40, 0, -40), new Vector2f(4, 0)),// 3

        };
        int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
        Mesh planeMesh = new Mesh(vertices, indices, true);
        Mesh boxMesh = Content.provideMesh("box.obj");

        rootNode = new Node();
        rootNode.setRootNode(true);

        Model plane = new Model(basicMaterial, planeMesh);
        rootNode.addChild(plane);

        Model box = new Model(basicMaterial, boxMesh);
        box.position = new Vector3f(0, 1, 20);
        rootNode.addChild(box);

        DirectionalLight dirLight = new DirectionalLight(Color3f.YELLOW, 0.75f, new Vector3f(0.5f));
        rootNode.addChild(dirLight);
        
        PointLight pointLight0 = new PointLight(new Attenuation(0, 0, 1), Color3f.RED, 1f);
        rootNode.addChild(pointLight0);
        
        PointLight pointLight1 = new PointLight(new Attenuation(0, 0, 1), Color3f.BLUE, 1f);
        rootNode.addChild(pointLight1);
        
        PointLight pointLight2 = new PointLight(new Attenuation(1f, .001f, .01f), Color3f.GREEN, 1f);
        pointLight2.position = new Vector3f(0, 5, 20);
        rootNode.addChild(pointLight2);
        
        SpotLight spotLight = new SpotLight(new Attenuation(1, 0.4f, 0.04f), new Vector3f(0, .8f, 6), 1f, new Vector3f(
                .5f), .7f);
        rootNode.addChild(spotLight);
    }

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
        // initGUI();
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

        buffer3D = new FrameBuffer(Display.getWidth(), Display.getHeight(), true);
    }

    private void initScene() {
        scene = new Scene();
        scene.init();

    }

    public void render() {
        buffer3D.bind();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        scene.render();
        FrameBuffer.SCREEN.bind();
        // glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);
        this.quadRender();
        Program.unbind();
        this.renderGUI();
    }

    private void renderGUI() {

    }

    private void quadRender() {
        glBindVertexArray(quadVAO);
        quadShader.use();
        quadShader.setUniformi("renderTex", 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, buffer3D.getTextureId());
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void update() {
        scene.update();
    }
}
