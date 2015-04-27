package eu.matejkormuth.game.shared.networking.tcp;

import eu.matejkormuth.game.shared.networking.Protocol;
import eu.matejkormuth.game.shared.networking.tcp.protocol.FileTransferMessage;

public class TcpProtocol extends Protocol {

    @Override
    protected void registerMessages() {
         register(0, FileTransferMessage.class);
    }

}
