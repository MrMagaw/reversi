package mrmagaw.ai.beemo.algorithm;

import java.math.BigInteger;
import mrmagaw.ai.beemo.AbstractBeeMo;

public class OBF<Game, Move, Pattern> extends Algorithm<Game, Move, Pattern>{
    public OBF(AbstractBeeMo<Game, Move, Pattern> a){super(a);}

    @Override
    public Move search(final Game game) {
	Move bestMove = null;
	BigInteger bestScore = BigInteger.ZERO;

	for (Move move : overlord.getMoves(game)) {
            Game newGame = overlord.tryMove(game, move);
	    BigInteger score = overlord.scoreGame(newGame);
            if(bestMove == null || score.compareTo(bestScore) > 0){
                bestScore = score;
                bestMove = move;
	    }
        }

	return bestMove;
    }
}