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

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrmagaw.games.reversi.players.ReversiPlayer;
import mrmagaw.games.reversi.players.Tester;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class Reversi {
    protected final ReversiPlayer[] players = new ReversiPlayer[2];
    protected Board board;
    protected boolean curPlayer = true;

    public Reversi(Class<? extends ReversiPlayer> white, Class<? extends ReversiPlayer> black){
	try {
	    //Randomize black and white?
	    players[0] = white.newInstance();
	    players[1] = black.newInstance();

	    players[0].init(true);
	    players[1].init(false);

	    board = new Board();
	} catch (InstantiationException | IllegalAccessException ex) {
	    Logger.getLogger(Reversi.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private void play(){
	while(!board.isFull() && board.score(true) > 0 && board.score(false) > 0){


	    System.out.println("========");
	    System.out.println(curPlayer ? "WHITE" : "BLACK");
	    System.out.println("========");
	    board.printBoard();
	    System.out.println("========");


	    int xy[] = players[curPlayer ? 0 : 1].getPlay(board.clone());
	    if(xy == null || xy.length != 2){
		System.out.println("Invalid Responce from " + players[curPlayer ? 0 : 1]);
		break;
	    }
	    if(board.play(xy[0], xy[1], curPlayer)){
		curPlayer = board.canPlay(!curPlayer) ? !curPlayer : curPlayer;
		try {
		    //(new Scanner(System.in)).nextLine();
		    sleep(1000);
		} catch (InterruptedException ex) {
		    Logger.getLogger(Reversi.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	int[] s = new int[]{board.score(true), board.score(false)};
	System.out.println("\n\n\n\n========");
	board.printBoard();
	System.out.println("========");
	System.out.println(s[0] > s[1] ? "White wins" : (s[0] == s[1] ? "Tie!" : "Black wins"));
	System.out.println("White: " + s[0] + " | Black: " + s[1]);
	System.out.println("========");
    }

    public static void main(String[] args){
	Reversi game = new Reversi(Tester.class, Tester.class);
	game.play();
    }
}
