package io.riddles.golad.engine

import io.riddles.golad.game.player.GoladPlayer
import io.riddles.javainterface.exception.TerminalException
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.io.FileIOHandler
import io.riddles.javainterface.io.IOInterface

/**
 * io.riddles.golad.engine.TestEngine - Created on 23-2-17
 *
 * [description]
 *
 * @author Jim van Eeden - jim@riddles.io
 */
class TestEngine extends GoladEngine {

    TestEngine(String[] botInputs, IOInterface ioHandler) throws TerminalException {
        super(new PlayerProvider<GoladPlayer>(), ioHandler)

        GoladPlayer bot0 = new GoladPlayer(0)
        GoladPlayer bot1 = new GoladPlayer(1)
        bot0.setIoHandler(new FileIOHandler(botInputs[0]))
        bot1.setIoHandler(new FileIOHandler(botInputs[1]))
        this.playerProvider.add(bot0)
        this.playerProvider.add(bot1)
    }
}
