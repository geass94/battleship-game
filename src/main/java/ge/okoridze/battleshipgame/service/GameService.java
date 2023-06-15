package ge.okoridze.battleshipgame.service;

import ge.okoridze.battleshipgame.model.Game;
import ge.okoridze.battleshipgame.model.Ship;
import ge.okoridze.battleshipgame.model.ShipType;
import ge.okoridze.battleshipgame.model.User;
import ge.okoridze.battleshipgame.repositpry.GameRepository;
import ge.okoridze.battleshipgame.repositpry.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final ShipRepository shipRepository;
    private Integer gridSize;

    public GameService(GameRepository gameRepository, ShipRepository shipRepository) {
        this.gameRepository = gameRepository;
        this.shipRepository = shipRepository;
    }

    public Game init(User user, Integer gridSize)
    {
        Game game = new Game();
        game.setGridSize(gridSize);
        this.gridSize = game.getGridSize();
        game.setUser(user);
        generateShips(game);
        return gameRepository.save(game);
    }


    private void generateShips(Game game) {
        Random random = new Random();
        List<Ship> ships = new ArrayList<>();

        boolean[][] occupied = new boolean[this.gridSize][this.gridSize]; // Track occupied cells

        // Generate ship positions
        for (int i = 0; i < 2; i++) {
            boolean horizontal = random.nextBoolean();

            Ship ship = generateShipPosition(ShipType.TWO_SLOT.getSize(), horizontal, occupied, random);
            ship.setType(ShipType.TWO_SLOT);
            ship.setGame(game);
            ships.add(ship);
            markOccupiedCells(ship, occupied);
        }

        for (int i = 0; i < 2; i++) {
            boolean horizontal = random.nextBoolean();

            Ship ship = generateShipPosition(ShipType.FOUR_SLOT.getSize(), horizontal, occupied, random);
            ship.setGame(game);
            ship.setType(ShipType.FOUR_SLOT);
            ships.add(ship);
            markOccupiedCells(ship, occupied);
        }

        game.setShips(ships);
    }

    private Ship generateShipPosition(int size, boolean horizontal, boolean[][] occupied, Random random) {
        int startX, startY, endX, endY;

        do {
            if (horizontal) {
                startX = random.nextInt(11 - size);
                startY = random.nextInt(this.gridSize);
                endX = startX + size - 1;
                endY = startY;
            } else {
                startX = random.nextInt(this.gridSize);
                startY = random.nextInt(11 - size);
                endX = startX;
                endY = startY + size - 1;
            }
        } while (!isValidShipPlacement(occupied, startX, startY, endX, endY));

        Ship ship = new Ship();
        ship.setStartX(startX);
        ship.setEndX(endX);
        ship.setStartY(startY);
        ship.setEndY(endY);
        return ship;
    }

    private boolean isValidShipPlacement(boolean[][] occupied, int startX, int startY, int endX, int endY) {
        // Check if ship is out of bounds
        if (startX < 0 || startX >= this.gridSize || startY < 0 || startY >= this.gridSize ||
                endX < 0 || endX >= this.gridSize || endY < 0 || endY >= this.gridSize) {
            return false;
        }

        // Check for overlapping or adjacent ships
        for (int x = startX - 1; x <= endX + 1; x++) {
            for (int y = startY - 1; y <= endY + 1; y++) {
                if (x >= 0 && x < this.gridSize && y >= 0 && y < this.gridSize && occupied[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void markOccupiedCells(Ship ship, boolean[][] occupied) {
        int startX = Math.max(0, ship.getStartX() - 1);
        int startY = Math.max(0, ship.getStartY() - 1);
        int endX = Math.min(this.gridSize - 1, ship.getEndX() + 1);
        int endY = Math.min(this.gridSize - 1, ship.getEndY() + 1);

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                occupied[x][y] = true;
            }
        }
    }

}
