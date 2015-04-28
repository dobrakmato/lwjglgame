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
