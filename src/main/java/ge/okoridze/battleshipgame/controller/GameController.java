package ge.okoridze.battleshipgame.controller;

import ge.okoridze.battleshipgame.dto.CreateGameRequest;
import ge.okoridze.battleshipgame.dto.GameDTO;
import ge.okoridze.battleshipgame.dto.ShipDTO;
import ge.okoridze.battleshipgame.dto.ShotDTO;
import ge.okoridze.battleshipgame.model.Game;
import ge.okoridze.battleshipgame.model.Ship;
import ge.okoridze.battleshipgame.model.User;
import ge.okoridze.battleshipgame.repositpry.GameRepository;
import ge.okoridze.battleshipgame.repositpry.ShipRepository;
import ge.okoridze.battleshipgame.repositpry.UserRepository;
import ge.okoridze.battleshipgame.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;
    private final UserRepository userRepository;
    private final GameService gameService;

    public GameController(GameRepository gameRepository, ShipRepository shipRepository, UserRepository userRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    private final Integer gridSize = 10;

    @PostMapping
    @Transactional
    public GameDTO createGame(Authentication authentication, @RequestBody CreateGameRequest req) {
        User user = userRepository.findByEmail(authentication.getName());
        return new GameDTO(gameService.init(user, req.gridSize < 10 ? 10 : req.gridSize));
    }

    @GetMapping("/{id}")
    public GameDTO getGame(@PathVariable Long id) {
        return gameRepository.findById(id).map((GameDTO::new))
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }

    @PostMapping(value = "/{id}/shoot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShotDTO shoot(@PathVariable Long id, @RequestParam int x, @RequestParam int y) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        game.setHits(game.getHits() - 1);
        gameRepository.save(game);

        for (Ship ship : game.getShips()) {
            if (x >= ship.getStartX() && x <= ship.getEndX() && y >= ship.getStartY() && y <= ship.getEndY()) {
                ship.hit();
                shipRepository.save(ship);
                if (ship.isSunk()) return new ShotDTO(game.getHits(), true, new ShipDTO(
                        ship.getStartX(),
                        ship.getEndX(),
                        ship.getStartY(),
                        ship.getEndY()
                ));
                return new ShotDTO(game.getHits(), true, null);
            }
        }

        return new ShotDTO(game.getHits(), false, null);
    }

}
