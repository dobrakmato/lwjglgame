/**
 * shared - Multiplayer Java game engine.
 * Copyright (C) 2015 Matej Kormuth <http://www.github.com/dobrakmato>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
