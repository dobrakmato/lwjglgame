/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.gl.FloatVertex;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.Program;
import eu.matejkormuth.game.client.gl.Shader;
import eu.matejkormuth.game.client.gl.ShaderType;

import javax.vecmath.Vector3f;

public class Renderer {

    private static final Logger log = LoggerFactory.getLogger(Renderer.class);

    private Shader basicVertex = new Shader(ShaderType.VERTEX, Content.readText("shaders", "basic.vs"));
    private Shader basicFragment = new Shader(ShaderType.FRAGMENT, Content.readText("shaders", "basic.fs"));
    private Program basicShader = new Program(basicVertex, basicFragment);

    private Mesh triangle = new Mesh(new FloatVertex(new Vector3f(-1f, -1f, 0)),
            new FloatVertex(new Vector3f(0, 1f, 0)), new FloatVertex(new Vector3f(1f, -1f, 0)));

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
        time += 0.033f;

        basicShader.use();
        basicShader.setUniformf("uniformFloat", (float) Math.abs(Math.sin(time)));
        triangle.draw();
    }
}
