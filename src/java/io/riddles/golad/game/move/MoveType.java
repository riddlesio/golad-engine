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

package io.riddles.golad.game.move;

/**
 * io.riddles.catchfrauds.game.move.ActionType - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
public enum MoveType {
    KILL,
    BIRTH,
    NONE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static MoveType fromString(String input) {
        for (MoveType moveType : MoveType.values()) {
            if (moveType.toString().equalsIgnoreCase(input)) {
                return moveType;
            }
        }

        return NONE;
    }
}