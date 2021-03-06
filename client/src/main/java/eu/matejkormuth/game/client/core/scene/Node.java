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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.content.Resource;
import eu.matejkormuth.game.client.content.ResourceInjectLocation;
import eu.matejkormuth.game.client.core.scene.nodetypes.ForwardLightSource;
import eu.matejkormuth.game.client.gl.IProgram;
import eu.matejkormuth.game.client.gl.Renderer;
import eu.matejkormuth.game.shared.Disposable;
import eu.matejkormuth.game.shared.Updatable;
import eu.matejkormuth.game.shared.math.Matrix4f;
import eu.matejkormuth.game.shared.math.Vector3f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Node implements Updatable, Disposable {

    private static final Logger log = LoggerFactory.getLogger(Node.class);

    private boolean isRootNode = false;
    protected Node parent;
    protected List<Node> children;
    protected List<NodeComponent> components;
    protected Renderer renderer;

    private Matrix4f positionMat = new Matrix4f().initIdentity();
    private Matrix4f rotationMat = new Matrix4f().initIdentity();
    private Matrix4f scaleMat = new Matrix4f().initIdentity();

    @Property
    public String name;

    public Node() {
        this(false);
    }

    public Node(boolean rootNode) {
        if (rootNode) {
            children = new ArrayList<>(16);
            components = new ArrayList<>(1);
        } else {
            children = new ArrayList<>(1);
            components = new ArrayList<>(2);
        }
        this.isRootNode = rootNode;
        checkName();
        onInitialize();
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

    private void onInitialize() {
    }

    @Property
    public Vector3f position = new Vector3f(0);
    @Property
    public Vector3f rotation = new Vector3f(0);
    @Property
    public Vector3f scale = new Vector3f(1);

    public boolean hasChild(String name) {
        for (Node node : this.children) {
            if (node.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void addComponent(NodeComponent component) {
        this.components.add(component);
        component.parent = this;
    }

    public void removeComponent(NodeComponent component) {
        this.components.remove(component);
        component.parent = null;
    }

    public void addChild(Node node) {
        if (this.children.contains(node)) {
            log.error("Can't add child node to parent node! Node {} is alredy child node of {}.", node.toString(),
                    this.toString());
        }
        this.children.add(node);
        node.parent = this;
        node.renderer = this.renderer;
        node.checkName();
    }

    public void removeChild(Node node) {
        this.children.remove(node);
        node.parent = null;
        node.renderer = null;
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

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    protected void createGraphBasedOnFields() throws IllegalArgumentException, IllegalAccessException {
        for (Field f : this.getClass().getDeclaredFields()) {
            if (Node.class.isAssignableFrom(f.getType())) {
                if(!f.isAccessible()) {
                    f.setAccessible(true);
                }
                
                // This is a child.
                Node childNode = (Node) f.get(this);
                this.addChild(childNode);
            }
        }
    }

    public List<ForwardLightSource> gatherLights(List<ForwardLightSource> lights) {
        if (lights == null) {
            lights = new ArrayList<>(20);
        }

        if (this instanceof ForwardLightSource) {
            lights.add((ForwardLightSource) this);
        }

        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).gatherLights(lights);
        }
        return lights;
    }

    protected List<ResourceInjectLocation> gatherResourceInjectLocations(List<ResourceInjectLocation> resourcers) {
        if (resourcers == null) {
            resourcers = new ArrayList<>(20);
        }

        for (Field f : this.getClass().getFields()) {
            if (f.isAnnotationPresent(Resource.class)) {
                resourcers.add(new ResourceInjectLocation(f, this));
            }
        }

        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).gatherResourceInjectLocations(resourcers);
        }
        
        return resourcers;
    }

    public void render(IProgram program) {
        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).render(program);
        }

        for (int i = 0; i < this.components.size(); i++) {
            this.components.get(i).render();
        }
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).update(delta);
        }

        for (int i = 0; i < this.components.size(); i++) {
            this.components.get(i).update(delta);
        }
    }

    public Matrix4f getTransformation() {
        // TODO: Cache matrices, update from setters.
        Matrix4f.initTranslation(this.positionMat, this.position.x, this.position.y, this.position.z);
        Matrix4f.initRotation(this.rotationMat, this.rotation.x, this.rotation.y, this.rotation.z);
        Matrix4f.initScale(this.scaleMat, this.scale.x, this.scale.y, this.scale.z);

        return positionMat.multiply(rotationMat.multiply(scaleMat));
    }

    @Override
    public void dispose() {
        this.onDestroy();
    }

    private void onDestroy() {
        for (int i = 0; i < this.children.size(); i++) {
            this.children.get(i).onDestroy();
        }

        this.components.clear();
        this.children = null;
        this.components = null;

        this.renderer = null;
        this.parent = null;
        this.scaleMat = null;
        this.positionMat = null;
        this.rotationMat = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [name=" + name + "]";
    }

}
