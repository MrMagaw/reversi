package mrmagaw.ai.beemo.algorithm;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mrmagaw.ai.beemo.AbstractBeeMo;

public class Simulator<Game, Move, Pattern> {
    private class Gamer extends Thread {
        private final Game game;
        private final int offset;
        private int numSimulations;
        private BigInteger totalScore = BigInteger.ZERO;

        public Gamer(Game game, int numSimulations, int offset){
            this.game = game;
            this.numSimulations = numSimulations + offset;
            this.offset = offset;
        }

        @Override
        public void run() {
            while(numSimulations-- > offset){
		Game g = overlord.cloneGame(game);

                while (!overlord.gameDone(g)){
		    g = overlord.nextTurn(g);
		    if(overlord.gameDone(g))
			break;
		    Move m = isearch.search(g);
		    if(m == null){
			overlord.debug(g);
			try {
			    Thread.sleep(10000);
			} catch (InterruptedException ex) {
			    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
			}
		    }
		    g = overlord.tryMove(g, m);
		}

                totalScore = totalScore.add(overlord.scoreGame(g));
            }
        }
    }

    private BigInteger totalScore = BigInteger.ZERO;
    private final AbstractBeeMo<Game, Move, Pattern> overlord;
    private final Algorithm<Game, Move, Pattern> isearch;
    public Simulator(AbstractBeeMo<Game, Move, Pattern> overlord, Algorithm<Game, Move, Pattern> internalSearch){
	this.overlord = overlord;
	isearch = internalSearch;
    }

    public BigInteger run(Game game, int numSimulations){
	/*
	Remove this later
	*/
/*
	//if(numSimulations == 1){
	    Gamer gamer = new Gamer(game, numSimulations, 0);
	    gamer.start();
	try {
	    gamer.join();
	} catch (InterruptedException ex) {
	    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
	}
	    return gamer.totalScore;
	//}
*/
	/*
	End remove
	*/
        //Number of threads used is 16 right now...
        Gamer[] gamers = (Gamer[])Array.newInstance(Gamer.class, 16);
        int simPerThread = numSimulations >> 4;
        int extraThread = numSimulations - (simPerThread << 4);

        for(int i = 0; i < gamers.length; ++i){
            gamers[i] = new Gamer(game, (i < extraThread) ? simPerThread : simPerThread + 1, (i * simPerThread));
            gamers[i].start();
        }
        for(int i = 0; i < gamers.length; ++i){
            try{
                gamers[i].join();
                totalScore = totalScore.add(gamers[i].totalScore);
            }catch(InterruptedException ex){System.err.println("Simulator interrupted!");}
        }
	return totalScore;
    }
}
