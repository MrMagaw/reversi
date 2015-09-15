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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import mrmagaw.games.reversi.Board;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class RandomPlayer implements ReversiPlayer{
    private  String name;
    private boolean colour;

    private final Random rand = new Random();



	@Override
	public void init(boolean white) {
	    colour = white;
	    name = white ? "White_Tester" : "Black_Tester";
	}

	@Override
	public int[] getPlay(Board b) {
	    ArrayList<Integer[]> plays = b.getPlays(colour);
	    int index = rand.nextInt(plays.size());
	    return new int[]{plays.get(index)[0], plays.get(index)[1]};
	}

	@Override
	public String getName() {
	    return name;
	}

    }
