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

import eu.matejkormuth.game.client.Application;
import eu.matejkormuth.game.client.Content;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class Scene {

    private Shader basicVertex = new Shader(ShaderType.VERTEX, Content.readText("shaders", "basic.vs"));
    private Shader basicFragment = new Shader(ShaderType.FRAGMENT, Content.readText("shaders", "basic.fs"));
    private Program basicShader = new Program(basicVertex, basicFragment);
    
    // private Mesh triangle = Content.loadObj("models", "box.obj");
    
    private FloatVertex[] vertices = new FloatVertex[] {
            new FloatVertex(new Vector3f(-1, -1, 0), new Vector2f(0, 0)),
            new FloatVertex(new Vector3f(0, 1, 0), new Vector2f(0.5f, 0)),
            new FloatVertex(new Vector3f(1, -1, 0), new Vector2f(1f, 0)),
            new FloatVertex(new Vector3f(0, -1, 1), new Vector2f(0, 0.5f))
    };
    private int[] indices = new int[] {
            3, 1, 0,
            2, 1, 3,
            0, 1, 2,
            0, 2, 3};
    private Mesh triangle = new Mesh(vertices, indices);
    
    private Texture2D texture = Content.importTexture2D("textures", "texture.png");
    private Camera camera = new Camera();
    
    private float time;
    
    public void render() {
        time += 0.012f;
        
        float rx = 0;
        float ry = (float) Math.sin(time) * 180;
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

        Matrix4f mvp = projection.multiply(view.multiply(transform));

        basicShader.use();
        basicShader.setUniform("transform", mvp);
        texture.bind(0);
        basicShader.setUniformi("sampler", 0);
        triangle.draw();
    }

    public void update() {
        camera.doInput();
    }
}
