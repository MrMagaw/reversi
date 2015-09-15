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
package mrmagaw.games.reversi;

import java.math.BigInteger;
import java.util.ArrayList;
import static mrmagaw.games.reversi.Globals.*;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class Board {
    //0 = Empty, 1 = White, 2 = Black, 3 = ERROR
    //Each pair of bits corresponds to a single tile
    //16 bits = 1 row
    //128 bits = board
    protected BigInteger
	    board = new BigInteger("1800240000000000000", 16);

    public static final BigInteger[]
	    COLOUR = {
		new BigInteger("55555555555555555555555555555555", 16),
		new BigInteger("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 16)
	    };

    public static final int[] flipOffsets = {-18, -16, -14, -2, 2, 14, 16, 18};


    public Board(){}
    public Board(BigInteger b){
	board = b;
    }

    public static void printBoard(BigInteger b){
	for(int y = 0; y < 8; y++)
	    System.out.println(new StringBuffer(
		    String.format("%8s",
			b.shiftRight(y << 4).and(BigInteger.valueOf(0xFFFFL)).toString(4)
		    )
			.replaceAll("1", "W")
			.replaceAll("2", "B")
			.replaceAll("0| ", "-")
	    ).reverse());
    }

    //0=empty, 1=White, 2=Black
    public int getPos(int x, int y){
	int pos = (x << 1) + (y << 4);
	return board.testBit(pos) ? 1 : (board.testBit(pos + 1) ? 2 : 0);
    }

    public void printBoard(){
	Board.printBoard(board);
    }

    public BigInteger getBoard(){
	return board;
    }

    public int score(boolean white){
	return board.and(COLOUR[white ? 0 : 1]).bitCount();
    }

    public boolean isDone(){
	return board.bitCount() == 64 ||
		score(false) == 0 || score(true) == 0 ||
		(!canPlay(true) && !canPlay(false));
    }

    public boolean canPlay(boolean white){
	for(int bitPos = 0; bitPos < 128; bitPos += 2){
	    //Filter out any occupied slots
	    while(board.testBit(bitPos) || board.testBit(bitPos + 1))
		bitPos += 2;
	    if(bitPos >= 128)
		break;
	    int y = bitPos >> 4;
	    int x = (bitPos >> 1) - (y << 3);

	    //8 driections to search.
	    for(int i = 7; i >= 0; --i){
		int curPos = bitPos + Board.flipOffsets[i];

		if(curPos < 0 || curPos >= 128 || !board.testBit(curPos + (white ? 1 : 0))) continue;

		do{
		    {//Stop rows from being indifferent
			int curY = curPos >> 4;
			int curX = (curPos >> 1) - (curY << 3);
			if((i == 3 || i == 4) && (curY != y)) break;
			if((i == 0 || i == 5) && (curX > x)) break;
			if((i == 2 || i == 7) && (curX < x)) break;
		    }

		    if(board.testBit(curPos + (white ? 0 : 1))){
			return true;
		    }else if(!board.testBit(curPos + (white ? 1 : 0)))
			break;
		    curPos += Board.flipOffsets[i]; //Next
		}while(curPos >= 0 && curPos < 128);
	    }
	}
	return false;
    }

    public ArrayList<Integer[]> getPlays(boolean white){
	ArrayList<Integer[]> plays = new ArrayList();

	for(int bitPos = 0; bitPos < 128; bitPos += 2){
	    //Filter out any occupied slots
	    while(board.testBit(bitPos) || board.testBit(bitPos + 1))
		bitPos += 2;
	    if(bitPos >= 128)
		break;

	    int y = bitPos >> 4;
	    int x = (bitPos >> 1) - (y << 3);

	    //8 driections to search.
	    for(int i = 7; i >= 0; --i){
		int curPos = bitPos + Board.flipOffsets[i];

		if(curPos < 0 || curPos >= 128 || !board.testBit(curPos + (white ? 1 : 0))) continue;

		do{

		    {//Stop rows from being indifferent
			int curY = curPos >> 4;
			int curX = (curPos >> 1) - (curY << 3);
			if((i == 3 || i == 4) && (curY != y)) break;
			if((i == 0 || i == 5) && (curX > x)) break;
			if((i == 2 || i == 7) && (curX < x)) break;
		    }

		    if(board.testBit(curPos + (white ? 0 : 1))){
			int cy = bitPos >> 4;
			plays.add(new Integer[]{(bitPos >> 1) - (cy << 3), cy});
			break;
		    }else if(!board.testBit(curPos + (white ? 1 : 0)))
			break;
		    curPos += Board.flipOffsets[i]; //Next
		}while(curPos >= 0 && curPos < 128);
	    }
	}
	return plays;
    }

    public boolean play(int x, int y, boolean white){
	return play(x, y, white, false);
    }

    public boolean play(int x, int y, boolean white, boolean quiet){
	//Out of bounds
	if(x > 7 || y > 7 || x < 0 || y < 0){
	    if(!quiet) System.out.println("Cannot play in spot {" + x + ", " + y +"}. Invalid position.");
	    return false;
	}

	//Generate the bit position
	int bitPos = (x << 1) + (y << 4);
	if(DEBUG) System.out.println("x: " + x + ", y: " + y + ", bitPos: " + bitPos);

	if(board.testBit(bitPos) || board.testBit(bitPos + 1)){
	//Occupied board space
	    if(!quiet) System.out.println("Cannot play in spot {" + x + ", " + y +"}. Space already occupied.");
	    return false;
	}

	boolean validFlip = false;

	//Determine if valid, flipping spot.
	//8 driections to search.
	for(int i = 7; i >= 0; --i){
	    int curPos = bitPos + flipOffsets[i];

	    if(curPos < 0 || curPos >= 128 || !board.testBit(curPos + (white ? 1 : 0))) continue;

	    do{

		{//Stop rows from being indifferent
		    int curY = curPos >> 4;
		    int curX = (curPos >> 1) - (curY << 3);
		    if((i == 3 || i == 4) && (curY != y)) break;
		    if((i == 0 || i == 5) && (curX > x)) break;
		    if((i == 2 || i == 7) && (curX < x)) break;
		}
		if(board.testBit(curPos + (white ? 0 : 1))){
		    validFlip = true;
		    while(curPos != bitPos){
			board = board.setBit(curPos + (white ? 0 : 1));
			board = board.clearBit(curPos + (white ? 1 : 0));
			curPos -= flipOffsets[i]; //Next
		    }
		    break;
		}else if(!board.testBit(curPos + (white ? 1 : 0)))
		    break;
		curPos += flipOffsets[i]; //Next
	    }while(curPos >= 0 && curPos < 128);
	}

	if(!validFlip){
	    //No flips
	    if(!quiet) System.out.println("Cannot play in spot {" + x + ", " + y +"}. Not valid play.");
	    return false;
	}

	board = board.setBit(bitPos + (white ? 0 : 1));

	if(DEBUG) printBoard();
	if(DEBUG) System.out.println("================");

	return true;
    }

    @Override
    public Board clone(){
	return new Board(board);
    }
}
