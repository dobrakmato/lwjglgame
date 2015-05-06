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

import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.client.content.ResourceInjectLocation;
import eu.matejkormuth.game.client.core.scene.nodetypes.Camera;
import eu.matejkormuth.game.client.gl.ICamera;
import eu.matejkormuth.game.shared.Disposable;

import java.util.List;

public class SceneInitializer {

    private static final Logger log = LoggerFactory.getLogger(SceneInitializer.class);

    private Node rootNode;
    private ICamera camera;

    public SceneInitializer(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Node loadScene() {
        // Create scene graph.
        try {
            rootNode.createGraphBasedOnFields();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Find camera or fail.
        for (Node node : rootNode.children) {
            if (node instanceof ICamera) {
                this.camera = (ICamera) node;
                break;
            }
        }
        if (this.camera == null) {
            log.error("Loaded scene does not contains camera!");
            log.warn("Creating default camera to allow scene render.");
            Camera cam = new Camera();
            rootNode.addChild(cam);
            this.camera = cam;
        }

        // Find resources in scene graph.
        List<ResourceInjectLocation> resources = rootNode.gatherResourceInjectLocations(null);

        // Load resources and inject them.
        for (ResourceInjectLocation resourceLoc : resources) {
            // Resource key should be in format category/folder/folder/name.ext

            Disposable resource = Content.provide(resourceLoc.annotation.value(), resourceLoc.resourceKey.split("/"));
            resourceLoc.inject(resource);
        }

        // Initialize resources (default values).

        // Finalize.

        // Go to render loop.
        return rootNode;
    }

    public ICamera getCamera() {
        return camera;
    }
}
