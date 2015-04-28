/**
 * server - Multiplayer Java game engine.
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
package eu.matejkormuth.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.server.commands.ShutdownCommand;
import eu.matejkormuth.game.shared.console.StdInConsole;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static Application app;

    public static void main(String[] args) {
        new Application().start();
    }

    public static Application getApplication() {
        return app;
    }

    private ScheduledExecutorService scheduler;
    private WorldServer world;
    private StdInConsole console;

    public Application() {
        app = this;
    }

    public void start() {
        scheduler = Executors.newScheduledThreadPool(1);
        
        log.info("Server started at {}", new SimpleDateFormat().format(new Date()));

        console = new StdInConsole();
        console.getDispatcher().register(new ShutdownCommand());
        console.start();
        
        world = new WorldServer();
        world.start();
    }

    public void shutdown() {
        log.info("Shutting down...");
        console.shutdown();
        world.shutdown();
        // Shutdown scheduler.
        scheduler.shutdown();
    }

    public StdInConsole getConsole() {
        return console;
    }

    public WorldServer getWorldServer() {
        return world;
    }
    
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

}
