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

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.gl.Camera;
import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.Texture2D;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.client.gl.lighting.BaseLight;
import eu.matejkormuth.game.client.gl.lighting.DirectionalLight;
import eu.matejkormuth.game.client.gl.lighting.PointLight;
import eu.matejkormuth.game.client.gl.lighting.SpotLight;
import eu.matejkormuth.game.client.gl.lighting.shaders.PhongShader;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class Scene {

    private PhongShader phong = new PhongShader();

    // private Mesh triangle = Content.importObj("models", "box.obj");

    private FloatVertex[] vertices = new FloatVertex[] { //
    new FloatVertex(new Vector3f(-10, 0, -10), new Vector2f(0, 0)),// 0
            new FloatVertex(new Vector3f(-10, 0, 10), new Vector2f(0, 1)), // 1
            new FloatVertex(new Vector3f(10, 0, 10), new Vector2f(1, 1)), // 2
            new FloatVertex(new Vector3f(10, 0, -10), new Vector2f(1, 0)),// 3

    };
    private int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
    private Mesh triangle = new Mesh(vertices, indices, true);

    private Texture2D texture = Content.importTexture2D("textures", "texture.png");
    private Texture2D normalMap = Content.importTexture2D("textures", "texture_n.png");
    private Texture2D specularMap = Content.importTexture2D("textures", "texture_s.png");
    private Material material = new Material(texture, normalMap, specularMap, new Vector3f(1, 1, 1), 1, 32);
    private Camera camera = new Camera();

    private DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0.7f, 0.7f, 0.5f),
            0.1f), new Vector3f(1, 1, 1).normalize());
    private PointLight pointLight1 = new PointLight(new BaseLight(new Vector3f(1, .25f, 0), 0.1f), new Attenuation(0,
            0, 1), new Vector3f(0, .8f, 0));
    private PointLight pointLight2 = new PointLight(new BaseLight(new Vector3f(0, .25f, 1), 0.1f), new Attenuation(0,
            0, 1), new Vector3f(0, .8f, 5));
    private PointLight pointLight3 = new PointLight(new BaseLight(new Vector3f(.25f, 1f, .1f), 0.4f), new Attenuation(1,
            .1f, .08f), new Vector3f(0, 2f, -4));
    private SpotLight flashLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, .5f, 1), 1f),
            new Attenuation(0, 0, 1), new Vector3f(0, .8f, 6)), new Vector3f(1), 0.7f);

    private float time;

    public void init() {
        camera.getPos().y += 2;
    }

    public void render() {
        time += 0.012f;

        float rx = 0;
        float ry = 0;// (float) Math.sin(time) * 180;
        float rz = 0;

        float sx = 1f;
        float sy = 1f;
        float sz = 1f;

        float tx = 0;
        float ty = 0;
        float tz = 5;

        float zNear = 0.1f;
        float zFar = 1000;
        float width = Application.get().getWindow().getWidth();
        float height = Application.get().getWindow().getHeight();
        float fov = 70f;

        Matrix4f translation = new Matrix4f().initTranslation(tx, ty, tz);
        Matrix4f rotation = new Matrix4f().initRotation(rx, ry, rz);
        Matrix4f scale = new Matrix4f().initScale(sx, sy, sz);

        Matrix4f transform = translation.multiply(rotation.multiply(scale));

        Matrix4f projection = new Matrix4f().initPerspective(fov, width, height, zNear, zFar);
        Matrix4f view = this.camera.getViewMatrix();

        phong.use();
        phong.setUniformi("sampler", 0);
        // phong.setUniformi("specularMap", 2);
        // phong.setUniformi("normalMap", 1);

        pointLight1.getPosition().x = (float) (Math.sin(time * 2) * 5);
        pointLight2.getPosition().x = (float) (Math.cos(time * 2) * 5);

        flashLight.getPointLight().setPosition(camera.getPos());
        flashLight.setDirection(camera.getForward());
        
        phong.setPointLight(0, pointLight1);
        phong.setPointLight(1, pointLight2);
        phong.setPointLight(2, pointLight3);
        phong.setSpotLight(0, flashLight);

        phong.setEyePosition(camera.getPos());
        phong.setAmbientLight(new Vector3f(0.1f));
        phong.setDirectionalLight(directionalLight);
        phong.setModel(transform);
        phong.setView(view);
        phong.setProjection(projection);
        phong.setMaterial(material);
        texture.bind(0);
        normalMap.bind(1);
        specularMap.bind(2);
        triangle.draw();
    }

    public void update() {
        camera.doInput();
    }
}
