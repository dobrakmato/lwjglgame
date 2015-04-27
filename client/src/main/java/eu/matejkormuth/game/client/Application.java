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
package eu.matejkormuth.game.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.shared.console.StdInConsole;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static Application app;

    public static void main(String[] args) {
        new Application().start();
    }

    public static Application getApplication() {
        return app;
    }

    private Map<String, CVar> cvars;
    private StdInConsole console;

    public Application() {
        app = this;
        this.cvars = new HashMap<>();
        CVarDefaults.init(this);
    }

    public void start() {
        log.info("Client started at {}", new SimpleDateFormat().format(new Date()));

        // Start console reader.
        this.console = new StdInConsole();
    }

    public void shutdown() {
        this.console.shutdown();
    }

    public CVar getCvar(String name) {
        if (cvars.containsKey(name)) {
            return cvars.get(name);
        } else {
            return cvars.put(name, new CVar(name));
        }
    }

}
