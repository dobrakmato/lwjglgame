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
package eu.matejkormuth.game.shared.networking;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Protocol {

    private final Map<Class<? extends Message>, Constructor<? extends Message>> constructorsByClass;
    private final Map<Byte, Constructor<? extends Message>> constructorsById;
    private final Map<Class<? extends Message>, Byte> idsByClass;

    public Protocol() {
        this.constructorsByClass = new HashMap<>();
        this.constructorsById = new HashMap<>();
        this.idsByClass = new HashMap<>();

        registerMessages();
    }

    protected abstract void registerMessages();

    public void register(int id, Class<? extends Message> type) {
        if (id > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("id must be lower then Byte.MAX_VALUE");
        }

        try {
            Constructor<? extends Message> noArgCtr = type.getConstructor();
            if (noArgCtr.isAccessible()) {
                throw new RuntimeException("Argless contructor is not accessible!");
            }

            constructorsByClass.put(type, noArgCtr);
            constructorsById.put((byte) id, noArgCtr);
            idsByClass.put(type, (byte) id);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("All Messages must declare public argless contructor!", e);
        }
    }

    public <T extends Message> T create(Class<T> type) {
        if (this.constructorsByClass.containsKey(type)) {
            try {
                Message Message = this.constructorsByClass.get(type).newInstance();
                return unsafeCast(Message, type);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException("Can't create Message of type " + type.getName(), e);
            }
        } else {
            throw new RuntimeException("No such Message of type " + type.getName()
                    + " is registered in this MessageFactory!");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> T create(short id) {
        if (this.constructorsById.containsKey(id)) {
            try {
                Message Message = this.constructorsById.get(id).newInstance();
                return (T) Message;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException("Can't create Message of id " + id, e);
            }
        } else {
            throw new RuntimeException("No such Message of type " + id + " is registered in this MessageFactory!");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T unsafeCast(Object o, Class<T> type) {
        return (T) o;
    }

    public byte getId(Message msg) {
        Class<?> clazz = msg.getClass();
        if (this.idsByClass.containsKey(clazz)) {
            return this.idsByClass.get(clazz);
        } else {
            throw new IllegalArgumentException("Message '" + msg.getClass().getName()
                    + "' is not recognized by protocol " + this.getClass().getName() + "!");
        }
    }

}
