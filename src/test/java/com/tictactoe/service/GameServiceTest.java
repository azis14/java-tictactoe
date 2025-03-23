package com.tictactoe.service;

import static org.junit.jupiter.api.Assertions.*;

import com.tictactoe.exception.GameException;
import com.tictactoe.model.Game;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

class GameServiceTest {

    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(3, "3,4,5");
    }

    @Test
    void createGame_validInputs_createsGame() {
        Game game = gameService.createGame(3, "PlayerX", "PlayerO");

        assertNotNull(game);
        assertEquals(3, game.getBoard().getSize());
        assertEquals("PlayerX", game.getPlayerX().getName());
        assertEquals("PlayerO", game.getPlayerO().getName());
    }

    @Test
    void createGame_boardSizeTooSmall_throwsException() {
        Exception exception = assertThrows(GameException.class, () -> {
            gameService.createGame(2, "PlayerX", "PlayerO");
        });

        assertEquals("Board size must be at least 3", exception.getMessage());
    }

    @Test
    void getGameById_validId_returnsGame() {
        Game game = gameService.createGame(3, "PlayerX", "PlayerO");
        Game retrievedGame = gameService.getGameById(game.getId());

        assertEquals(game, retrievedGame);
    }

    @Test
    void getGameById_invalidId_throwsException() {
        Exception exception = assertThrows(GameException.class, () -> {
            gameService.getGameById("invalid-id");
        });

        assertEquals(
            "Game not found with id: invalid-id",
            exception.getMessage()
        );
    }

    @Test
    void makeMove_validMove_updatesGameState() {
        Game game = gameService.createGame(3, "PlayerX", "PlayerO");
        boolean moveMade = gameService.makeMove(game.getId(), 0, 0);

        assertTrue(moveMade);
    }

    @Test
    void resetGame_validId_resetsGame() {
        Game game = gameService.createGame(3, "PlayerX", "PlayerO");
        gameService.makeMove(game.getId(), 0, 0);

        gameService.resetGame(game.getId());

        assertTrue(game.getBoard().getCell(0, 0).isEmpty());
    }

    @Test
    void getScaleOptions_validOptions_returnsMap() {
        Map<Integer, String> scaleOptions = gameService.getScaleOptions();

        assertEquals(3, scaleOptions.size());
        assertEquals("3x3", scaleOptions.get(3));
        assertEquals("4x4", scaleOptions.get(4));
        assertEquals("5x5", scaleOptions.get(5));
    }
}
