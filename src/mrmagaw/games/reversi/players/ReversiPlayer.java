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

import mrmagaw.games.reversi.Board;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public interface ReversiPlayer {
    //Will be called on a new game; true = white, false = black.
    public void init(boolean white);
    public int[] getPlay(Board b);
    public String getName();
}
