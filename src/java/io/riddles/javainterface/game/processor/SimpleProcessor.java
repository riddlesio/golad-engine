package io.riddles.javainterface.game.processor;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * io.riddles.javainterface.game.processor.SimpleProcessor - Created on 12-12-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public abstract class SimpleProcessor<S extends AbstractState, P extends AbstractPlayer> extends AbstractProcessor<S, P> {

    public SimpleProcessor(PlayerProvider<P> playerProvider) {
        super(playerProvider);
    }

    /**
     * Play one round of the game. Return the state that will be the state for the next round.
     * So multiple states may be created here if a round constist of multiple states.
     * @param inputState The current state
     * @param roundNumber The current round number
     * @return The state that will be the start of the next round
     */
    public abstract S createNextState(S inputState, int roundNumber);
}
