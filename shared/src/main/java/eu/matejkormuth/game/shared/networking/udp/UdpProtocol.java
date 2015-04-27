package eu.matejkormuth.game.shared.networking.udp;

import eu.matejkormuth.game.shared.networking.Protocol;
import eu.matejkormuth.game.shared.networking.udp.protocol.UserInputMessage;

public class UdpProtocol extends Protocol {

    @Override
    protected void registerMessages() {
        register(0, UserInputMessage.class);
    }

}
