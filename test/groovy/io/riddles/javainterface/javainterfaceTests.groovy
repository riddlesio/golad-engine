package io.riddles.javainterface

import io.riddles.javainterface.game.TestProcessor
import io.riddles.javainterface.game.TestSerializer
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.player.TestPlayer
import io.riddles.javainterface.game.state.TestState
import io.riddles.javainterface.io.FileIOHandler
import io.riddles.javainterface.io.TestIOHandler
import org.json.JSONArray
import org.json.JSONObject
import spock.lang.Specification

class javainterfaceTests extends Specification {
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

    def "JSON addDefaultJSON output"() {

        setup:
        PlayerProvider p = new PlayerProvider<TestPlayer>();

        p.add(new TestPlayer(1));
        p.add(new TestPlayer(2));

        TestSerializer serializer = new TestSerializer(p);

        String s = serializer.traverseToString(new TestProcessor(), new TestState());
        JSONObject json = new JSONObject(s);
        expect:


        json.get("settings") instanceof JSONObject;
        json.get("settings").get('players') instanceof JSONObject;
        json.get("settings").get('players').get('names') instanceof JSONArray;
        json.get("settings").get('players').get('count') instanceof Integer;
        json.get("score") instanceof Integer;
        if (json.get("winner") != null) {
            json.get("winner") instanceof Integer;
        }
    }

    def "Player"() {

        setup:
        TestPlayer testPlayer1 = new TestPlayer(1);
        testPlayer1.setName("FooPlayer");
        testPlayer1.setIoHandler(new FileIOHandler("./test/resources/wrapper_input.txt"))

        expect:
        testPlayer1.getName() == "FooPlayer";
        testPlayer1.getIoHandler() instanceof FileIOHandler;
    }

    def "PlayerProvider"() {

        setup:
        PlayerProvider p = new PlayerProvider<TestPlayer>();

        p.add(new TestPlayer(1));
        p.add(new TestPlayer(2));

        expect:
        p.getPlayerById(1).getId() == 1;
        p.getPlayerById(2).getId() == 2;
    }


}