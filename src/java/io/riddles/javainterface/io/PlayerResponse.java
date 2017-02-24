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

package io.riddles.javainterface.io;

import io.riddles.javainterface.game.player.PlayerBound;

/**
 * io.riddles.javainterface.io.PlayerResponse - Created on 12-12-16
 *
 * Wrap a player response and playerId together in one Object for the Processor to eat.
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class PlayerResponse implements PlayerBound {
    private String value;
    private int playerId;

    public PlayerResponse(String value, int playerId) {
        this.value = value;
        this.playerId = playerId;
    }

    public String getValue() {
        return this.value;
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
