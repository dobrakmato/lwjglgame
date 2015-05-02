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

import java.lang.reflect.Field;

public class SceneGraphWriter {

    private static final String EOL = System.lineSeparator();
    private SceneExplorer explorer;
    private ObjectInitializator initializator;
    private int currentIdentation = 0;
    private StringBuilder out;

    public SceneGraphWriter(StringBuilder output) {
        explorer = new SceneExplorer();
        initializator = new ObjectInitializator();
        this.out = output;
    }

    public void nl() {
        out.append(EOL);
    }

    public String ident() {
        char[] array = new char[currentIdentation];
        for (int i = 0; i < currentIdentation; i++) {
            array[i] = ' ';
        }
        return new String(array);
    }

    public void line(String line) {
        out.append(ident() + line + EOL);
    }

    public String comment(String msg) {
        return "// " + msg;
    }

    public void write(SceneNode root, String name) {
        if (!root.isRootNode()) {
            throw new IllegalArgumentException("SceneNode must be root node!");
        }
        // Reset identation.
        currentIdentation = 0;

        line(comment("Groovy Class Script"));
        line(comment("Content-Type: Scene"));
        
        nl();
        line("import eu.matejkormuth.game.client.core.scene.*");
        line("import eu.matejkormuth.game.client.core.scene.lights.*");
        line("import eu.matejkormuth.game.client.gl.lighting.*");
        line("import eu.matejkormuth.game.shared.math.*");

        nl();
        line("class " + className(name) + " extends SceneNode {");
        currentIdentation += 4;
        writeRootChildren(root);
        currentIdentation -= 4;
        line("}");
    }

    private void writeRootChildren(SceneNode root) {
        // Write root children nodes.
        for (SceneNode child : explorer.getChildren(root)) {
            String nodeType = explorer.getType(child);
            String nodeName = fieldName(explorer.getName(child));
            // Open new child node.
            line(nodeType + " " + nodeName + " = new " + nodeType + "(");
            currentIdentation += 4;
            writeProperties(child);
            // Write children node's children.
            writeChildren(child);
            currentIdentation -= 4;
            // Close this child node.
            line(");");
        }
    }

    private void writeChildren(SceneNode node) {
        if (explorer.getChildrenCount(node) == 0) {
            line("children: []");
            return;
        }

        line("children: [");
        currentIdentation += 4;
        for (SceneNode child : explorer.getChildren(node)) {
            String nodeType = explorer.getType(child);
            // Open new child node.
            line("new " + nodeType + "(");
            currentIdentation += 4;
            writeProperties(child);
            // Write children node's children.
            writeChildren(child);
            currentIdentation -= 4;
            // Close this child node.
            line("),");
        }
        currentIdentation -= 4;
        line("]");
    }

    private void writeProperties(SceneNode child) {
        // Write children node's properties.
        for (Field property : explorer.getProperties(child)) {
            String propertyName = property.getName();
            String propertyInitializer = initializator.createInitializator(explorer.getValue(property, child));
            line(propertyName + ": " + propertyInitializer + ", ");
        }
    }

    private static String className(String name) {
        String n = name.replaceAll(" ", "");
        return n.substring(0, 1).toUpperCase() + n.substring(1);
    }

    private static String fieldName(String name) {
        String n = name.replaceAll(" ", "");
        return n.substring(0, 1).toLowerCase() + n.substring(1);
    }
}
