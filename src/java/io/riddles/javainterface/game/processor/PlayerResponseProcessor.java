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

package io.riddles.javainterface.game.processor;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.state.AbstractPlayerState;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.javainterface.io.PlayerResponse;

/**
 * io.riddles.javainterface.game.processor.PlayerResponseProcessor - Created on 12-12-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public abstract class PlayerResponseProcessor<S extends AbstractState, P extends AbstractPlayer> extends AbstractProcessor<S, P> {

    public PlayerResponseProcessor(PlayerProvider<P> playerProvider) {
        super(playerProvider);
    }

    /**
     * Play one round of the game. Return the state that will be the state for the next round.
     * So multiple states may be created here if a round constist of multiple states.
     * @param currentState The current state
     * @param input Player's response from the action request
     * @param roundNumber The current round number
     * @return A new state that with the player's response included in it
     */
    public abstract S createNextStateFromResponse(S currentState, PlayerResponse input, int roundNumber);

    /**
     * Send all updates to the given player that need to be sent in the
     * current (intermediate) state
     * @param currentState The current state as it is right before the updates should be sent
     * @param player The player to send the updates to
     */
    public abstract void sendUpdates(S currentState, P player);

    /**
     * Get the type of action the player should be asked now
     * @param currentState The current state as it is right before the action request
     * @param playerState The current state of the player
     * @return An action type that will be requested of the player
     */
    public abstract Enum getActionType(S currentState, AbstractPlayerState playerState);
}
