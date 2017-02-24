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

package io.riddles.golad.engine;

import java.security.SecureRandom;
import java.util.ArrayList;

import io.riddles.golad.game.GoladSerializer;
import io.riddles.golad.game.board.GoladBoard;
import io.riddles.golad.game.player.GoladPlayer;
import io.riddles.golad.game.processor.GoladProcessor;
import io.riddles.golad.game.state.GoladPlayerState;
import io.riddles.golad.game.state.GoladState;
import io.riddles.golad.boardgenerator.GoladBoardGenerator;
import io.riddles.javainterface.configuration.Configuration;
import io.riddles.javainterface.engine.AbstractEngine;
import io.riddles.javainterface.engine.GameLoopInterface;
import io.riddles.javainterface.engine.SimpleGameLoop;
import io.riddles.javainterface.exception.TerminalException;
import io.riddles.javainterface.game.player.PlayerProvider;
import io.riddles.javainterface.io.IOInterface;

/**
 * io.riddles.golad.engine.GoladEngine - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladEngine extends AbstractEngine<GoladProcessor, GoladPlayer, GoladState> {

    public GoladEngine(PlayerProvider<GoladPlayer> playerProvider, IOInterface ioHandler) throws TerminalException {
        super(playerProvider, ioHandler);
    }

    @Override
    protected Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration();
        SecureRandom random = new SecureRandom();

        configuration.put("fieldWidth", 20);
        configuration.put("fieldHeight", 20);
        configuration.put("playerCount", 2);
        configuration.put("maxRounds", 100);
        configuration.put("initialCellsPerPlayer", 10);
        configuration.put("randomSeed", random.nextInt());

        return configuration;
    }

    @Override
    protected GoladProcessor createProcessor() {
        return new GoladProcessor(this.playerProvider);
    }

    @Override
    protected GameLoopInterface createGameLoop() {
        return new SimpleGameLoop();
    }

    @Override
    protected GoladPlayer createPlayer(int id) {
        return new GoladPlayer(id);
    }

    @Override
    protected void sendSettingsToPlayer(GoladPlayer player) {
        player.sendSetting("your_botid", player.getId());
        player.sendSetting("field_width", configuration.getInt("fieldWidth"));
        player.sendSetting("field_height", configuration.getInt("fieldHeight"));
    }

    @Override
    protected GoladState getInitialState() {
        // Generate random initial board
        int width = configuration.getInt("fieldWidth");
        int height = configuration.getInt("fieldHeight");
        int playerCount = this.playerProvider.getPlayers().size();

        GoladBoardGenerator generator = new GoladBoardGenerator(width, height, playerCount);
        GoladBoard board = generator.generate();

        // Create initial player states
        ArrayList<GoladPlayerState> playerStates = new ArrayList<>();
        for (GoladPlayer player : this.playerProvider.getPlayers()) {
            GoladPlayerState playerState = new GoladPlayerState(player.getId());
            playerStates.add(playerState);
        }

        // Create initial state
        GoladState state = new GoladState(playerStates, board);

        // Update initial player states
        state.updatePlayerStates();

        return state;
    }

    @Override
    public String getPlayedGame(GoladState initialState) {
        GoladSerializer serializer = new GoladSerializer();
        return serializer.traverseToString(this.processor, initialState);
    }
}
