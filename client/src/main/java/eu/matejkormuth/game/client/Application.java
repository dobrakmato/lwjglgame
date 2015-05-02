/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.game.client.al.AudioDevice;
import eu.matejkormuth.game.client.commands.ShutdownCommand;
import eu.matejkormuth.game.client.content.Content;
import eu.matejkormuth.game.shared.console.StdInConsole;
import eu.matejkormuth.game.shared.scripting.GroovyScriptExecutor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static Application app;

    public static void main(String[] args) {
        // TODO: Parse args.
        
        new Application().start();
    }

    public static Application get() {
        return app;
    }
    
    private AudioDevice audioDevice;
    private GroovyScriptExecutor scriptExecutor;
    private StdInConsole console;
    private Window window;
    private Configuration conf;

    public Application() {
        app = this;
    }

    public void start() {
        log.info("Client started at {}", new SimpleDateFormat().format(new Date()));
        
        // Load configuration.
        this.conf = Configuration.load();

        // Enable LWJGL debug.
        System.setProperty("org.lwjgl.util.Debug", "true");

        // Prepare content loading.
        Content.setRoot(new File(".").getAbsoluteFile().toPath());

        // Initialize Groovy script executor.
        this.scriptExecutor = new GroovyScriptExecutor();
        this.scriptExecutor.execute(Content.getPath("scripts", "main.groovy"));
        
        // Start console reader.
        this.console = new StdInConsole();
        this.console.getDispatcher().register(new ShutdownCommand());
        this.console.start();

        // Create audio device.
        this.audioDevice = new AudioDevice();
        
        // Create window and start rendering.
        this.window = new Window();
        this.window.doUpdate();
    }

    public void shutdown() {
        this.console.shutdown();
        this.window.shutdown();
        
        log.info("Saving configuration...");
        conf.save();
    }

    public StdInConsole getConsole() {
        return console;
    }

    public Window getWindow() {
        return window;
    }
    
    public Configuration getConfiguration() {
        return conf;
    }
    
    public GroovyScriptExecutor getScriptExecutor() {
        return scriptExecutor;
    }

}
