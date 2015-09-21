package mrmagaw.ai.beemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import mrmagaw.ai.beemo.algorithm.Algorithm;
import mrmagaw.ai.beemo.algorithm.Simulator;


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
 * @param <Game>
 * @param <Move>
 * @param <Pattern>
 *
 *
 */

public abstract class AbstractBeeMo<Game, Move, Pattern>{
    public final int simSampleSize;
    protected Algorithm<Game, Move, Pattern> algo;
    protected Simulator<Game, Move, Pattern> sim;

    public AbstractBeeMo(){
	simSampleSize = 320;
    }

    public abstract Game cloneGame(final Game game);
    public abstract ArrayList<Move> getMoves(final Game game);
    public abstract Game tryMove(final Game game, final Move move);
    public abstract boolean gameDone(final Game game);
    public abstract BigDecimal scoreGame(final Game game);
    public abstract Game nextTurn(final Game game);
    public abstract Game freshGame();

    public abstract Pattern patternize(final Game game);

    public abstract void debug(final Game game);

    //Returns >0 if game1 is better, 0 if they're equal, <0 if game2 is better
    public BigDecimal compareGames(final Game game1, final Game game2){
	return scoreGame(game1).subtract(scoreGame(game2));
    }

    public BigDecimal simulate(final Game game){
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
