package io.riddles.javainterface

import io.riddles.javainterface.game.player.TestPlayer
import io.riddles.javainterface.io.FileIOHandler
import io.riddles.javainterface.io.TestIOHandler
import spock.lang.Specification

/**
 * Created by joost on 1/30/17.
 */
class IOTests extends Specification {
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

    def "IOHandler"() {

        setup:
        TestPlayer testPlayer1 = new TestPlayer(1);
        testPlayer1.setName("FooPlayer");

        TestIOHandler testIOHandler = Mock();
        testPlayer1.setIoHandler(testIOHandler);

        when:
        testPlayer1.sendSetting("test", 1);
        then:
        1 * testIOHandler.sendMessage("settings test 1")

        when:
        testPlayer1.sendSetting("test2", "value");
        then:
        1 * testIOHandler.sendMessage("settings test2 value")
    }

    def "FileIOHandler with FileNotFoundException"() {

        setup:

        FileIOHandler testIOHandler;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_existing.txt");

        then:
        notThrown Exception
    }

    def "FileIOHandler getNextMessage Exception"() {

        setup:

        FileIOHandler testIOHandler;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_existing.txt");
        testIOHandler.getNextMessage();
        testIOHandler.getNextMessage();
        testIOHandler.getNextMessage();
        testIOHandler.getNextMessage();

        then:
        thrown Exception
    }

    def "FileIOHandler getNextMessage no Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_empty.txt");
        testIOHandler.getNextMessage();

        then:
        notThrown Exception
    }

    def "FileIOHandler sendWarning no Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_empty.txt");
        testIOHandler.sendWarning();

        then:
        notThrown Exception
    }

    def "FileIOHandler sendMessage no Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_empty.txt");
        testIOHandler.sendMessage();

        then:
        notThrown Exception
    }

    def "FileIOHandler getNextMessageFromFile Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/empty.txt");
        testIOHandler.getNextMessageFromFile();

        then:
        thrown Exception
    }

    def "FileIOHandler sendRequest no Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_empty.txt");
        testIOHandler.sendRequest("hello");

        then:
        notThrown Exception
    }

    def "FileIOHandler sendRequest empty no Exception"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/empty.txt");
        testIOHandler.sendRequest("hello");

        then:
        notThrown Exception
    }

    def "FileIOHandler waitForMessage 1"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/empty.txt");
        testIOHandler.waitForMessage("line 1");

        then:
        notThrown Exception
    }
    def "FileIOHandler waitForMessage 2"() {

        setup:
        FileIOHandler testIOHandler = null;

        when:
        testIOHandler = new FileIOHandler("./test/resources/not_empty.txt");
        testIOHandler.waitForMessage("line 1");

        then:
        notThrown Exception
    }


}
