package io.riddles.golad

import io.riddles.golad.engine.GoladEngine
import io.riddles.golad.engine.TestEngine
import io.riddles.golad.game.state.GoladState
import io.riddles.javainterface.io.FileIOHandler
import spock.lang.Specification

/**
 * io.riddles.golad.GoladGameSpec - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
class GoladGameSpec extends Specification {

    def "test running of standard game with input files"() {
        setup:
        String[] botInputs = new String[2]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        GoladEngine engine = new TestEngine(botInputs, new FileIOHandler(wrapperInput))

        when:
        GoladState firstState = engine.willRun()
        GoladState finalState = engine.run(firstState)

        then:
        println(engine.getPlayedGame(finalState))
        1 == 1
    }
}
