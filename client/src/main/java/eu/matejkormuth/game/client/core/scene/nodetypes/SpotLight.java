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
package eu.matejkormuth.game.client.core.scene.nodetypes;

import eu.matejkormuth.game.client.core.scene.Node;
import eu.matejkormuth.game.client.core.scene.Property;
import eu.matejkormuth.game.client.gl.IProgram;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.client.gl.pipelines.forward.PForwardSpot;
import eu.matejkormuth.game.shared.math.Color3f;

public class SpotLight extends Node implements ForwardLightSource {

    private static PForwardSpot forwardProgram = new PForwardSpot();

    public SpotLight() {
    }

    public SpotLight(Attenuation attenuation, Color3f color, float intensity, float cutoff) {
        this.attenuation = attenuation;
        this.color = color;
        this.intensity = intensity;
        this.cutoff = cutoff;
    }

    @Property
    public Attenuation attenuation = new Attenuation(0, 0, 1);
    @Property
    public Color3f color = new Color3f(1, 1, 1);
    @Property
    public float intensity = 1f;
    @Property
    public float cutoff = 0.7f;

    @Override
    public IProgram getForwardProgram() {
        return forwardProgram;
    }

    @Override
    public void setLightUniforms() {
        forwardProgram.setPosition(position);
        forwardProgram.setDirection(rotation);
        forwardProgram.setCutoff(cutoff);
        forwardProgram.setColor(color);
        forwardProgram.setIntensity(intensity);
        // Beware! This setter uses color and intensity, so It has to be last.
        forwardProgram.setAttenuation(attenuation);
    }
}
