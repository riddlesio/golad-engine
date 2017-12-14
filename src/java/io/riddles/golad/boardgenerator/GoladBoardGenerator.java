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

package io.riddles.golad.boardgenerator;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.logging.Logger;

import io.riddles.golad.engine.GoladEngine;
import io.riddles.golad.game.board.GoladBoard;
import io.riddles.javainterface.exception.ConfigurationException;

/**
 * io.riddles.golad.boardgenerator.GoladMapGenerator - Created on 20-2-17
 *
 * Generates a board for GOLAD, currently only works for 2 players
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladBoardGenerator {

    private final static Logger LOGGER = Logger.getLogger(GoladBoardGenerator.class.getName());

    private int width;
    private int height;
    private int playerCount;
    private double initialCellsPerPlayer;
    private SecureRandom random;

    public GoladBoardGenerator(int width, int height, int playerCount) {
        this.width = width;
        this.height = height;
        this.playerCount = playerCount;
        this.initialCellsPerPlayer = GoladEngine.configuration.getInt("initialCellsPerPlayer");

        if (this.height % 2 != 0) {
            throw new ConfigurationException("Field height must be an even number");
        }

        if (playerCount != 2) {
            throw new ConfigurationException("Current implementation only works for 2 players");
        }

        if (this.initialCellsPerPlayer * 2> 0.6 * this.width * this.height) {
            throw new ConfigurationException(
                    "The field is too small for this amount of initial living cells");
        }

        try {
            this.random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.severe("Not able to use SHA1PRNG, using default algorithm");
            this.random = new SecureRandom();
        }
        String seed = GoladEngine.configuration.getString("seed");
        LOGGER.info("RANDOM SEED IS: " + seed);
        this.random.setSeed(seed.getBytes());
    }

    public GoladBoard generate() {
        GoladBoard board = new GoladBoard(this.width, this.height, this.playerCount);

        populateFirstHalf(board);
        populateSecondHalf(board);

        board.updateFieldPreview();

        return board;
    }

    /**
     * Randomly generates living cells on the top half of the board
     * @param board Empty board
     */
    private void populateFirstHalf(GoladBoard board) {
        ArrayList<Point> halfBoardPoints = new ArrayList<>();

        for (int y = 0; y < this.height / 2; y++) {
            for (int x = 0; x < this.width; x++) {
                halfBoardPoints.add(new Point(x, y));
            }
        }

        for (int i = 0; i < this.initialCellsPerPlayer; i++) {
            int index = this.random.nextInt(halfBoardPoints.size());

            int player = 0;
            if (GoladEngine.configuration.getInt("separateStartingCells") <= 0) {
                player = this.random.nextInt(2);
            }

            Point point = halfBoardPoints.remove(index);
            board.setFieldAt(point, player + "");
        }
    }

    /**
     * Populates the second half of the board by rotating the
     * top half to the bottom half
     * @param board Board with the top half populated
     */
    private void populateSecondHalf(GoladBoard board) {
        String[][] rotated = rotateFields(board);
        String[][] inverted = invertPlayerCells(rotated);

        // Populate second half with rotated and inverted board
        for (int y = this.height / 2; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                board.getFields()[x][y] = inverted[x][y];
            }
        }
    }

    private String[][] rotateFields(GoladBoard board) {
        String[][] rotated = new String[this.width][this.height];

        // Vertical matrix reflection
        for (int y = 0; y < this.height / 2; y++) {
            for (int x = 0; x < this.width; x++) {
                rotated[x][y] = board.getFields()[x][this.height - (y + 1)];
                rotated[x][this.height - (y + 1)] = board.getFields()[x][y];
            }
        }

        // Horizontal matrix reflection
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width / 2; x++) {
                String temp = rotated[x][y];
                rotated[x][y] = rotated[this.width - (x + 1)][y];
                rotated[this.width - (x + 1)][y] = temp;
            }
        }

        return rotated;
    }

    private String[][] invertPlayerCells(String[][] fields) {
        String[][] inverted = new String[this.width][this.height];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                try {
                    inverted[x][y] = 2 - (Integer.parseInt(fields[x][y]) + 1) + "";
                } catch (Exception ex) {
                    inverted[x][y] = ".";
                }
            }
        }

        return inverted;
    }
}
