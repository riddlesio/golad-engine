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

import io.riddles.golad.game.move.GoladMove;
import io.riddles.javainterface.game.state.AbstractPlayerState;

/**
 * io.riddles.golad.game.state.GoladPlayerState - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladPlayerState extends AbstractPlayerState<GoladMove> {

    private int fieldCount;
    private GoladMove lastMove;

    public GoladPlayerState(int playerId) {
        super(playerId);
        this.fieldCount = -1;
    }

    public GoladPlayerState(GoladPlayerState playerState) {
        super(playerState.getPlayerId());
        this.fieldCount = -1;
        this.lastMove = playerState.getLastMove();
    }

    public int getFieldCount() {
        return this.fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public void setLastMove(GoladMove move) {
        this.lastMove = move;
    }

    public GoladMove getLastMove() {
        return this.lastMove;
    }
}
