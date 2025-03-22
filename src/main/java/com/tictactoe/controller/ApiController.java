package com.tictactoe.controller;

import com.tictactoe.exception.GameException;
import com.tictactoe.model.Game;
import com.tictactoe.service.GameService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class ApiController {

    private final GameService gameService;

    @Autowired
    public ApiController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable String gameId) {
        try {
            Game game = gameService.getGameById(gameId);
            return ResponseEntity.ok(game);
        } catch (GameException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<Map<String, Object>> makeMove(
        @PathVariable String gameId,
        @RequestParam int row,
        @RequestParam int col
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            Game game = gameService.getGameById(gameId);
            boolean isValidMove = gameService.makeMove(gameId, row, col);

            if (!isValidMove) {
                response.put("success", false);
                response.put("message", "Invalid move");
                return ResponseEntity.badRequest().body(response);
            }

            response.put("success", true);
            response.put("game", game);
            return ResponseEntity.ok(response);
        } catch (GameException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/{gameId}/reset")
    public ResponseEntity<Game> resetGame(@PathVariable String gameId) {
        try {
            gameService.resetGame(gameId);
            Game game = gameService.getGameById(gameId);
            return ResponseEntity.ok(game);
        } catch (GameException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
