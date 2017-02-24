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

package io.riddles.javainterface.engine;

import io.riddles.javainterface.game.player.AbstractPlayer;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.processor.AbstractProcessor;
import io.riddles.javainterface.game.processor.PlayerResponseProcessor;
import io.riddles.javainterface.game.state.AbstractPlayerState;
import io.riddles.javainterface.game.state.AbstractState;
import io.riddles.javainterface.io.PlayerResponse;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * io.riddles.javainterface.engine.SimpleGameLoop - Created on 5-12-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class TurnBasedGameLoop<S extends AbstractState> implements GameLoopInterface<PlayerResponseProcessor<S, AbstractPlayer>, S> {

    private final static Logger LOGGER = Logger.getLogger(AbstractProcessor.class.getName());

    @Override
    public S run(S initialState, PlayerResponseProcessor<S, AbstractPlayer> processor) {
        S state = initialState;
        int roundNumber = 0;

        while (state != null && !processor.hasGameEnded(state)) {
            roundNumber++;
            state = playRound(processor, roundNumber, state);

            if (state == null) {
                LOGGER.severe("Processor has returned a null state");
            }
        }

        return state;
    }

    private S playRound(PlayerResponseProcessor<S, AbstractPlayer> processor, int roundNumber, S state) {
        LOGGER.info(String.format("Playing round %d", roundNumber));

        ArrayList<AbstractPlayerState> playerStates = state.getPlayerStates();

        return playerStates.stream()
            .reduce(state, (S intermediateState, AbstractPlayerState playerState) -> {
                if (processor.hasGameEnded(intermediateState)) {
                    return intermediateState;
                }

                int playerId = playerState.getPlayerId();
                AbstractPlayer player = processor.getPlayerProvider().getPlayerById(playerId);

                processor.sendUpdates(intermediateState, player);
                Enum actionType = processor.getActionType(intermediateState, playerState);
                String response = player.requestMove(actionType);

                return processor.createNextStateFromResponse(
                        intermediateState, new PlayerResponse(response, playerId), roundNumber);
            }, (inputState, outputState) -> outputState);
    }
}

