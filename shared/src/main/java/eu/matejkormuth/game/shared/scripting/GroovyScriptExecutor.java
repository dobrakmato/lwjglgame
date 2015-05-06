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

import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.tools.GroovyClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GroovyScriptExecutor {

    private static final Logger log = LoggerFactory.getLogger(GroovyScriptExecutor.class);
    private static final Logger scriptLog = LoggerFactory.getLogger("ScriptLogger-0");

    private Map<Path, String> loadedScripts;
    private GroovyClassLoader gcl;
    private GroovyShell shell;

    public GroovyScriptExecutor(ClassLoader loader) {
        gcl = new GroovyClassLoader(loader);
        shell = new GroovyShell(gcl);
        loadedScripts = new HashMap<>();

        addCtxVars();
    }

    private void addCtxVars() {
        shell.getContext().setVariable("include", new Closure<Void>(this) {
            private static final long serialVersionUID = 3132067890900944384L;

            @Override
            public Void call(Object... args) {
                GroovyScriptExecutor.this.execute(Paths.get(args[0].toString()));
                return null;
            }
        });

        shell.getContext().setVariable("console", scriptLog);
    }

    public void execute(Path path) {
        try {
            if (!loadedScripts.containsKey(path)) {
                if (!loadScript(path)) {
                    return;
                }
            }
            String script = loadedScripts.get(path);
            if (isAClassDefinition(script)) {
                compileAndLoadClass(path, script);
            } else {
                evalScript(script);
            }

        } catch (Exception e) {
            log.error("Can't execute " + path.toString(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    private Class compileAndLoadClass(Path path, String fileContents) {
        File srcFile = path.toFile();
        File compiledFile = new File(srcFile.getAbsolutePath().replace(".groovy", ".gcs"));
        String className = srcFile.getName().substring(0, srcFile.getName().indexOf("."));
        byte[] classBytes = null;
        if (!compiledFile.exists()) {
            // Compile class.
            CompilationUnit compileUnit = new CompilationUnit();
            compileUnit.addSource(className, fileContents);
            compileUnit.compile(Phases.CLASS_GENERATION);

            for (Object compileClass : compileUnit.getClasses()) {
                GroovyClass groovyClass = (GroovyClass) compileClass;
                if (className.equals(groovyClass.getName())) {
                    // Save class.
                    try {
                        Files.write(compiledFile.toPath(), classBytes = groovyClass.getBytes());
                    } catch (IOException e) {
                        log.error("Can't save compiled class for " + className);
                    }
                }
            }

        }
        // Load compiled file.
        if (classBytes == null) {
            try {
                classBytes = Files.readAllBytes(compiledFile.toPath());
            } catch (IOException e) {
                log.error("Can't load compiled class file!", e);
            }
        }

        return gcl.defineClass(className, classBytes);
    }

    private boolean loadScript(Path path) {
        if (path.toFile().exists()) {
            log.debug("Loading script file {}...", path.toString());
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

    private void evalScript(String script) {
        try {
            shell.evaluate(script);
        } catch (Exception e) {
            log.error("Error while executing script: ", e);
        }
    }

    private boolean isAClassDefinition(String script) {
        return script.contains("class ");
    }

    private static String imports = "import eu.matejkormuth.game.client.core.scene.*\n"
            + "import eu.matejkormuth.game.client.core.scene.components.*\n"
            + "import eu.matejkormuth.game.client.core.scene.nodetypes.*\n"
            + "import eu.matejkormuth.game.client.gl.lighting.*\n" + "import eu.matejkormuth.game.shared.math.*\n";

    @SuppressWarnings("rawtypes")
    public Object loadScene(Path path) {
        String content;
        try {
            content = new String(Files.readAllBytes(path));

            if (!content.contains("import ")) {
                content = imports + content;
            }

            Class c = compileAndLoadClass(path, content);
            return c.newInstance();
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
