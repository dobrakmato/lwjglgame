/**
 * shared - Multiplayer Java game engine.
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
package eu.matejkormuth.game.shared.content;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Content {

    private static Path root;

    void setRoot(Path path) {
        root = path;
    }

    public static <T> T load(String first, String... more) {
        return load(getPath(first, more));
    }

    public static <T> T load(Path path) {
        // TODO: Implement loading.
        return null;
    }

    public static Path getPath(String first, String... more) {
        return root.resolve(Paths.get(first, more));
    }

}
