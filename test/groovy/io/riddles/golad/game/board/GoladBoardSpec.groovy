package io.riddles.golad.game.board

import io.riddles.golad.engine.GoladEngine
import io.riddles.javainterface.configuration.Configuration
import spock.lang.Specification

/**
 * io.riddles.golad.game.board.GoladBoardSpec - Created on 21-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
class GoladBoardSpec extends Specification {

    def "test map generator player fields count"() {
        setup:
        GoladEngine.configuration = new Configuration()
        GoladEngine.configuration.put("playerCount", 2)
        String input = ".,.,.,.,.,.,.,.,.,.," +
                       ".,.,.,.,.,.,.,.,.,.," +
                       ".,.,1,1,.,.,.,0,0,.," +
                       ".,.,1,1,.,.,.,0,.,.," +
                       ".,.,.,.,.,.,.,.,.,.," +
                       ".,.,.,.,.,.,.,.,.,.," +
                       ".,.,1,.,.,.,.,.,.,.," +
                       ".,.,0,.,.,.,0,1,0,.," +
                       ".,.,1,.,.,.,.,.,.,.," +
                       ".,.,.,.,.,.,.,.,.,."
        GoladBoard board = new GoladBoard(10, 10, 2, input)

        when:
        board.mutate()

        then:
        String output = ".,.,.,.,.,.,.,.,.,.," +
                        ".,.,.,.,.,.,.,.,.,.," +
                        ".,.,1,1,.,.,.,0,0,.," +
                        ".,.,1,1,.,.,.,0,0,.," +
                        ".,.,.,.,.,.,.,.,.,.," +
                        ".,.,.,.,.,.,.,.,.,.," +
                        ".,.,.,.,.,.,.,0,.,.," +
                        ".,1,0,1,.,.,.,1,.,.," +
                        ".,.,.,.,.,.,.,0,.,.," +
                        ".,.,.,.,.,.,.,.,.,."
        board.toString() == output
    }
}
