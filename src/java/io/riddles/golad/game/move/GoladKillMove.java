package io.riddles.golad.game.move;

import java.awt.Point;

/**
 * io.riddles.golad.game.move.GoladKillMove - Created on 21-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladKillMove extends GoladMove {

    public GoladKillMove(Point coordinate) {
        super(coordinate);
    }

    public MoveType getMoveType() {
        return MoveType.KILL;
    }
}
