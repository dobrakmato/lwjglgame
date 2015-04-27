package eu.matejkormuth.game.shared.networking;

public class MessageFactory {
    private Protocol protocol;

    public MessageFactory(Protocol protocol) {
        this.protocol = protocol;
    }

    public <T extends Message> T createPacket(Class<T> type) {
        return protocol.create(type);
    }

    public <T extends Message> T createPacket(short id) {
        return protocol.create(id);
    }

}
