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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static mrmagaw.games.reversi.Globals.*;
import mrmagaw.games.reversi.gui.Gui;
import mrmagaw.games.reversi.players.*;

/**
 *
 * @author MrMagaw <MrMagaw@gmail.com>
 */
public class Reversi {
    protected final ReversiPlayer[] players = new ReversiPlayer[2];
    protected Board board;
    protected boolean curPlayer = true;
    protected Gui gui;
    protected int winner = -1;

    public Reversi(Class<? extends ReversiPlayer> white, Class<? extends ReversiPlayer> black){
	try {
	    //Randomize black and white?
	    players[0] = white.newInstance();
	    players[1] = black.newInstance();

	    players[0].init(true, gui);
	    players[1].init(false, gui);

	    board = new Board();
	    if(SHOW_GUI){
		gui = Gui.init();
		gui.updateTiles(board, true);
	    }
	} catch (InstantiationException | IllegalAccessException ex) {
	    Logger.getLogger(Reversi.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public Reversi(Class<? extends ReversiPlayer> white, Class<? extends ReversiPlayer> black, Gui gui){
	try {
	    //Randomize black and white?
	    players[0] = white.newInstance();
	    players[1] = black.newInstance();

	    players[0].init(true, gui);
	    players[1].init(false, gui);

	    board = new Board();
	    if(SHOW_GUI){
		this.gui = gui;
		gui.updateTiles(board, true);
	    }
	} catch (InstantiationException | IllegalAccessException ex) {
	    Logger.getLogger(Reversi.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private void play(){
	while(!board.isDone() && board.canPlay(curPlayer)){
	    if(VERBOSE){
		System.out.println("========");
		System.out.println(curPlayer ? "WHITE" : "BLACK");
		System.out.println("========");
		board.printBoard();
		System.out.println("========");
	    }

	    int xy[] = players[curPlayer ? 0 : 1].getPlay(board.clone());
	    if(xy == null || xy.length != 2){
		System.out.println("Invalid Responce from " + players[curPlayer ? 0 : 1]);
		break;
	    }
	    if(board.play(xy[0], xy[1], curPlayer)){
		curPlayer = board.canPlay(!curPlayer) ? !curPlayer : curPlayer;
		if(SHOW_GUI){
		    try {
			sleep(DELAY);
		    } catch (InterruptedException ex) {
			Logger.getLogger(Reversi.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    gui.updateTiles(board, curPlayer);
		}
	    }
	}
	int[] s = new int[]{board.score(true), board.score(false)};
	if(VERBOSE){
	    System.out.println("\n\n\n\n========");
	    board.printBoard();
	    System.out.println("========");
	}
	winner = s[0] > s[1] ? 1 : (s[0] == s[1] ? 0 : 2);
	System.out.println(winner == 1 ? "White wins" : (winner == 0 ? "Tie!" : "Black wins"));
	System.out.println("White: " + s[0] + " | Black: " + s[1]);
	if(SHOW_GUI){
	    gui.winner(winner);
	    JOptionPane.showMessageDialog(null, (winner == 1 ? "White wins" : (winner == 0 ? "Tie!" : "Black wins")) + "\nWhite: " + s[0] + " | Black: " + s[1]);
	}
    }

    public static void main(String[] args){
	int[] scores = new int[]{0, 0, 0};
	int runs = 1;
	Reversi game = new Reversi(RandomPlayer.class, RandomPlayer.class);
	for(int i = runs - 1; i >= 0; --i){
	    game = new Reversi(HumanPlayer.class, ReversiBeeMo.class, game.gui);
	    game.play();
	    ++scores[game.winner];
	    if(i % 10 == 0)
		System.out.println(String.format("%d/%d (%01.1f%%)", runs-i, runs, (((float)runs-i)/runs*100)));
	}
	System.out.println(String.format(
		"Out of %d games\n%d (%f%%) tied\n%d (%f%%) White won\n%d (%01.1f%%) Black won",
		runs,
		scores[0], ((float)scores[0]/runs*100),
		scores[1], ((float)scores[1]/runs*100),
		scores[2], ((float)scores[2]/runs*100)));
    }
}
