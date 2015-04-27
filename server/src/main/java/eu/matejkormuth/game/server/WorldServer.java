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

import eu.matejkormuth.game.server.gameobjects.SWorld;

import java.util.concurrent.atomic.AtomicInteger;

public class WorldServer {

    private static final AtomicInteger id = new AtomicInteger();

    private SWorld world;
    private Thread updateThread;
    private boolean running;
    private int tps = 66;

    public WorldServer() {
        this.world = new SWorld();
        this.updateThread = new Thread(this::update, "WorldServer-" + id.incrementAndGet());
    }

    private void update() {
        // Target length of one update in milliseconds.
        final double targetLengthMs = 1000 / this.tps;
        long lastStart, lastEnd = System.nanoTime();
        while (running) {
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
        this.running = true;
        this.updateThread.start();
    }

    public void shutdown() {
        running = false;
    }

    public int getTPS() {
        return tps;
    }

    public SWorld getWorld() {
        return world;
    }
}
