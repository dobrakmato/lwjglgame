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

import eu.matejkormuth.game.server.gameobjects.SWorld;

import java.util.concurrent.atomic.AtomicInteger;

public class WorldServer {

    private static final Logger log = LoggerFactory.getLogger(WorldServer.class);
    private static final AtomicInteger id = new AtomicInteger();

    private SWorld world;
    private Thread updateThread;
    private TPSMeter tpsMeter;
    private boolean running;
    private int targetTps = 66;

    public WorldServer() {
        this.tpsMeter = new TPSMeter(this.targetTps);
        this.world = new SWorld();
        this.updateThread = new Thread(this::doUpdate, "WorldServer-" + id.incrementAndGet());
    }

    private void doUpdate() {
        // Target length of one update in milliseconds.
        final double targetLengthMs = 1000 / this.targetTps;
        long lastStart, lastEnd = System.nanoTime();
        while (running) {
            this.tpsMeter.tickOccured();
            lastStart = System.nanoTime();
            // Time elapsed since last update.
            this.world.update((lastStart - lastEnd) / 1000000000f);
            lastEnd = System.nanoTime();

            try {
                // Time elapsed since start of this update.
                Thread.sleep((long) (targetLengthMs - (lastEnd - lastStart) / 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        log.info("Starting up WorldServer...");
        this.running = true;
        this.updateThread.start();
    }

    public void shutdown() {
        log.info("Shutting down WorldServer...");
        this.tpsMeter.disable();
        running = false;
    }

    public int getTargetTPS() {
        return targetTps;
    }

    public int getEffectiveTPS() {
        return tpsMeter.getLastTPS();
    }

    public SWorld getWorld() {
        return world;
    }
}
