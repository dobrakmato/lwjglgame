/**
 * client - Multiplayer Java game engine.
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
package eu.matejkormuth.game.client;

public class CVarDefaults {
    static void init(Application app) {
        app.getCvar("displayHeight").setVal(1280);
        app.getCvar("displayWidth").setVal(720);
        app.getCvar("fullscreen").setVal(true);
        app.getCvar("musicVolume").setVal(0.75f);
        app.getCvar("soundVolume").setVal(1f);
        app.getCvar("mouseSensitivity").setVal(0.5f);
    }
}
