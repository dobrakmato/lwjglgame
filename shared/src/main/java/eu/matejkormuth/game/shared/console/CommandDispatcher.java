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
package eu.matejkormuth.game.shared.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandDispatcher {

    private static final Logger log = LoggerFactory.getLogger(CommandDispatcher.class);

    private Map<String, ConsoleCommand> commands;
    
    public CommandDispatcher() {
        commands = new HashMap<>();
    }

    public void register(ConsoleCommand command) {
        if (commands.containsKey(command.getName())) {
            throw new RuntimeException("Command " + command.getName() + " is already registered!");
        }
        commands.put(command.getName(), command);
    }

    public void dispatch(String line) {
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split(" ");

        if (parts[0].equalsIgnoreCase("help") && parts.length == 2) {
            if (commands.containsKey(parts[1])) {
                displayHelp(parts[1]);
            } else {
                // No such command.
                log.error("No such command '{}'!", parts[1]);
            }
        }

        if (commands.containsKey(parts[0])) {
            dispatch0(parts);
        } else {
            // No such command.
            log.error("No such command '{}'!", parts[0]);
        }
    }

    private void displayHelp(String string) {
        ConsoleCommand cmd = commands.get(string);
        log.info("Command: ", cmd.getName());
        log.info("Description: ", cmd.getDescription());
        log.info("Usage: ", cmd.getUsage());
    }

    private void dispatch0(String[] parts) {
        ConsoleCommand cmd = commands.get(parts[0]);
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        cmd.execute(args);
    }

}
