package io.riddles.javainterface.game.player;


import java.util.ArrayList;

/**
 * io.riddles.javainterface.engine.player.PlayerBound - Created on 12-12-16
 *
 * [description]
 *
 * @author Joost de Meij - joost@riddles.io, Jim van Eeden - jim@riddles.io
 */
public class PlayerProvider<P extends AbstractPlayer> {

    protected ArrayList<P> players;

    public PlayerProvider() {
        this.players = new ArrayList<>();
    }

    public P getPlayerById(int playerId) {
        for (P player : this.players) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        throw new RuntimeException(String.format("Player with id %d not found", playerId));
    }

    public ArrayList<P> getPlayers() {
        return this.players;
    }

    public void add(P player) {
        this.players.add(player);
    }

    /**
     * When this.players's size == 2 the opponent player will be returned,
     * otherwise, an error will be thrown.
     * @param player The player to find the opponent for.
     * @return The AbstractPlayer of the opponent player
     */
    private P getOpponentPlayer(P player) {
        if (this.players.size() != 2) {
            throw new RuntimeException(String.format("getOpponentPlayer only possible if amount " +
                    "if players is 2, there are %d players in this game", this.players.size()));
        }

        for (P other : this.players) {
            if (player.getId() != other.getId()) {
                return other;
            }
        }

        throw new RuntimeException(
                String.format("Opponent player can't be found for player id %s", player.getId()));
    }
}
