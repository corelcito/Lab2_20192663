package com.juegoantena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerStats implements Serializable {
    private String playerName;
    private final List<GameResult> gameResults;

    public PlayerStats(String playerName) {
        this.playerName = playerName;
        this.gameResults = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<GameResult> getGameResults() {
        return gameResults;
    }

    public void addGameResult(GameResult gameResult) {
        gameResults.add(gameResult);
    }

    public void addNamePlayer(String name ) {
        playerName = name;
    }
}