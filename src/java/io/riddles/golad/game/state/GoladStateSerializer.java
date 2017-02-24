package io.riddles.golad.game.state;

import org.json.JSONArray;
import org.json.JSONObject;

import io.riddles.golad.game.board.GoladBoard;
import io.riddles.golad.game.move.GoladMoveSerializer;
import io.riddles.javainterface.game.state.AbstractStateSerializer;

/**
 * io.riddles.golad.game.state.GoladStateSerializer - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladStateSerializer extends AbstractStateSerializer<GoladState> {

    @Override
    public String traverseToString(GoladState state) {
        return visitState(state).toString();
    }

    @Override
    public JSONObject traverseToJson(GoladState state) {
        return visitState(state);
    }

    private JSONObject visitState(GoladState state) {
        GoladMoveSerializer moveSerializer = new GoladMoveSerializer();

        JSONObject stateObj = new JSONObject();
        GoladBoard board = state.getBoard();

        JSONArray players = new JSONArray();
        for (GoladPlayerState playerState : state.getPlayerStates()) {
            JSONObject playerObj = new JSONObject();
            playerObj.put("id", playerState.getPlayerId());
            playerObj.put("score", playerState.getFieldCount());

            if (playerState.getMove() != null) {
                playerObj.put("move", moveSerializer.traverseToJson(playerState.getMove()));
            } else {
                playerObj.put("move", JSONObject.NULL);
            }

            players.put(playerObj);
        }

        stateObj.put("round", state.getRoundNumber());
        stateObj.put("board", board.toString());
        stateObj.put("boardPreview", board.previewToString());
        stateObj.put("players", players);

        return stateObj;
    }
}
