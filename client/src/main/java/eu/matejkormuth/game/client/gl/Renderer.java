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

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.components.light.CirclePosComponent;
import eu.matejkormuth.game.client.core.scene.nodetypes.Camera;
import eu.matejkormuth.game.client.core.scene.nodetypes.DirectionalLight;
import eu.matejkormuth.game.client.core.scene.nodetypes.ForwardLightSource;
import eu.matejkormuth.game.client.core.scene.nodetypes.Model;
import eu.matejkormuth.game.client.core.scene.nodetypes.PointLight;
import eu.matejkormuth.game.client.core.scene.nodetypes.Skybox;
import eu.matejkormuth.game.client.core.scene.nodetypes.SpotLight;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.client.gl.pipelines.forward.PForwardAmbient;
import eu.matejkormuth.game.shared.math.Color3f;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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

    // private Font font = Content.provideFont("font");

    // One pass rendering shader.
    // private PhongShader phong;
    // RT for 3D scene.
    private FrameBuffer buffer3D;

    private Renderer2D guiRenderer;

    // Root node of scene.
    private Node rootNode;
    private ICamera camera;

    private Color3f ambientColor = Color3f.WHITE;

    // Forward rendering shaders.
    private PForwardAmbient forwardAmbient = new PForwardAmbient(); // Program

    // Programs use in frame.
    private TIntList usedPrograms = new TIntArrayList();
    private List<ForwardLightSource> lights = new ArrayList<>();
    // Amount of rendered frames.
    private long renderedFrames = 0;

    public void load0() {
        Material basicMaterial = Content.provideMaterial("default.mat");

        FloatVertex[] vertices = new FloatVertex[] { //
        new FloatVertex(new Vector3f(-40, 0, -40), new Vector2f(0, 0)),// 0
                new FloatVertex(new Vector3f(-40, 0, 40), new Vector2f(0, 8)), // 1
                new FloatVertex(new Vector3f(40, 0, 40), new Vector2f(8, 8)), // 2
                new FloatVertex(new Vector3f(40, 0, -40), new Vector2f(8, 0)),// 3

        };
        int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
        Mesh planeMesh = new Mesh("plane", vertices, indices, true);
        Mesh boxMesh = Content.provideMesh("box.obj");
        TextureCubeMap skyboxTex = Content.provideTextureCubeMap("calm");

        rootNode = new Node();
        rootNode.setRenderer(this);
        rootNode.setRootNode(true);

        Skybox skybox = new Skybox(skyboxTex);
        rootNode.addChild(skybox);

        Model plane = new Model(basicMaterial, planeMesh);
        rootNode.position = new Vector3f(0, -20, 0);
        rootNode.addChild(plane);

        Model box = new Model(basicMaterial, boxMesh);
        box.position = new Vector3f(0, 1, 20);
        box.scale = new Vector3f(1);
        box.addComponent(new CirclePosComponent());
        rootNode.addChild(box);

        DirectionalLight dirLight = new DirectionalLight(Color3f.WHITE.darker(0.7f), 0.7f, new Vector3f(1));
        rootNode.addChild(dirLight);

        int k = 5;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                PointLight pointLight0 = new PointLight(new Attenuation(0, 0, 1), new Color3f(i / (float) k, 1f, j
                        / (float) k), 1f);
                pointLight0.position = new Vector3f(-20 + i * 4, 1, -20 + j * 4);
                pointLight0.addComponent(new CirclePosComponent());
                rootNode.addChild(pointLight0);
            }
        }

        SpotLight flashLight = new SpotLight(new Attenuation(1, .04f, .004f), Color3f.CYAN, 1, new Vector3f(0, -.5f,
                .75f), 0.7f);
        // flashLight.addComponent(new CameraComp());
        flashLight.position = new Vector3f(-30, 1, 5);
        flashLight.rotation = new Vector3f(0, -.5f, .75f);
        rootNode.addChild(flashLight);

        PointLight pointLight1 = new PointLight(new Attenuation(0, 0, 1), Color3f.BLUE, 1f);
        pointLight1.position = new Vector3f(0, 1, 10);
        rootNode.addChild(pointLight1);

        PointLight pointLight2 = new PointLight(new Attenuation(1f, .001f, .01f), Color3f.WHITE, 1f);
        pointLight2.position = new Vector3f(0, 5, 20);
        rootNode.addChild(pointLight2);

        // SpotLight spotLight = new SpotLight(new Attenuation(1, 0.4f, 0.04f),
        // new Color3f(0, .8f, 6), 1f, new Vector3f(
        // .5f), .7f);
        // rootNode.addChild(spotLight);

        Camera camera = new Camera();
        rootNode.addChild(camera);
        this.camera = camera;
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
        // glDepthFunc(GL_ALWAYS);

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
        initQuadRender();

        // SceneInitializer init = new
        // SceneInitializer(Content.provideScene("MyScene.groovy"));
        // this.rootNode = init.loadScene();
        // this.rootNode.setRootNode(true);
        // this.camera = init.getCamera();

        // System.out.println(new SceneGraphWriter().write(this.rootNode,
        // "exported0"));

        load0();
        load1();
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

    public void load1() {
        guiRenderer = new Renderer2D();

        forwardAmbient.use();
        forwardAmbient.setAmbientColor(ambientColor);
        forwardAmbient.setProjectionMatrix(this.camera.getProjectionMatrix());
    }

    public void render() {
        buffer3D.bind();

        this.lights.clear();
        List<ForwardLightSource> lights = rootNode.gatherLights(this.lights);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        forwardAmbient.use();
        forwardAmbient.setViewMatrix(this.camera.getViewMatrix());

        rootNode.render(forwardAmbient);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        Matrix4f projection = this.camera.getProjectionMatrix();
        Matrix4f view = this.camera.getViewMatrix();
        Vector3f eyePos = this.camera.getPosition();

        usedPrograms.clear();

        for (ForwardLightSource l : lights) {
            IProgram program = l.getForwardProgram();

            if (!program.isCurrent()) {
                program.use();
            }
            if (!usedPrograms.contains(program.getId())) {
                program.setProjectionMatrix(projection);
                program.setViewMatrix(view);
                program.setEyePos(eyePos);
            }

            l.setLightUniforms();
            
            rootNode.render(program);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);

        FrameBuffer.SCREEN.bind();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glDisable(GL_CULL_FACE);
        glDisable(GL_DEPTH_TEST);

        this.quadRender();
        Program.unbind();
        this.renderGUI();
        renderedFrames++;
    }

    private void renderGUI() {
        guiRenderer.fillRectangle(new Vector2f(0.9f, 0.9f), new Vector2f(0.1f, .1f), Color3f.YELLOW);
        // this.font.renderText("its working", 16, 16);
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
        rootNode.update(0);
    }
    
    public long getRenderedFrames() {
        return renderedFrames;
    }

    public ICamera getCamera() {
        return this.camera;
    }
}
