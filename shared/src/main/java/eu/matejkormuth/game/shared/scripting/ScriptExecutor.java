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
package eu.matejkormuth.game.shared.scripting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ScriptExecutor {

    private static final Logger log = LoggerFactory.getLogger(ScriptExecutor.class);

    private Map<Path, String> loadedScripts;
    private GroovyClassLoader gcl;
    private GroovyShell shell;

    public ScriptExecutor() {
        gcl = new GroovyClassLoader();
        shell = new GroovyShell();
        loadedScripts = new HashMap<>();

        addExecFunction();
    }

    private void addExecFunction() {
        shell.getContext().setVariable("exec", new Closure<Void>(this) {
            private static final long serialVersionUID = 3132067890900944384L;

            @Override
            public Void call(Object... args) {
                ScriptExecutor.this.execute(Paths.get(args[0].toString()));
                return null;
            }
        });
    }

    public void execute(Path path) {
        if (!loadedScripts.containsKey(path)) {
            if (!load(path)) {
                return;
            }
        }
        eval(loadedScripts.get(path));
    }

    private boolean load(Path path) {
        if (path.toFile().exists()) {
            log.info("Loading script file {}...", path.toString());
            try {
                String contents = new String(Files.readAllBytes(path), Charset.forName("UTF-8"));
                loadedScripts.put(path, contents);
                return true;
            } catch (IOException e) {
                log.error("Can't load script file {}!", path.toString());
                log.error("Problem: ", e);
                return false;
            }
        } else {
            log.error("Script file {} could not be found!", path.toString());
            return false;
        }
    }

    private void eval(String script) {
        
        // Determinate whether this is a script or a class definition.
        if(isAClassDefinition(script)) {
            loadClass(script);
        } else {
            evalScript(script);
        }
    }

    private void loadClass(String script) {
        gcl.parseClass(script);
    }

    private void evalScript(String script) {
        try {
            shell.evaluate(script);
        } catch (Exception e) {
            log.error("Error while executing script: ", e);
        }
    }

    private boolean isAClassDefinition(String script) {
        return script.contains("class");
    }
}
