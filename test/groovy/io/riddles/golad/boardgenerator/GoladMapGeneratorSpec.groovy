package io.riddles.golad.boardgenerator

import io.riddles.golad.engine.GoladEngine
import io.riddles.golad.game.board.GoladBoard
import io.riddles.javainterface.configuration.Configuration
import spock.lang.Specification

/**
 * io.riddles.golad.boardgenerator.GoladMapGeneratorSpec - Created on 20-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */

class GoladMapGeneratorSpec extends Specification {

    def "test map generator player fields count"() {
        setup:
        GoladEngine.configuration = new Configuration()
        GoladEngine.configuration.put("initialCellsPerPlayer", 50)
        GoladEngine.configuration.put("randomSeed", new Random().nextInt())
        GoladBoardGenerator generator = new TestMapGenerator(20, 20, 2)

        when:
        GoladBoard board = generator.generate()

        then:
        generator.getFieldsCountForPlayer(board, 1) == 50
        generator.getFieldsCountForPlayer(board, 1) == 50
    }
}