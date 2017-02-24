/*
 * Copyright 2016 riddles.io (developers@riddles.io)
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

package io.riddles.javainterface.game.state;

import io.riddles.javainterface.game.player.PlayerBound;

import java.util.ArrayList;

/**
 * io.riddles.javainterface.engine.state.AbstractState - Created on 6-12-16
 *
 * [description]
 *
 * @author Josot de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public abstract class AbstractPlayerState<M> implements PlayerBound {
    private M move;
    private int playerId;

    public AbstractPlayerState(int playerId) {
        this.playerId = playerId;
    }

    public AbstractPlayerState(int playerId, M move) {
        this.playerId = playerId;
        this.move = move;
    }

    public void setMove(M move) {
        this.move = move;
    }

    public M getMove() {
        return this.move;
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
