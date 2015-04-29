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
