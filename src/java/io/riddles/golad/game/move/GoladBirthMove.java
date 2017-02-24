package io.riddles.golad.game.move;

import java.awt.Point;
import java.util.ArrayList;

/**
 * io.riddles.golad.game.move.GoladBirthMove - Created on 21-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladBirthMove extends GoladMove {

    private ArrayList<Point> sacrificeCoordinates;

    public GoladBirthMove(Point coordinate, ArrayList<Point> sacrificeCoordinates) {
        super(coordinate);
        this.sacrificeCoordinates = sacrificeCoordinates;
    }

    public MoveType getMoveType() {
        return MoveType.BIRTH;
    }

    public ArrayList<Point> getSacrificeCoordinates() {
        return this.sacrificeCoordinates;
    }
}
