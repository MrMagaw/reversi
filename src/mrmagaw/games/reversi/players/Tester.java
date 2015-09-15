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
public class Tester implements ReversiPlayer{
    private  String name;
    private boolean colour;

    private final Random rand = new Random();

    public ArrayList<Integer[]> getPlays(Board b){
	ArrayList<Integer[]> plays = new ArrayList();

	BigInteger board = b.getBoard();

	for(int bitPos = 0; bitPos < 128; bitPos += 2){
	    //Filter out any occupied slots
	    while(board.testBit(bitPos) || board.testBit(bitPos + 1))
		bitPos += 2;

	    int y = bitPos >> 4;
	    int x = (bitPos >> 1) - (y << 3);

	    //8 driections to search.
	    for(int i = 7; i >= 0; --i){
		int curPos = bitPos + Board.flipOffsets[i];

		if(curPos < 0 || curPos >= 128 || !board.testBit(curPos + (colour ? 1 : 0))) continue;

		boolean breakout = false;
		do{

		    {//Stop rows from being indifferent
			int curY = curPos >> 4;
			int curX = (curPos >> 1) - (curY << 3);
			if((i == 3 || i == 4) && (curY != y)) break;
			if((i == 0 || i == 5) && (curX > x)) break;
			if((i == 2 || i == 7) && (curX < x)) break;
		    }

		    if(board.testBit(curPos + (colour ? 0 : 1))){
			int cy = bitPos >> 4;
			plays.add(new Integer[]{(bitPos >> 1) - (cy << 3), cy});
			break;
		    }else if(!board.testBit(curPos + (colour ? 1 : 0)))
			break;
		    curPos += Board.flipOffsets[i]; //Next
		}while(curPos >= 0 && curPos < 128);
		if(breakout) break;
	    }
	}
	return plays;
    }

	@Override
	public void init(boolean white) {
	    colour = white;
	    name = white ? "White_Tester" : "Black_Tester";
	}

	@Override
	public int[] getPlay(Board b) {
	    ArrayList<Integer[]> plays = getPlays(b);
	    int index = rand.nextInt(plays.size());
	    return new int[]{plays.get(index)[0], plays.get(index)[1]};
	}

	@Override
	public String getName() {
	    return name;
	}

    }
