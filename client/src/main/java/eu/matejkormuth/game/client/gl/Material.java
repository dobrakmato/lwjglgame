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

import eu.matejkormuth.game.shared.Disposable;

public class Material implements Disposable {
    private Texture2D diffuse;
    private Texture2D normalMap;
    private Texture2D specularMap;
    private float specularIntensity;
    private float specularPower;
    private String key;

    public Material(String key, Texture2D diffuse, Texture2D normalMap, Texture2D specularMap, float specularIntensity,
            float specularPower) {
        this.key = key;
        this.diffuse = diffuse;
        this.normalMap = normalMap;
        this.specularMap = specularMap;
        this.specularIntensity = specularIntensity;
        this.specularPower = specularPower;
    }

    public Texture2D getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Texture2D diffuse) {
        this.diffuse = diffuse;
    }

    public Texture2D getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture2D normalTexture) {
        this.normalMap = normalTexture;
    }

    public Texture2D getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Texture2D specularMap) {
        this.specularMap = specularMap;
    }

    public float getSpecularPower() {
        return specularPower;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }

    @Override
    public void dispose() {
        diffuse.dispose();
        specularMap.dispose();
        normalMap.dispose();
        
    }

    public String getKey() {
        return this.key;
    }

}
