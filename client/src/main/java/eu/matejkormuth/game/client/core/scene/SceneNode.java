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

import eu.matejkormuth.game.shared.math.Vector3f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SceneNode {
    private boolean isRootNode = false;
    protected SceneNode parent;
    protected List<SceneNode> children;
    @Property
    public String name;

    public SceneNode() {
        checkName();
        children = new ArrayList<>();
        initialize();
    }

    private void checkName() {
        if (this.parent != null) {
            if (this.name == null || this.name.isEmpty()) {
                this.name = this.getClass().getSimpleName();
                int id = 0;
                while (this.parent.hasChild(this.name + id)) {
                    id++;
                }
                this.name = this.name + id;
            } else {
                if (this.parent.hasChild(name)) {
                    int id = 0;
                    while (this.parent.hasChild(this.name + id)) {
                        id++;
                    }
                    this.name = this.name + id;
                }
            }
        } else {
            if (this.name == null || this.name.isEmpty()) {
                if (this.isRootNode) {
                    this.name = "RootNode";
                } else {
                    this.name = this.getClass().getSimpleName();
                    this.name = this.name.substring(0, 1).toLowerCase() + this.name.substring(1);
                }
            }
        }
    }

    private void initialize() {
    }

    @Property
    public Vector3f position = new Vector3f(0);
    @Property
    public Vector3f rotation = new Vector3f(0);
    @Property
    public Vector3f scale = new Vector3f(1);

    public boolean hasChild(String name) {
        for (SceneNode node : this.children) {
            if (node.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void addChild(SceneNode node) {
        this.children.add(node);
        node.parent = this;
        node.checkName();
    }

    public void removeChild(SceneNode node) {
        this.children.remove(node);
        node.parent = null;
        node.checkName();
    }

    public Vector3f getPosition() {
        if (parent != null && !parent.isRootNode) {
            return parent.getPosition().add(this.position);
        } else {
            return this.position;
        }
    }

    public Vector3f getRotation() {
        if (parent != null && !parent.isRootNode) {
            return parent.getRotation().add(this.rotation);
        } else {
            return this.rotation;
        }
    }

    public Vector3f getScale() {
        if (parent != null && !parent.isRootNode) {
            return parent.getScale().multiply(this.scale);
        } else {
            return this.scale;
        }
    }

    public void setRootNode(boolean isRootNode) {
        this.isRootNode = isRootNode;
    }

    public boolean isRootNode() {
        return this.isRootNode;
    }

    public SceneNode getParent() {
        return parent;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    protected void createGraphBasedOnFields() throws IllegalArgumentException, IllegalAccessException {
        for (Field f : this.getClass().getDeclaredFields()) {
            if (SceneNode.class.isAssignableFrom(f.getClass())) {
                // This is a child.
                SceneNode childNode = (SceneNode) f.get(this);
                this.addChild(childNode);
            }
        }
    }
}
