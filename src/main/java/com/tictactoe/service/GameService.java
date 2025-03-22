package com.tictactoe.service;

import com.tictactoe.model.Game;
import java.util.List;
import java.util.Map;

public interface GameService {
    Game createGame(int boardSize, String playerXName, String playerOName);
    Game getGameById(String gameId);
    boolean makeMove(String gameId, int row, int col);
    void resetGame(String gameId);
    Map<Integer, String> getScaleOptions();
}
