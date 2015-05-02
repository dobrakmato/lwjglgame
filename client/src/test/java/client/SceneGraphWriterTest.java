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
package client;

import org.junit.Test;

import eu.matejkormuth.game.client.core.scene.NodeGroup;
import eu.matejkormuth.game.client.core.scene.SceneGraphWriter;
import eu.matejkormuth.game.client.core.scene.SceneNode;
import eu.matejkormuth.game.client.core.scene.lights.PointLight;
import eu.matejkormuth.game.shared.math.Vector3f;

public class SceneGraphWriterTest {

    @Test
    public void test() {
        StringBuilder builder = new StringBuilder();
        SceneGraphWriter writer = new SceneGraphWriter(builder);
        SceneNode root = new SceneNode();
        root.setRootNode(true);
        PointLight light = new PointLight();
        light.position = new Vector3f(1, 2, 3);
        root.addChild(light);
        NodeGroup group = new NodeGroup();
        PointLight light2 = new PointLight();
        light.position = new Vector3f(4, 5, 6);
        PointLight light3 = new PointLight();
        light.position = new Vector3f(1, -2, 1);
        group.addChild(light2);
        group.addChild(light3);
        root.addChild(group);
        
        writer.write(root, "scene 1");

        System.out.println(builder.toString());
    }

}
