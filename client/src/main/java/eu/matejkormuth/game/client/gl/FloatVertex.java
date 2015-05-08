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


import eu.matejkormuth.game.shared.math.Vector2f;
import eu.matejkormuth.game.shared.math.Vector3f;

public class FloatVertex {

    // Size of this vertex is 11 floating point numbers.
    public static final int SIZE = 11;
    public static final int BYTES = 4; // Float has 4 bytes.
    
    // 3 floats - POS
    // 2 floats - TEXCOORDS
    // 3 floats - NORMAL
    // 3 floats - TANGENT

    private float[] data = new float[SIZE];

    public FloatVertex(Vector3f pos) {
        this(pos, new Vector2f());
    }

    public FloatVertex(Vector3f pos, Vector2f texCoords) {
        this.setPosition(pos);
        this.setTexCoords(texCoords);
    }
    
    public FloatVertex(Vector3f pos, Vector2f texCoords, Vector3f normal) {
        this.setPosition(pos);
        this.setTexCoords(texCoords);
        this.setNormal(normal);
    }

    public void setPosition(Vector3f pos) {
        this.data[0] = pos.x;
        this.data[1] = pos.y;
        this.data[2] = pos.z;
    }

    private void setTexCoords(Vector2f texCoords) {
        this.data[3] = texCoords.x;
        this.data[4] = texCoords.y;
    }
    
    public void setNormal(Vector3f normal) {
        this.data[5] = normal.x;
        this.data[6] = normal.y;
        this.data[7] = normal.z;
    }
    
    public void setTangent(Vector3f tangent) {
        this.data[8] = tangent.x;
        this.data[9] = tangent.y;
        this.data[10] = tangent.z;
    }

    public float[] getData() {
        return data;
    }

    public Vector2f getTexCoords() {
        return new Vector2f(this.data[3], this.data[4]);
    }
    
    public Vector3f getPos() {
        return new Vector3f(this.data[0], this.data[1], this.data[2]);
    }

    public Vector3f getNormal() {
        return new Vector3f(this.data[5], this.data[6], this.data[7]);
    }

    public Vector3f getTangent() {
        return new Vector3f(this.data[8], this.data[9], this.data[10]);
    }
}
