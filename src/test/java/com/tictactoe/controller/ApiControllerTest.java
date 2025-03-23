package com.tictactoe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tictactoe.exception.GameException;
import com.tictactoe.model.Game;
import com.tictactoe.service.GameService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class ApiControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGame_validId_returnsGame() {
        Game game = new Game(3, "PlayerX", "PlayerO", 3);
        when(gameService.getGameById(game.getId())).thenReturn(game);

        ResponseEntity<Game> response = apiController.getGame(game.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(game, response.getBody());
    }

    @Test
    void getGame_invalidId_returnsNotFound() {
        when(gameService.getGameById("invalid-id")).thenThrow(
            new GameException("Game not found with id: invalid-id")
        );

        ResponseEntity<Game> response = apiController.getGame("invalid-id");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void makeMove_validMove_updatesGameState() {
        Game game = new Game(3, "PlayerX", "PlayerO", 3);
        when(gameService.getGameById(game.getId())).thenReturn(game);
        when(gameService.makeMove(game.getId(), 0, 0)).thenReturn(true);

        ResponseEntity<Map<String, Object>> response = apiController.makeMove(
            game.getId(),
            0,
            0
        );

        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody().get("success"));
        assertEquals(game, response.getBody().get("game"));
    }

    @Test
    void makeMove_invalidMove_returnsBadRequest() {
        Game game = new Game(3, "PlayerX", "PlayerO", 3);
        when(gameService.getGameById(game.getId())).thenReturn(game);
        when(gameService.makeMove(game.getId(), 0, 0)).thenReturn(false);

        ResponseEntity<Map<String, Object>> response = apiController.makeMove(
            game.getId(),
            0,
            0
        );

        assertEquals(400, response.getStatusCodeValue());
        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals("Invalid move", response.getBody().get("message"));
    }

    @Test
    void makeMove_invalidGameId_returnsNotFound() {
        when(gameService.getGameById("invalid-id")).thenThrow(
            new GameException("Game not found with id: invalid-id")
        );

        ResponseEntity<Map<String, Object>> response = apiController.makeMove(
            "invalid-id",
            0,
            0
        );

        assertEquals(404, response.getStatusCodeValue());
        assertFalse((Boolean) response.getBody().get("success"));
        assertEquals(
            "Game not found with id: invalid-id",
            response.getBody().get("message")
        );
    }

    @Test
    void resetGame_validId_resetsGame() {
        Game game = new Game(3, "PlayerX", "PlayerO", 3);
        when(gameService.getGameById(game.getId())).thenReturn(game);

        ResponseEntity<Game> response = apiController.resetGame(game.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(game, response.getBody());
    }

    @Test
    void resetGame_invalidId_returnsNotFound() {
        when(gameService.getGameById("invalid-id")).thenThrow(
            new GameException("Game not found with id: invalid-id")
        );

        ResponseEntity<Game> response = apiController.resetGame("invalid-id");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
