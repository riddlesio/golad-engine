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

package io.riddles.golad.game.board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.riddles.golad.game.move.GoladBirthMove;
import io.riddles.golad.game.move.GoladKillMove;
import io.riddles.javainterface.exception.InvalidMoveException;
import io.riddles.javainterface.game.data.Board;

/**
 * io.riddles.golad.game.board.GoladBoard - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public class GoladBoard extends Board<String> {

    private int playerCount;
    private String[][] fieldsPreview;

    public GoladBoard(int width, int height, int playerCount) {
        super(width, height);
        this.playerCount = playerCount;
        this.fields = new String[width][height];
        this.clear();
    }

    public GoladBoard(int width, int height, int playerCount, String fieldInput) {
        super(width, height);
        this.playerCount = playerCount;
        this.fields = new String[width][height];
        this.setFieldsFromString(fieldInput);

        updateFieldPreview();
    }

    public GoladBoard(GoladBoard board) {
        super(board.getWidth(), board.getHeight());
        this.playerCount = board.getPlayerCount();
        this.fields = board.getFieldsCopy();

        updateFieldPreview();
    }

    @Override
    public void clear() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.fields[x][y] = ".";
            }
        }
    }

    @Override
    public String fieldFromString(String field) {
        return field;
    }

    public String previewToString() {
        StringBuilder output = new StringBuilder();
        String connector = "";

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                output.append(connector).append(this.fieldsPreview[x][y]);
                connector = ",";
            }
        }

        return output.toString();
    }

    /**
     * Returns the id's of all the dead players on the board
     * @return A list with id's of all the dead players
     */
    public ArrayList<Integer> getAlivePlayerIds() {
        ArrayList<Integer> alivePlayerIds = new ArrayList<>();
        int[] playerFields = countPlayerFields();

        for (int id = 0; id < playerFields.length; id++) {
            if (playerFields[id] > 0) {
                alivePlayerIds.add(id);
            }
        }

        return alivePlayerIds;
    }

    /**
     * Mutates the board according to Conway's Game of Life
     * for one generation
     */
    public void mutate() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                updateField(x, y);
            }
        }

        updateFieldPreview();
    }

    /**
     * Counts the amount of fields each player has
     * @return Array with player id as index and values are the living cell count
     */
    public int[] countPlayerFields() {
        int[] playerFields = new int[this.playerCount];
        for (int i = 0; i < this.playerCount; i++) {
            playerFields[i] = 0;
        }

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                try {
                    int playerIndex = Integer.parseInt(this.fields[x][y]);
                    playerFields[playerIndex]++;
                } catch (Exception ignored) {}
            }
        }

        return playerFields;
    }

    public void processKillMove(GoladKillMove move) {
        try {
            validateKillMove(move);
        } catch (InvalidMoveException ex) {
            move.setException(ex);
            return;
        }

        setFieldAt(move.getCoordinate(), ".");

        updateFieldPreview();
    }

    public void processBirthMove(GoladBirthMove move, int playerId) {
        try {
            validateBirthMove(move, playerId);
        } catch (InvalidMoveException ex) {
            move.setException(ex);
            return;
        }

        for (Point sacrifice : move.getSacrificeCoordinates()) {
            setFieldAt(sacrifice, ".");
        }
        setFieldAt(move.getCoordinate(), playerId + "");

        updateFieldPreview();
    }

    /**
     * For all fields, sets what happens in the next generation
     * by updating this.fieldsPreview
     */
    public void updateFieldPreview() {
        this.fieldsPreview = getFieldsCopy();

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                int[] neighborCount = countNeighbors(x, y);
                int neighborSum = Arrays.stream(neighborCount).sum();

                if (isAlive(x, y) && (neighborSum < 2 || neighborSum > 3)) { // dies
                    killField(x, y);
                } else if (!isAlive(x, y) && neighborSum == 3) {  // born
                    int playerId = IntStream.range(0, neighborCount.length)
                            .reduce((max, i) -> neighborCount[i] > neighborCount[max] ? i : max)
                            .orElseThrow(RuntimeException::new);
                    birthField(x, y, playerId);
                }
            }
        }
    }

    private void validateKillMove(GoladKillMove move) throws InvalidMoveException {
        Point kill = move.getCoordinate();

        if (getFieldFromInput(kill).equals(".")) {
            throw new InvalidMoveException("Can't kill a dead cell");
        }
    }

    private void validateBirthMove(GoladBirthMove move, int playerId) throws InvalidMoveException {
        Point birth = move.getCoordinate();

        if (!getFieldFromInput(birth).equals(".")) {
            throw new InvalidMoveException("You can only birth a dead cell");
        }

        for (Point sacrifice : move.getSacrificeCoordinates()) {
            if (!getFieldFromInput(sacrifice).equals(playerId + "")) {
                throw new InvalidMoveException("You can only sacrifice your own living cells");
            }
        }

        ArrayList<Point> distinctPoints = move.getSacrificeCoordinates().stream()
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

        if (distinctPoints.size() < move.getSacrificeCoordinates().size()) {
            throw new InvalidMoveException("You can sacrifice a cell only once");
        }
    }

    private String getFieldFromInput(Point point) throws InvalidMoveException {
        String field = super.getFieldAt(point);

        if (field != null) {
            return field;
        }

        throw new InvalidMoveException(
                String.format("%d,%d is not a point on the field", point.x, point.y));
    }

    private String[][] getFieldsCopy() {
        String[][] copy = new String[this.width][this.height];

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                copy[x][y] = this.fields[x][y];
            }
        }

        return copy;
    }

    private int[] countNeighbors(int x, int y) {
        int[] neighbors = new int[this.playerCount];
        for (int i = 0; i < this.playerCount; i++) {
            neighbors[i] = 0;
        }

        int minX = x == 0 ? x : x - 1;
        int maxX = x == this.width - 1 ? x : x + 1;
        int minY = y == 0 ? y : y - 1;
        int maxY = y == this.height - 1 ? y : y + 1;

        for (int dy = minY; dy <= maxY; dy++) {
            for (int dx = minX; dx <= maxX; dx++) {
                try {
                    if (dx == x && dy == y) continue;

                    int playerIndex = Integer.parseInt(this.fields[dx][dy]);
                    neighbors[playerIndex]++;
                } catch (Exception ignored) {}
            }
        }

        return neighbors;
    }

    private void killField(int x, int y) {
        this.fieldsPreview[x][y] = String.format("%s>.", this.fieldsPreview[x][y]);
    }

    private void birthField(int x, int y, int playerId) {
        this.fieldsPreview[x][y] = String.format("%s>%s", this.fieldsPreview[x][y], playerId);
    }

    private void updateField(int x, int y) {
        String[] split = this.fieldsPreview[x][y].split(">");

        if (split.length <= 1) return;

        this.fields[x][y] = split[1];
    }

    private boolean isAlive(int x, int y) {
        return !this.fields[x][y].equals(".");
    }

    private int getPlayerCount() {
        return this.playerCount;
    }
}
