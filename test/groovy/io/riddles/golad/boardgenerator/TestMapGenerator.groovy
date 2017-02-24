package io.riddles.golad.boardgenerator

import io.riddles.golad.game.board.GoladBoard
import io.riddles.javainterface.configuration.Configuration

/**
 * io.riddles.golad.boardgenerator.TestMapGenerator - Created on 21-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
class TestMapGenerator extends GoladBoardGenerator {

    TestMapGenerator(int width, int height, int playerCount) {
        super(width, height, playerCount)
    }

    static int getFieldsCountForPlayer(GoladBoard board, int playerId) {
        int playerFields = 0
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                if (board.getFields()[x][y] == playerId + "") {
                    playerFields++
                }
            }
        }
        return playerFields
    }
}
