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
package eu.matejkormuth.game.client.core.scene.nodetypes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.BufferUtils;

import eu.matejkormuth.game.client.content.Resource;
import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.Property;
import eu.matejkormuth.game.client.gl.IProgram;
import eu.matejkormuth.game.client.gl.TextureCubeMap;
import eu.matejkormuth.game.client.gl.pipelines.SkyboxProgram;
import eu.matejkormuth.game.client.gl.pipelines.forward.PForwardDirectional;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Skybox extends Node {

    private static final FloatBuffer vertices = (FloatBuffer) BufferUtils.createFloatBuffer(24)
            .put(new float[] { 1, -1, -1, 1, -1, 1, -1, -1, 1, -1, -1, -1, 1, 1, -1, 1, 1, 1, -1, 1, 1, -1, 1, -1 })
            .flip();
    private static final IntBuffer indices = (IntBuffer) BufferUtils
            .createIntBuffer(36)
            .put(new int[] { 1, 2, 3, 7, 6, 5, 4, 5, 1, 5, 6, 2, 2, 6, 7, 0, 3, 7, 0, 1, 3, 4, 7, 5, 0, 4, 1, 1, 5, 2,
                    3, 2, 7, 4, 0, 7 }).flip();
    private static int vbo = -1;
    private static int vao = -1;
    private static int ebo = -1;
    private static final SkyboxProgram program = new SkyboxProgram();

    private Matrix4f positionMat = new Matrix4f().initIdentity();
    private Matrix4f rotationMat = new Matrix4f().initIdentity();
    private Matrix4f scaleMat = new Matrix4f().initIdentity();

    public Skybox(TextureCubeMap texture) {
        this.cubeMap = texture;
        scale = new Vector3f(150);
        position = new Vector3f(0, 25, 0);
    }

    @Property
    @Resource(TextureCubeMap.class)
    public Object cubeMap;

    @Property
    public boolean isStatic = false;

    @Override
    public void render(IProgram currentProgram) {

        if (!(currentProgram instanceof PForwardDirectional)) {
            return;
        }

        if (vbo == -1) {
            vbo = glGenBuffers();
            vao = glGenVertexArrays();
            ebo = glGenBuffers();
            glBindVertexArray(vao);

            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 3, 0);
        }

        // Skybox is rendered using it's own program.
        program.use();
        program.setModelMatrix(getTransformation());
        program.setViewMatrix(this.renderer.getCamera().getViewMatrix());
        program.setProjectionMatrix(this.renderer.getCamera().getProjectionMatrix());
        ((TextureCubeMap) cubeMap).bind(0);
        program.setCubeMap(0);

        glCullFace(GL_FRONT);
        // glDisable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ZERO);
        glDepthFunc(GL_LEQUAL);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);

        glDepthFunc(GL_EQUAL);
        // glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glCullFace(GL_BACK);

        // Use the previous program for next nodes.
        currentProgram.use();
    }

    @Override
    public Matrix4f getTransformation() {
        Vector3f pos = null;
        if (this.isStatic) {
            pos = this.renderer.getCamera().getPosition();
        } else {
            pos = this.getPosition();
        }

        Matrix4f.initTranslation(this.positionMat, pos.x, pos.y, pos.z);
        Matrix4f.initRotation(this.rotationMat, this.rotation.x, this.rotation.y, this.rotation.z);
        Matrix4f.initScale(this.scaleMat, this.scale.x, this.scale.y, this.scale.z);

        return positionMat.multiply(rotationMat.multiply(scaleMat));
    }
}
