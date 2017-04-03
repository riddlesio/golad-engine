package io.riddles.golad.game;

import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.golad.game.processor.GoladProcessor;
import io.riddles.golad.game.state.GoladState;
import io.riddles.golad.game.state.GoladStateSerializer;
import io.riddles.javainterface.game.AbstractGameSerializer;

/**
 * io.riddles.golad.game.GoladSerializer - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladSerializer extends AbstractGameSerializer<GoladProcessor, GoladState> {

    @Override
    public String traverseToString(GoladProcessor processor, GoladState initialState) {
        GoladStateSerializer stateSerializer = new GoladStateSerializer();
        JSONObject game = new JSONObject();

        game = addDefaultJSON(initialState, game, processor);

        JSONObject board = new JSONObject();
        board.put("width", initialState.getBoard().getWidth());
        board.put("height", initialState.getBoard().getHeight());

        game.getJSONObject("settings").put("board", board);

        JSONArray states = new JSONArray();
        states.put(stateSerializer.traverseToJson(initialState));

        GoladState state = initialState;
        while (state.hasNextState()) {
            state = (GoladState) state.getNextState();
            states.put(stateSerializer.traverseToJson(state));
        }

        game.put("states", states);

        return game.toString();
    }
}
