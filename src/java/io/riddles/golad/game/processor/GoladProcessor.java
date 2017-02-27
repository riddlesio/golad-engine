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

package io.riddles.golad.game.processor;

import java.util.ArrayList;

import io.riddles.golad.engine.GoladEngine;
import io.riddles.golad.game.move.ActionType;
import io.riddles.golad.game.move.GoladBirthMove;
import io.riddles.golad.game.move.GoladKillMove;
import io.riddles.golad.game.move.GoladMove;
import io.riddles.golad.game.move.GoladMoveDeserializer;
import io.riddles.golad.game.player.GoladPlayer;
import io.riddles.golad.game.state.GoladPlayerState;
import io.riddles.golad.game.state.GoladState;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.game.processor.SimpleProcessor;

/**
 * io.riddles.golad.game.processor.GoladProcessor - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladProcessor extends SimpleProcessor<GoladState, GoladPlayer> {

    private GoladMoveDeserializer moveDeserializer;

    public GoladProcessor(PlayerProvider<GoladPlayer> playerProvider) {
        super(playerProvider);
        this.moveDeserializer = new GoladMoveDeserializer();
    }

    @Override
    public boolean hasGameEnded(GoladState state) {
        int maxRounds = GoladEngine.configuration.getInt("maxRounds");
        ArrayList<Integer> alivePlayerIds = state.getBoard().getAlivePlayerIds();

        return state.getRoundNumber() >= maxRounds || alivePlayerIds.size() <= 1;
    }

    @Override
    public Integer getWinnerId(GoladState state) {
        ArrayList<Integer> alivePlayerIds = state.getBoard().getAlivePlayerIds();

        if (alivePlayerIds.size() == 1) {
            return alivePlayerIds.get(0);
        }

        return null;
    }

    @Override
    public double getScore(GoladState state) {
        return state.getRoundNumber();
    }

    @Override
    public GoladState createNextState(GoladState inputState, int roundNumber) {
        GoladState nextState = inputState;

        for (GoladPlayerState playerState : inputState.getPlayerStates()) {
            GoladPlayer player = getPlayer(playerState.getPlayerId());
            nextState = createNextStateForPlayer(nextState, player, roundNumber);

            if (hasGameEnded(nextState)) break;
        }

        return nextState;
    }

    private GoladState createNextStateForPlayer(GoladState inputState, GoladPlayer player, int roundNumber) {
        sendUpdatesToPlayer(inputState, player);

        GoladState movePerformedState = createMovePerformedState(inputState, player, roundNumber);

        return createMutatedBoardState(movePerformedState, roundNumber);
    }

    private void sendUpdatesToPlayer(GoladState state, GoladPlayer player) {
        player.sendUpdate("round", state.getRoundNumber());
        player.sendUpdate("board", state.getBoard().toString());

        for (GoladPlayerState targetPlayerState : state.getPlayerStates()) {
            GoladPlayer target = getPlayer(targetPlayerState.getPlayerId());
            player.sendUpdate("living_cells", target, targetPlayerState.getFieldCount());
        }
    }

    /**
     * Create state where the player has just performed his move
     * @param inputState State right before the move of the player
     * @param player Player to perform the move
     * @param roundNumber Current round number
     * @return The state as it is after the player's move
     */
    private GoladState createMovePerformedState(GoladState inputState, GoladPlayer player, int roundNumber) {
        GoladState movePerformedState = inputState.createNextState(roundNumber);

        String response = player.requestMove(ActionType.MOVE);
        GoladMove move = this.moveDeserializer.traverse(response);

        if (move instanceof GoladKillMove) {
            movePerformedState.getBoard().processKillMove((GoladKillMove) move);
        } else if (move instanceof GoladBirthMove) {
            movePerformedState.getBoard().processBirthMove((GoladBirthMove) move, player.getId());
        }

        GoladPlayerState playerState = movePerformedState.getPlayerStateById(player.getId());
        playerState.setMove(move);
        movePerformedState.updatePlayerStates();

        return movePerformedState;
    }

    /**
     * Create state where the move is processed by the board
     * @param inputState State right after the move of the player
     * @param roundNumber Current round number
     * @return The state as it is after the board has mutated once
     */
    private GoladState createMutatedBoardState(GoladState inputState, int roundNumber) {
        GoladState mutatedBoardState = inputState.createNextState(roundNumber);

        mutatedBoardState.getBoard().mutate();
        mutatedBoardState.updatePlayerStates();

        return mutatedBoardState;
    }

    private GoladPlayer getPlayer(int id) {
        return this.playerProvider.getPlayerById(id);
    }
}
