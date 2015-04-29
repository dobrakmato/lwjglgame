/**
 * server - Multiplayer Java game engine.
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
