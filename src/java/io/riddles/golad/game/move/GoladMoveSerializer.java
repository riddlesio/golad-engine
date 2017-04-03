package io.riddles.golad.game.move;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Point;

import io.riddles.javainterface.serialize.Serializer;

/**
 * io.riddles.golad.game.move.GoladMoveSerializer - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladMoveSerializer extends Serializer<GoladMove> {

    @Override
    public String traverseToString(GoladMove traversible) {
        return visitMove(traversible).toString();
    }

    @Override
    public JSONObject traverseToJson(GoladMove traversible) {
        return visitMove(traversible);
    }

    private JSONObject visitMove(GoladMove move) {
        JSONObject moveObj = new JSONObject();

        if (move.getMoveType() != null) {
            moveObj.put("moveType", move.getMoveType().toString());
        } else {
            moveObj.put("moveType", JSONObject.NULL);
        }

        if (move.getException() != null) {
            moveObj.put("exception", move.getException().getMessage());
        } else {
            moveObj.put("exception", JSONObject.NULL);
        }

        if (move instanceof GoladKillMove) {
            JSONObject killCell = visitPoint(move.getCoordinate());
            moveObj.put("killCell", killCell);
        } else if (move instanceof  GoladBirthMove) {
            JSONObject birthCell = visitPoint(move.getCoordinate());
            moveObj.put("birthCell", birthCell);

            JSONArray sacrificeCells = new JSONArray();
            for (Point sacrifice : ((GoladBirthMove) move).getSacrificeCoordinates()) {
                sacrificeCells.put(visitPoint(sacrifice));
            }

            moveObj.put("sacrificeCells", sacrificeCells);
        }

        return moveObj;
    }
}
