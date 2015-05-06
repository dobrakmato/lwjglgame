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
package eu.matejkormuth.game.client.content;

import eu.matejkormuth.game.client.core.scene.Node;

import java.lang.reflect.Field;

public class ResourceInjectLocation {
    public Resource annotation;
    public Field location;
    public Node target;
    public String resourceKey;

    public ResourceInjectLocation(Field location, Node target) {
        if (!location.isAnnotationPresent(Resource.class)) {
            throw new RuntimeException("Provided field is not a @Resource field.");
        }

        this.annotation = location.getAnnotation(Resource.class);
        this.location = location;
        this.target = target;

        Object key;
        try {
            key = location.get(target);
            if (key instanceof String) {
                this.resourceKey = (String) key;
            } else {
                throw new RuntimeException("Provided field does not contains a resource loaction key.");
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void inject(Object obj) {
        if (obj.getClass() != annotation.value()) {
            throw new RuntimeException("Tried to inject worng resource type to " + this.target.toString()
                    + "! Expected type was: " + annotation.value().getSimpleName() + ". Provided type: "
                    + obj.getClass().getSimpleName() + ".");
        }

        try {
            location.set(target, obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
