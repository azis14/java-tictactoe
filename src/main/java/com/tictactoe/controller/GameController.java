package com.tictactoe.controller;

import com.tictactoe.model.Game;
import com.tictactoe.service.GameService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boardSizes", gameService.getScaleOptions());
        return "index";
    }

    @PostMapping("/game/new")
    public String createGame(
        @RequestParam(defaultValue = "3") int boardSize,
        @RequestParam(
            required = false,
            defaultValue = "Player X"
        ) String playerXName,
        @RequestParam(
            required = false,
            defaultValue = "Player O"
        ) String playerOName,
        Model model
    ) {
        Game game = gameService.createGame(boardSize, playerXName, playerOName);
        return "redirect:/game/" + game.getId();
    }

    @GetMapping("/game/{gameId}")
    public String getGame(@PathVariable String gameId, Model model) {
        try {
            Game game = gameService.getGameById(gameId);
            model.addAttribute("game", game);
            model.addAttribute("strikeToWin", game.getStrikeToWin());
            return "game";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }
}
