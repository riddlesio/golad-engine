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

package io.riddles.javainterface.game.data;


import java.awt.Point;

/**
 * io.riddles.javainterface.game.board.Board - Created on 7-10-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public abstract class Board<T> {
    protected T fields[][];
    protected int width;
    protected int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public T getFieldAt(Point point) {
        if (point.x < 0 || point.x >= this.width || point.y < 0 || point.y >= this.height) {
            return null;
        }

        return this.fields[point.x][point.y];
    }

    public void setFieldAt(Point point, T cell) {
        this.fields[point.x][point.y] = cell;
    }

    /**
     * Creates comma separated String
     * @return String
     */
    public String toString() {
        String output = "";
        String connector = "";

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                output += connector + this.fields[x][y];
                connector = ",";
            }
        }

        return output;
    }

    public void setFieldsFromString(String input) {
        String[] split = input.split(",");
        int x = 0;
        int y = 0;

        for (String fieldString : split) {
            this.fields[x][y] = fieldFromString(fieldString);

            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    /**
     * Dumps visual representation of the board to the
     * output log
     */
    public void dump() {
        int maxLength = 0;

        for (int y = 0; y < this.height; y++) {  // get maximum string length of cell
            for (int x = 0; x < this.width; x++) {
                String cellString = this.fields[x][y] + "";
                int length = cellString.length();

                if (length > maxLength) {
                    maxLength = length;
                }
            }
        }

        for (int y = 0; y < this.height; y++) {  // dump the board
            String line = "";
            for (int x = 0; x < this.width; x++) {
                String cell = this.fields[x][y] + "";
                line += cell;
                for (int i = 0; i <= maxLength - cell.length(); i++) {
                     line += " ";
                }
            }
            System.out.println(line);
        }

        System.out.println();
    }

    public T[][] getFields() {
        return this.fields;
    }

    public void setFields(T[][] fields) {
        this.fields = fields;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public abstract void clear();

    public abstract T fieldFromString(String field);
}
