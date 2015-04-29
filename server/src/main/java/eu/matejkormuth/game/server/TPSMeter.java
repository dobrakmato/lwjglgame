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
