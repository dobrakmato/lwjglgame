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
