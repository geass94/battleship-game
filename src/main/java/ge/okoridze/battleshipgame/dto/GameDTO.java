package ge.okoridze.battleshipgame.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ge.okoridze.battleshipgame.model.Ship;

import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonSerialize
public class GameDTO {
    public Long id;
    public List<Ship> ships;
    public int gridSize;
    public boolean won;
    public int hits;
    public UserDTO user;
    public String createdAt;

    public GameDTO(ge.okoridze.battleshipgame.model.Game game) {
        this.id = game.getId();
        this.ships = game.getShips();
        this.gridSize = game.getGridSize();
        this.won = game.getHits() >= 0;
        this.hits = 50 - game.getHits();
        this.user = new UserDTO(game.getUser());
        this.createdAt = game.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
