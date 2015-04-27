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
package eu.matejkormuth.game.client.gui;

import java.awt.Color;

public class Window extends Component {

    private Color background;
    private Container container;
    
    public Window(Component parent) {
        // Top level component has no parent.
        super(null);
        this.container = new Container(this);
        this.container.setPosition(0, 0);
        this.background = Color.lightGray;
    }

    @Override
    public void draw() {
        drawBorder();
        drawContainer();
    }

    private void drawBorder() {
        // TODO Auto-generated method stub

    }

    private void drawContainer() {
        this.container.draw();
    }
    
    public Color getBackground() {
        return background;
    }
    
    public void setBackground(Color background) {
        this.background = background;
    }
}
