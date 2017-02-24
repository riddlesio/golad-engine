package io.riddles.golad.game.move;

/**
 * io.riddles.golad.game.move.ActionType - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public enum ActionType {
    MOVE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
