package mrmagaw.ai.beemo.algorithm;

import mrmagaw.ai.beemo.AbstractBeeMo;

public abstract class Algorithm<Game, Move, Pattern> {
    protected final AbstractBeeMo<Game, Move, Pattern> overlord;
    Algorithm(AbstractBeeMo<Game, Move, Pattern> a){overlord = a;}

    public abstract Move search(final Game game);
}
