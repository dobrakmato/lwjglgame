/**
 * client - Multiplayer Java game engine. Copyright (C) 2015 Matej Kormuth
 * <http://www.github.com/dobrakmato>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
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
