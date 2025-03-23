package com.tictactoe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.tictactoe.model.Game;
import com.tictactoe.service.GameService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private Model model;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void index_displaysBoardSizes() {
        Map<Integer, String> boardSizes = new LinkedHashMap<>();
        boardSizes.put(3, "3x3");
        boardSizes.put(5, "5x5");
        when(gameService.getScaleOptions()).thenReturn(boardSizes);

        String viewName = gameController.index(model);

        assertEquals("index", viewName);
        verify(model).addAttribute("boardSizes", boardSizes);
    }

    @Test
    void createGame_redirectsToGamePage() {
        Game game = new Game(3, "Player X", "Player O", 3);
        when(gameService.createGame(3, "Player X", "Player O")).thenReturn(
            game
        );

        String viewName = gameController.createGame(
            3,
            "Player X",
            "Player O",
            model
        );

        assertEquals("redirect:/game/" + game.getId(), viewName);
    }

    @Test
    void getGame_validId_displaysGamePage() {
        Game game = new Game(3, "Player X", "Player O", 3);
        when(gameService.getGameById(game.getId())).thenReturn(game);

        String viewName = gameController.getGame(game.getId(), model);

        assertEquals("game", viewName);
        verify(model).addAttribute("game", game);
        verify(model).addAttribute("strikeToWin", game.getStrikeToWin());
    }

    @Test
    void getGame_invalidId_displaysIndexWithError() {
        when(gameService.getGameById("invalid-id")).thenThrow(
            new RuntimeException("Game not found")
        );

        String viewName = gameController.getGame("invalid-id", model);

        assertEquals("index", viewName);
        verify(model).addAttribute("error", "Game not found");
    }
}
