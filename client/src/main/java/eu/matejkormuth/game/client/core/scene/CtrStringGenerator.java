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
package eu.matejkormuth.game.client.core.scene;

import eu.matejkormuth.game.client.gl.Material;
import eu.matejkormuth.game.client.gl.Mesh;
import eu.matejkormuth.game.client.gl.lighting.Attenuation;
import eu.matejkormuth.game.shared.math.Color3f;
import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class CtrStringGenerator {
    public String createConstructor(Object obj) {
        Class<?> c = obj.getClass();
        if (c == boolean.class || c == Boolean.class) {
            return Boolean.toString((boolean) obj);
        } else if (c == byte.class || c == Byte.class) {
            return Byte.toString((byte) obj);
        } else if (c == short.class || c == Short.class) {
            return Short.toString((short) obj);
        } else if (c == int.class || c == Integer.class) {
            return Integer.toString((int) obj);
        } else if (c == long.class || c == Long.class) {
            return Long.toString((long) obj) + "L";
        } else if (c == float.class || c == Float.class) {
            return Float.toString((float) obj) + "f";
        } else if (c == double.class || c == Double.class) {
            return Double.toString((double) obj);
        } else if (c == String.class) {
            return "\"" + obj + "\"";
        } else if (c == Color3f.class) {
            Color3f v = (Color3f) obj;
            return "new Color3f(" + v.x + ", " + v.y + ", " + v.z + ")";
        } else if (c == Vector3f.class) {
            Vector3f v = (Vector3f) obj;
            return "new Vector3f(" + v.x + ", " + v.y + ", " + v.z + ")";
        } else if (c == Vector2f.class) {
            Vector2f v = (Vector2f) obj;
            return "new Vector2f(" + v.x + ", " + v.y + ")";
        } else if (c == Attenuation.class) {
            Attenuation a = (Attenuation) obj;
            return "new Attenuation(" + a.getConstant() + ", " + a.getLinear() + ", " + a.getQuadratic() + ")";
        } else if (c == Material.class) {
            return "\"$MATERIAL_RESOURCE_NOT_FOUND\"";
        } else if (c == Mesh.class) {
            return "\"$MESH_RESOURCE_NOT_FOUND\"";
        } else {
            throw new RuntimeException("Can't provide initialization code for type " + c.getName());
        }
    }
}
