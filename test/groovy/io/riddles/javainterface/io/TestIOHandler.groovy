package io.riddles.javainterface.io

/**
 * Created by joost.
 */
class TestIOHandler extends BotIOHandler {

    public TestIOHandler(int botId) {
        super(botId);
    }

    @Override
    void sendMessage(String s) {
        System.out.println(s);
    }
}