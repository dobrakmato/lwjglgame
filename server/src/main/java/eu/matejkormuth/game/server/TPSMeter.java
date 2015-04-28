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

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TPSMeter {

    private static final Logger log = LoggerFactory.getLogger(TPSMeter.class);

    private int lastTps;
    private int ticks;
    private int targetTps;
    private ScheduledFuture<?> task;

    public TPSMeter(int targetTps) {
        this.targetTps = targetTps;
        this.task = Application.getApplication().getScheduler()
                .scheduleAtFixedRate(this::reset, 1, 1, TimeUnit.SECONDS);
    }

    public void tickOccured() {
        this.ticks++;
    }

    private void reset() {
        this.lastTps = this.ticks;

        if (this.lastTps < this.targetTps - 10) {
            log.warn("Server is running slowly! Current TPS: {}, Target TPS: {}", this.lastTps, this.targetTps);
        }

        this.ticks = 0;
    }

    public int getLastTPS() {
        return lastTps;
    }

    public void disable() {
        if(this.task == null) {
            throw new IllegalStateException("TPSMeter hasn't been started yet!");
        }
        
        this.task.cancel(false);
    }
}
