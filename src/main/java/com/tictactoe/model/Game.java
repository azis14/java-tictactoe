package com.tictactoe.model;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Game {

    private String id;
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private GameStatus status;
    private LocalDateTime createdAt;
    private Player winner;
    private int strikeToWin;

    public Game() {}

    public Game(
        int boardSize,
        String playerXName,
        String playerOName,
        int strikeToWin
    ) {
        this.id = java.util.UUID.randomUUID().toString();
        this.board = new Board(boardSize);
        this.playerX = new Player(
            "X",
            !StringUtils.isEmpty(playerXName) ? playerXName : "Player X"
        );
        this.playerO = new Player(
            "O",
            !StringUtils.isEmpty(playerOName) ? playerOName : "Player O"
        );
        this.currentPlayer = playerX;
        this.status = GameStatus.IN_PROGRESS;
        this.createdAt = LocalDateTime.now();
        this.strikeToWin = strikeToWin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    public boolean makeMove(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }

        if (board.placeSymbol(row, col, currentPlayer.getSymbol())) {
            updateGameStatus(row, col);
            if (status == GameStatus.IN_PROGRESS) {
                switchPlayer();
            }
            return true;
        }

        return false;
    }

    private void updateGameStatus(int row, int col) {
        if (checkWin(row, col)) {
            status = (currentPlayer == playerX)
                ? GameStatus.PLAYER_X_WON
                : GameStatus.PLAYER_O_WON;
            winner = currentPlayer;
            return;
        }

        // Check for draw
        if (board.isFull()) {
            status = GameStatus.DRAW;
        }
    }

    private boolean checkWin(int row, int col) {
        String symbol = currentPlayer.getSymbol();
        int size = board.getSize();

        int rowCount = 0;
        for (int j = 0; j < size; j++) {
            if (
                board.getCell(row, j) != null &&
                board.getCell(row, j).getValue().equals(symbol)
            ) {
                rowCount++;
                if (rowCount >= strikeToWin) return true;
            } else {
                rowCount = 0;
            }
        }

        int colCount = 0;
        for (int i = 0; i < size; i++) {
            if (
                board.getCell(i, col) != null &&
                board.getCell(i, col).getValue().equals(symbol)
            ) {
                colCount++;
                if (colCount >= strikeToWin) return true;
            } else {
                colCount = 0;
            }
        }

        return checkDiagonals(symbol);
    }

    private boolean checkDiagonals(String symbol) {
        int size = board.getSize();

        // Check all possible diagonals
        for (int i = 0; i <= size - strikeToWin; i++) {
            for (int j = 0; j <= size - strikeToWin; j++) {
                // Check diagonal (top-left to bottom-right)
                boolean diagWin = true;
                for (int k = 0; k < strikeToWin; k++) {
                    if (
                        board.getCell(i + k, j + k) == null ||
                        !board.getCell(i + k, j + k).getValue().equals(symbol)
                    ) {
                        diagWin = false;
                        break;
                    }
                }
                if (diagWin) return true;

                // Check anti-diagonal (top-right to bottom-left)
                boolean antiDiagWin = true;
                for (int k = 0; k < strikeToWin; k++) {
                    if (
                        board.getCell(i + k, j + strikeToWin - 1 - k) == null ||
                        !board
                            .getCell(i + k, j + strikeToWin - 1 - k)
                            .getValue()
                            .equals(symbol)
                    ) {
                        antiDiagWin = false;
                        break;
                    }
                }
                if (antiDiagWin) return true;
            }
        }

        return false;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void resetGame() {
        board.clear();
        currentPlayer = playerX;
        status = GameStatus.IN_PROGRESS;
        winner = null;
    }

    public int getStrikeToWin() {
        return strikeToWin;
    }

    public void setStrikeToWin(int strikeToWin) {
        this.strikeToWin = strikeToWin;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public void setPlayerO(Player playerO) {
        this.playerO = playerO;
    }
}
