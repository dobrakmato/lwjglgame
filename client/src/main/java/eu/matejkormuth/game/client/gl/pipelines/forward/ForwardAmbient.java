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
package eu.matejkormuth.game.client.gl.pipelines.forward;

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.gl.IProgram;
import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Program;
import eu.matejkormuth.game.client.gl.ShaderType;
import eu.matejkormuth.game.shared.math.Color3f;
import eu.matejkormuth.game.shared.math.Matrix4f;

public class ForwardAmbient extends Program implements IProgram {

    public ForwardAmbient() {
        super(Content.provideShader(ShaderType.VERTEX, "forward", "forward-ambient.vs"), Content.provideShader(
                ShaderType.FRAGMENT, "forward", "forward-ambient.fs"));
    }

    public void setAmbientColor(Color3f color) {
        this.setUniform("ambientColor", color.x, color.y, color.z);
    }

    @Override
    public void setModelMatrix(Matrix4f model) {
        this.setUniform("model", model);
    }
    
    @Override
    public void setViewMatrix(Matrix4f view) {
        this.setUniform("view", view);
    }
    
    @Override
    public void setProjectionMatrix(Matrix4f projection) {
        this.setUniform("projection", projection);
    }

    @Override
    public void setMaterial(Material material) {
        // This shader doesn't use this.
    }
}
