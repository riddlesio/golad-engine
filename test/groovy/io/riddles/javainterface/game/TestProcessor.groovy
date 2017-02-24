package io.riddles.javainterface.game

import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.player.TestPlayer
import io.riddles.javainterface.game.processor.AbstractProcessor
import io.riddles.javainterface.game.state.TestState

/**
 * Created by joost on 1/23/17.
 */
class TestProcessor extends AbstractProcessor<TestState, TestPlayer> {

    protected TestProcessor(PlayerProvider<TestPlayer> playerProvider) {
        super(playerProvider)
    }

    @Override
    boolean hasGameEnded(TestState state) {
        return false
    }

    @Override
    Integer getWinnerId(TestState state) {
        return null
    }

    @Override
    double getScore(TestState state) {
        return 0
    }
}
