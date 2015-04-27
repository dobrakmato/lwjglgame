/**
 * client - Multiplayer Java game engine. Copyright (C) 2015 Matej Kormuth
 * <http://www.github.com/dobrakmato>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.matejkormuth.game.client.gl;

import static org.lwjgl.opengl.GL20.*;
import eu.matejkormuth.game.shared.Disposable;

public class Program implements Disposable {

    private int program;
    private Shader vertexShader;
    private Shader fragmentShader;

    public Program(Shader vertexShader, Shader fragmentShader) {

        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;

        program = glCreateProgram();
        glAttachShader(program, vertexShader.getShaderId());
        glAttachShader(program, fragmentShader.getShaderId());
        glLinkProgram(program);
    }

    public void use() {
        glUseProgram(this.program);
    }

    @Override
    public void dispose() {
        glDetachShader(program, this.vertexShader.getShaderId());
        glDetachShader(program, this.fragmentShader.getShaderId());
        glDeleteProgram(program);

        vertexShader = null;
        fragmentShader = null;
    }

}
