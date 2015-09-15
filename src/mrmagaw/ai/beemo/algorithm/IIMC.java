package mrmagaw.ai.beemo.algorithm;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import mrmagaw.ai.beemo.AbstractBeeMo;

public class IIMC<Game, Move, Pattern> extends Algorithm<Game, Move, Pattern>{
    public IIMC(AbstractBeeMo<Game, Move, Pattern> a){ super(a);}

    @Override
    public Move search(final Game game){
	Move bestMove = null;
	BigInteger bestScore = BigInteger.valueOf(Long.MIN_VALUE);

        for(Move move : overlord.getMoves(game)){
            Game newGame = overlord.tryMove(game, move);

            //SIMULATE Games
            BigInteger score = overlord.simulate(newGame);

            if(score.compareTo(bestScore) > 0){
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }
}