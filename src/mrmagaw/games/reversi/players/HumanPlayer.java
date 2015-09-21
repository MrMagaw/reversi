/*
 * Copyright (C) 2015 MrMagaw <MrMagaw@gmail.com>
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
package mrmagaw.games.reversi.players;

import java.util.logging.Level;
import java.util.logging.Logger;
import mrmagaw.games.reversi.Board;
import mrmagaw.games.reversi.gui.Gui;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class HumanPlayer implements ReversiPlayer{
    private boolean white;
    private Gui gui;

    @Override
    public void init(boolean white, Gui gui) {
	this.white = white;
	this.gui = gui;
    }

    @Override
    public int[] getPlay(Board b) {
	gui.clearLastTile();
	int[] resp = null;
	while((resp = gui.getLastTile()) == null){
	    try {
		Thread.sleep(50);
	    } catch (InterruptedException ex) {
		Logger.getLogger(HumanPlayer.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return resp;
    }

    @Override
    public String getName() {
	return "Some Guy Infront of the Keyboard";
    }

}
