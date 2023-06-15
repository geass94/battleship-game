package ge.okoridze.battleshipgame.dto;

public record ShotDTO(Integer hitsLeft, Boolean hit, ShipDTO ship) {
}
