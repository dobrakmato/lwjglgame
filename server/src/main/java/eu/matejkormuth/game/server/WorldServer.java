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
