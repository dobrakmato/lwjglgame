/**
 * shared - Multiplayer Java game engine.
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
package eu.matejkormuth.game.shared.math;

import java.util.Arrays;

public class Matrix4f {
    public float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;

        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = 0;

        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = 0;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;

        m[1][0] = 0;
        m[1][1] = y;
        m[1][2] = 0;
        m[1][3] = 0;

        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = z;
        m[2][3] = 0;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }
    
    public static Matrix4f initScale(Matrix4f mat, float x, float y, float z) {
        mat.m[0][0] = x;
        mat.m[0][1] = 0;
        mat.m[0][2] = 0;
        mat.m[0][3] = 0;

        mat.m[1][0] = 0;
        mat.m[1][1] = y;
        mat.m[1][2] = 0;
        mat.m[1][3] = 0;

        mat.m[2][0] = 0;
        mat.m[2][1] = 0;
        mat.m[2][2] = z;
        mat.m[2][3] = 0;

        mat.m[3][0] = 0;
        mat.m[3][1] = 0;
        mat.m[3][2] = 0;
        mat.m[3][3] = 1;

        return mat;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1;
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = x;

        m[1][0] = 0;
        m[1][1] = 1;
        m[1][2] = 0;
        m[1][3] = y;

        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = 1;
        m[2][3] = z;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }
    
    public static Matrix4f initTranslation(Matrix4f mat, float x, float y, float z) {
        mat.m[0][0] = 1;
        mat.m[0][1] = 0;
        mat.m[0][2] = 0;
        mat.m[0][3] = x;

        mat.m[1][0] = 0;
        mat.m[1][1] = 1;
        mat.m[1][2] = 0;
        mat.m[1][3] = y;

        mat.m[2][0] = 0;
        mat.m[2][1] = 0;
        mat.m[2][2] = 1;
        mat.m[2][3] = z;

        mat.m[3][0] = 0;
        mat.m[3][1] = 0;
        mat.m[3][2] = 0;
        mat.m[3][3] = 1;

        return mat;
    }


    public Matrix4f initRotation(float degreesX, float degreesY, float degreesZ) {

        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rz.m[0][0] = FastMath.cos(degreesZ);
        rz.m[0][1] = -FastMath.sin(degreesZ);
        rz.m[0][2] = 0;
        rz.m[0][3] = 0;

        rz.m[1][0] = FastMath.sin(degreesZ);
        rz.m[1][1] = FastMath.cos(degreesZ);
        rz.m[1][2] = 0;
        rz.m[1][3] = 0;

        rz.m[2][0] = 0;
        rz.m[2][1] = 0;
        rz.m[2][2] = 1;
        rz.m[2][3] = 0;

        rz.m[3][0] = 0;
        rz.m[3][1] = 0;
        rz.m[3][2] = 0;
        rz.m[3][3] = 1;

        // ----------------------

        rx.m[0][0] = 1;
        rx.m[0][1] = 0;
        rx.m[0][2] = 0;
        rx.m[0][3] = 0;

        rx.m[1][0] = 0;
        rx.m[1][1] = FastMath.cos(degreesX);
        rx.m[1][2] = -FastMath.sin(degreesX);
        rx.m[1][3] = 0;

        rx.m[2][0] = 0;
        rx.m[2][1] = FastMath.sin(degreesX);
        rx.m[2][2] = FastMath.cos(degreesX);
        rx.m[2][3] = 0;

        rx.m[3][0] = 0;
        rx.m[3][1] = 0;
        rx.m[3][2] = 0;
        rx.m[3][3] = 1;

        // ---------------------

        ry.m[0][0] = FastMath.cos(degreesY);
        ry.m[0][1] = 0;
        ry.m[0][2] = -FastMath.sin(degreesY);
        ry.m[0][3] = 0;

        ry.m[1][0] = 0;
        ry.m[1][1] = 1;
        ry.m[1][2] = 0;
        ry.m[1][3] = 0;

        ry.m[2][0] = FastMath.sin(degreesY);
        ry.m[2][1] = 0;
        ry.m[2][2] = FastMath.cos(degreesY);
        ry.m[2][3] = 0;

        ry.m[3][0] = 0;
        ry.m[3][1] = 0;
        ry.m[3][2] = 0;
        ry.m[3][3] = 1;

        m = rz.multiply(ry.multiply(rx)).m;
        return this;
    }
    
    public static Matrix4f initRotation(Matrix4f mat, float degreesX, float degreesY, float degreesZ) {

        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rz.m[0][0] = FastMath.cos(degreesZ);
        rz.m[0][1] = -FastMath.sin(degreesZ);
        rz.m[0][2] = 0;
        rz.m[0][3] = 0;

        rz.m[1][0] = FastMath.sin(degreesZ);
        rz.m[1][1] = FastMath.cos(degreesZ);
        rz.m[1][2] = 0;
        rz.m[1][3] = 0;

        rz.m[2][0] = 0;
        rz.m[2][1] = 0;
        rz.m[2][2] = 1;
        rz.m[2][3] = 0;

        rz.m[3][0] = 0;
        rz.m[3][1] = 0;
        rz.m[3][2] = 0;
        rz.m[3][3] = 1;

        // ----------------------

        rx.m[0][0] = 1;
        rx.m[0][1] = 0;
        rx.m[0][2] = 0;
        rx.m[0][3] = 0;

        rx.m[1][0] = 0;
        rx.m[1][1] = FastMath.cos(degreesX);
        rx.m[1][2] = -FastMath.sin(degreesX);
        rx.m[1][3] = 0;

        rx.m[2][0] = 0;
        rx.m[2][1] = FastMath.sin(degreesX);
        rx.m[2][2] = FastMath.cos(degreesX);
        rx.m[2][3] = 0;

        rx.m[3][0] = 0;
        rx.m[3][1] = 0;
        rx.m[3][2] = 0;
        rx.m[3][3] = 1;

        // ---------------------

        ry.m[0][0] = FastMath.cos(degreesY);
        ry.m[0][1] = 0;
        ry.m[0][2] = -FastMath.sin(degreesY);
        ry.m[0][3] = 0;

        ry.m[1][0] = 0;
        ry.m[1][1] = 1;
        ry.m[1][2] = 0;
        ry.m[1][3] = 0;

        ry.m[2][0] = FastMath.sin(degreesY);
        ry.m[2][1] = 0;
        ry.m[2][2] = FastMath.cos(degreesY);
        ry.m[2][3] = 0;

        ry.m[3][0] = 0;
        ry.m[3][1] = 0;
        ry.m[3][2] = 0;
        ry.m[3][3] = 1;

        mat.m = rz.multiply(ry.multiply(rx)).m;
        return mat;
    }

    public Matrix4f initPerspective(float fov, float width, float height, float zNear, float zFar) {
        float ar = width / height;
        float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        m[0][0] = 1f / (tanHalfFOV * ar);
        m[0][1] = 0;
        m[0][2] = 0;
        m[0][3] = 0;

        m[1][0] = 0;
        m[1][1] = 1f / tanHalfFOV;
        m[1][2] = 0;
        m[1][3] = 0;

        m[2][0] = 0;
        m[2][1] = 0;
        m[2][2] = (-zNear - zFar) / zRange;
        m[2][3] = 2 * zFar * zNear / zRange;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 1;
        m[3][3] = 0;

        return this;
    }

    public Matrix4f initCamera(Vector3f forward, Vector3f up) {
        Vector3f f = new Vector3f(forward);
        f.normalize();
        
        Vector3f r = new Vector3f(up);
        r.normalize();
        r = r.cross(f);
        
        Vector3f u = f.cross(r);

        m[0][0] = r.x;
        m[0][1] = r.y;
        m[0][2] = r.z;
        m[0][3] = 0;

        m[1][0] = u.x;
        m[1][1] = u.y;
        m[1][2] = u.z;
        m[1][3] = 0;

        m[2][0] = f.x;
        m[2][1] = f.y;
        m[2][2] = f.z;
        m[2][3] = 0;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return this;
    }
    
    public static Matrix4f initCamera(Matrix4f mat, Vector3f forward, Vector3f up) {
        Vector3f f = new Vector3f(forward);
        f.normalize();
        
        Vector3f r = new Vector3f(up);
        r.normalize();
        r = r.cross(f);
        
        Vector3f u = f.cross(r);

        mat.m[0][0] = r.x;
        mat.m[0][1] = r.y;
        mat.m[0][2] = r.z;
        mat.m[0][3] = 0;

        mat.m[1][0] = u.x;
        mat.m[1][1] = u.y;
        mat.m[1][2] = u.z;
        mat.m[1][3] = 0;

        mat.m[2][0] = f.x;
        mat.m[2][1] = f.y;
        mat.m[2][2] = f.z;
        mat.m[2][3] = 0;

        mat.m[3][0] = 0;
        mat.m[3][1] = 0;
        mat.m[3][2] = 0;
        mat.m[3][3] = 1;

        return mat;
    }

    public Matrix4f multiply(Matrix4f mat) {
        Matrix4f result = new Matrix4f();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                result.m[x][y] = this.m[x][0] * mat.m[0][y] + //
                        this.m[x][1] * mat.m[1][y] + //
                        this.m[x][2] * mat.m[2][y] + //
                        this.m[x][3] * mat.m[3][y]; //
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(m);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix4f other = (Matrix4f) obj;
        if (!Arrays.deepEquals(m, other.m))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(m[0]) + "\n" + Arrays.toString(m[1]) + "\n" + Arrays.toString(m[2]) + "\n"
                + Arrays.toString(m[3]);
    }

}
