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
package eu.matejkormuth.game.client.gl.lighting.shaders;

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.gl.IProgram;
import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Program;
import eu.matejkormuth.game.client.gl.ShaderType;
import eu.matejkormuth.game.client.gl.Texture2D;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.client.gl.lighting.BaseLight;
import eu.matejkormuth.game.client.gl.lighting.DirectionalLight;
import eu.matejkormuth.game.client.gl.lighting.PointLight;
import eu.matejkormuth.game.client.gl.lighting.SpotLight;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class PhongShader extends Program implements IProgram {

    public static final int MAX_POINT_LIGHTS = 4;
    public static final int MAX_SPOT_LIGHTS = 4;

    public PhongShader() {
        super(Content.importShader(ShaderType.VERTEX, "phong.vs"), Content
                .importShader(ShaderType.FRAGMENT, "phong.fs"));
    }

    @Override
    public void setModelMatrix(Matrix4f transform) {
        setUniform("model", transform);
    }

    @Override
    public void setViewMatrix(Matrix4f transform) {
        setUniform("view", transform);
    }

    @Override
    public void setProjectionMatrix(Matrix4f projection) {
        setUniform("projection", projection);
    }

    @Override
    public void setMaterial(Material material) {
        if (material.getDiffuse() != null) {
            material.getDiffuse().bind(0);
        } else {
            Texture2D.unbind();
        }
        // setUniform("baseColor", material.getColor());
        setSpecularIntensity(material.getSpecularIntensity());
        setSpecularPower(material.getSpecularPower());
    }

    public void setPointLight(int index, PointLight pointLight) {
        setUniform("pointLights[" + index + "]", pointLight);
    }

    public void setSpotLight(int index, SpotLight spotLight) {
        setUniform("spotLights[" + index + "]", spotLight);
    }

    private void setUniform(String uniform, SpotLight spotLight) {
        setUniform(uniform + ".pointLight", spotLight.getPointLight());
        setUniform(uniform + ".direction", spotLight.getDirection());
        setUniformf(uniform + ".cutoff", spotLight.getCutoff());
    }

    public void setAmbientLight(Vector3f light) {
        setUniform("ambientLight", light);
    }

    public void setDirectionalLight(DirectionalLight light) {
        setUniform("directionalLight", light);
    }

    public void setEyePosition(Vector3f eyePosition) {
        setUniform("eyePos", eyePosition);
    }

    private void setSpecularIntensity(float intensity) {
        setUniformf("specularIntensity", intensity);
    }

    private void setSpecularPower(float power) {
        setUniformf("specularPower", power);
    }

    private void setUniform(String uniform, PointLight light) {
        setUniform(uniform + ".base", light.getBase());
        setUniform(uniform + ".atten", light.getAtten());
        setUniform(uniform + ".position", light.getPosition());
        setUniformf(uniform + ".range", light.getRange());
    }

    private void setUniform(String uniform, Attenuation atten) {
        setUniformf(uniform + ".constant", atten.getConstant());
        setUniformf(uniform + ".linear", atten.getLinear());
        setUniformf(uniform + ".quadratic", atten.getQuadratic());
    }

    private void setUniform(String uniform, DirectionalLight light) {
        setUniform(uniform + ".base", light.getBase());
        setUniform(uniform + ".direction", light.getDirection());
    }

    private void setUniform(String uniform, BaseLight base) {
        setUniform(uniform + ".color", base.getColor());
        setUniformf(uniform + ".intensity", base.getIntensity());
    }

    @Override
    public void setEyePos(Vector3f eyePos) {
        // TODO Auto-generated method stub
        
    }
}
