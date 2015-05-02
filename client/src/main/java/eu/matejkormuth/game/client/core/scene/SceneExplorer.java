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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneExplorer {

    private static final Logger log = LoggerFactory.getLogger(SceneExplorer.class);
    private Map<Class<? extends SceneNode>, Field[]> propertiesCache;

    public SceneExplorer() {
        propertiesCache = new HashMap<>();
    }

    public List<SceneNode> getChildren(SceneNode node) {
        return node.getChildren();
    }
    
    public Field[] getProperties(SceneNode node) {
        Class<? extends SceneNode> clazz = node.getClass();
        if (propertiesCache.containsKey(clazz)) {
            return propertiesCache.get(clazz);
        } else {
            ArrayList<Field> properties = new ArrayList<>();
            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(Property.class)) {
                    properties.add(field);
                }
            }
            Field[] propertyArray = properties.toArray(new Field[0]);
            propertiesCache.put(clazz, propertyArray);
            return propertyArray;
        }
    }

    public Object getProperty(SceneNode node, String name) {
        Class<? extends SceneNode> clazz = node.getClass();
        Field[] properties = null;
        if (propertiesCache.containsKey(clazz)) {
            properties = propertiesCache.get(clazz);
        } else {
            properties = getProperties(node);
        }
        for (Field f : properties) {
            if (f.getName().equalsIgnoreCase(name)) {
                try {
                    return f.get(node);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Can't get value of property " + name, e);
                    return null;
                }
            }
        }
        return null;
    }

    public void setProperty(SceneNode node, String name, Object value) {
        Class<? extends SceneNode> clazz = node.getClass();
        Field[] properties = null;
        if (propertiesCache.containsKey(clazz)) {
            properties = propertiesCache.get(clazz);
        } else {
            properties = getProperties(node);
        }
        for (Field f : properties) {
            if (f.getName().equalsIgnoreCase(name)) {
                try {
                    f.set(node, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Can't set value of property " + name, e);
                }
            }
        }
    }

    public String getType(SceneNode node) {
        return node.getClass().getSimpleName();
    }

    public String getName(SceneNode node) {
        return node.name;
    }

    public Object getValue(Field property, SceneNode node) {
        try {
            return property.get(node);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("Can't get value of property " + property.getName(), e);
            return null;
        }
    }

    public int getChildrenCount(SceneNode node) {
        return node.children.size();
    }
}
