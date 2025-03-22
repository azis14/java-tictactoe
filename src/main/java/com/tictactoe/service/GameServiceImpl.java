package com.tictactoe.service;

import com.tictactoe.exception.GameException;
import com.tictactoe.model.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final Map<String, Game> games = new HashMap<>();
    private final int strikeToWin;
    private final String scaleOptions;

    public GameServiceImpl(
        @Value("${game.strike-to-win:3}") int strikeToWin,
        @Value("${game.scale-options:1}") String scaleOptions
    ) {
        this.strikeToWin = strikeToWin;
        this.scaleOptions = scaleOptions;
    }

    @Override
    public Game createGame(
        int boardSize,
        String playerXName,
        String playerOName
    ) {
        if (boardSize < strikeToWin) {
            throw new GameException(
                "Board size must be at least " + strikeToWin
            );
        }

        Game game = new Game(boardSize, playerXName, playerOName, strikeToWin);
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public Game getGameById(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new GameException("Game not found with id: " + gameId);
        }
        return game;
    }

    @Override
    public boolean makeMove(String gameId, int row, int col) {
        Game game = getGameById(gameId);
        return game.makeMove(row, col);
    }

    @Override
    public void resetGame(String gameId) {
        Game game = getGameById(gameId);
        game.resetGame();
    }

    @Override
    public Map<Integer, String> getScaleOptions() {
        Map<Integer, String> boardSizes = new LinkedHashMap<>();

        String[] scaleOptionsArray = scaleOptions.split(",");

        for (String option : scaleOptionsArray) {
            int size = Integer.parseInt(option);
            String label = String.format("%dx%d", size, size);
            boardSizes.put(size, label);
        }

        return boardSizes;
    }
}
