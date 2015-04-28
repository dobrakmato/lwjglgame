/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client.commands;

import eu.matejkormuth.game.client.Application;
import eu.matejkormuth.game.shared.console.ConsoleCommand;

public class ShutdownCommand extends ConsoleCommand {

    public ShutdownCommand() {
        super("shutdown");
        this.description = "Shuts down the client disconnecting from current game.";
        this.usage = "shutdown";
    }

    @Override
    public void execute(String[] args) {
        Application.getApplication().shutdown();
    }

}
