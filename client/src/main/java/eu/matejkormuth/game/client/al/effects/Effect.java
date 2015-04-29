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
package eu.matejkormuth.game.client.al.effects;

import static org.lwjgl.openal.EFX10.*;
import static org.lwjgl.openal.EFXUtil.*;
import eu.matejkormuth.game.shared.Disposable;

public abstract class Effect implements Disposable {
    private int type;
    private int effect;
    private int slot;

    public Effect(int type) {
        this.effect = alGenEffects();
        this.slot = alGenAuxiliaryEffectSlots();
        alEffecti(this.effect, AL_EFFECT_TYPE, type);
        alAuxiliaryEffectSloti(this.slot, AL_EFFECTSLOT_EFFECT, this.effect);
    }

    public boolean isSupported() {
        return isEffectSupported(this.type);
    }

    public int getSlotId() {
        return slot;
    }

    public int getEffectId() {
        return effect;
    }

    protected void setPropertyf(int property, float value) {
        alEffectf(effect, property, value);
    }

    protected void setPropertyi(int property, int value) {
        alEffecti(effect, property, value);
    }

    protected float getPropertyf(int property) {
        return alGetEffectf(effect, property);
    }

    protected int getPropertyi(int property) {
        return alGetEffecti(effect, property);
    }

    @Override
    public void dispose() {
        alDeleteAuxiliaryEffectSlots(this.slot);
        alDeleteEffects(this.effect);
    }
}
