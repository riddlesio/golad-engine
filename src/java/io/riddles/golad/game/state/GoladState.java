/*
 * Copyright 2017 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.golad.game.state;

import java.util.ArrayList;

import io.riddles.golad.game.board.GoladBoard;
import io.riddles.javainterface.game.state.AbstractState;

/**
 * io.riddles.golad.game.state.GoladState - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladState extends AbstractState<GoladPlayerState> {

    private GoladBoard board;

    // For initial state only
    public GoladState(ArrayList<GoladPlayerState> playerStates, GoladBoard board) {
        super(null, playerStates, 0);
        this.board = board;
    }

    private GoladState(GoladState previousState, ArrayList<GoladPlayerState> playerStates, int roundNumber) {
        super(previousState, playerStates, roundNumber);
        this.board = new GoladBoard(previousState.getBoard());
    }

    public GoladState createNextState(int roundNumber) {
        // Create new player states from current player states
        ArrayList<GoladPlayerState> playerStates = new ArrayList<>();
        for (GoladPlayerState playerState : this.getPlayerStates()) {
            playerStates.add(new GoladPlayerState(playerState));
        }

        // Create new state from current state
        return new GoladState(this, playerStates, roundNumber);
    }

    public void updatePlayerStates() {
        int[] playerFieldCount = this.board.countPlayerFields();

        for (GoladPlayerState playerState : this.getPlayerStates()) {
            int fieldCount = playerFieldCount[playerState.getPlayerId()];
            playerState.setFieldCount(fieldCount);
        }
    }

    public GoladBoard getBoard() {
        return this.board;
    }
}
