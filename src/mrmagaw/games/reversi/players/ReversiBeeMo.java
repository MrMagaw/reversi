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
import mrmagaw.ai.beemo.AbstractBeeMo;
import mrmagaw.ai.beemo.algorithm.*;
import mrmagaw.games.reversi.Board;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class ReversiBeeMo extends AbstractBeeMo<Board, Integer[], BigInteger> implements ReversiPlayer {
    private boolean white;
    private Algorithm<Board, Integer[], BigInteger> algo;

    private ReversiBeeMo evilMo;

    public ReversiBeeMo(){
	super();
    }

    private ReversiBeeMo(final boolean white){
	super();
	this.white = white;
	algo = new OBF(this);
    }

    @Override
    public void init(final boolean white) {
	this.white = white;
	algo = new IIMC(this);
	evilMo = new ReversiBeeMo(!white);
    }

    @Override
    public Board cloneGame(final Board game) { return game.clone(); }

    @Override
    public boolean gameDone(final Board game) { return game.isDone(); }

    @Override
    public ArrayList<Integer[]> getMoves(final Board game) { return game.getPlays(white); }

    @Override
    public Board tryMove(final Board game, final Integer[] move) {
	/*
	This should be removed...
	*/
	if(move == null){
	    //Find out why we are getting null moves!
	    return game;
	}

	Board b = game.clone();
	b.play(move[0], move[1], white, true);
	return b;
    }


    @Override
    public BigInteger scoreGame(final Board game) {
	//I should improve this.
	return BigInteger.valueOf(game.score(white) - game.score(!white));
    }

    @Override
    public int[] getPlay(final Board b) {
	Integer[] move = algo.search(b);
	return new int[]{move[0], move[1]};
    }

    @Override
    public Board nextTurn(Board game) {
	if(game.canPlay(!white)) {
	    do{
		int[] move = evilMo.getPlay(game);
		game = evilMo.tryMove(game, new Integer[]{move[0], move[1]});
	    }while(!game.canPlay(white) && game.canPlay(!white));
	}
	return game;
    }

    @Override
    public void debug(final Board game){
	System.err.println("========\nisWhite: " + white + "\n========");
	game.printBoard();
    }

    @Override
    public String getName() {
	return "ReversiBeeMo";
    }

    @Override
    public BigInteger patternize(final Board game) {
	return game.getBoard();
    }

    @Override
    public Board freshGame() { return new Board(); }
}
