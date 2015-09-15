package mrmagaw.ai.beemo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import mrmagaw.ai.beemo.algorithm.OBF;
import mrmagaw.ai.beemo.algorithm.Simulator;
import mrmagaw.games.reversi.players.ReversiPlayer;


/**
 *
 * @author Karo & William
 *                                   _________
 *                                ||.     . ||
 *                                ||   ‿    ||
 *_|_|_|    _|      _|    _|_|    ||        ||
 *_|    _|  _|_|  _|_|  _|    _| /||-----AB-||\
 *_|_|_|    _|  _|  _|  _|    _|  ||===   . ||
 *_|    _|  _|      _|  _|    _|  || +  o  0||
 *_|_|_|    _|      _|    _|_|    ||________||
 *                                   |    |
 *plays poker
 *
 *
 */

public abstract class AbstractBeeMo<Game, Move, Pattern>{
    public final int simSampleSize;
    protected final Simulator<Game, Move, Pattern> sim;

    public AbstractBeeMo(){
	simSampleSize = 320;
	sim = new Simulator(this, new OBF(this));
    }

    public abstract Game cloneGame(final Game game);
    public abstract ArrayList<Move> getMoves(final Game game);
    public abstract Game tryMove(final Game game, final Move move);
    public abstract boolean gameDone(final Game game);
    public abstract BigInteger scoreGame(final Game game);
    public abstract Game nextTurn(final Game game);
    public abstract Game freshGame();

    public abstract Pattern patternize(final Game game);

    public abstract void debug(final Game game);

    //Returns >0 if game1 is better, 0 if they're equal, <0 if game2 is better
    public BigInteger compareGames(final Game game1, final Game game2){
	return scoreGame(game1).subtract(scoreGame(game2));
    }

    public BigInteger simulate(final Game game){
	return sim.run(game, simSampleSize);
    }

    @Override
    public String toString(){
	return "AbstractBeeMo";
    }

    public String getName(){
        return this.toString();
    }
}
