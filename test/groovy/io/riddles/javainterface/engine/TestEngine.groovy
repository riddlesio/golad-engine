package io.riddles.javainterface.engine

import io.riddles.javainterface.configuration.Configuration
import io.riddles.javainterface.game.TestProcessor
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.player.TestPlayer
import io.riddles.javainterface.game.state.TestState
import io.riddles.javainterface.io.FileIOHandler

/**
 * Created by joost.
 */
class TestEngine extends AbstractEngine<TestProcessor, TestPlayer, TestState> {

    TestEngine(PlayerProvider<TestPlayer> playerProvider, String wrapperInput) {
        super(playerProvider, null);
        this.ioHandler = new FileIOHandler(wrapperInput);
    }

    @Override
    protected Configuration getDefaultConfiguration() {
        return new Configuration()
    }

    @Override
    protected TestState getInitialState() {
        return new TestState()
    }

    @Override
    protected TestPlayer createPlayer(int id) {
        return new TestPlayer()
    }

    @Override
    protected void sendSettingsToPlayer(TestPlayer player) {

    }

    @Override
    protected TestProcessor createProcessor() {
        return new TestProcessor()
    }

    @Override
    protected GameLoopInterface createGameLoop() {
        return new SimpleGameLoop()
    }

    @Override
    protected String getPlayedGame(TestState initialState) {
        return "";
    }
}