package io.riddles.javainterface.game

import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.state.TestState
import org.json.JSONObject

/**
 * Created by joost
 */
public class TestSerializer extends AbstractGameSerializer<TestProcessor,TestState> {

    TestSerializer(PlayerProvider playerProvider) {
        super(playerProvider);
    }

    public JSONObject getDefaultJSON(TestProcessor processor, TestState initialState) {
        JSONObject game = new JSONObject();
        game = addDefaultJSON(initialState, game, processor);
    }

    @Override
    public String traverseToString(TestProcessor processor, TestState initialState) {
        JSONObject game = new JSONObject();

        game = addDefaultJSON(initialState, game, processor);
        return game.toString();
    }
}