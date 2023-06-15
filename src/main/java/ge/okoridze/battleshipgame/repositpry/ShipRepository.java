package ge.okoridze.battleshipgame.repositpry;

import ge.okoridze.battleshipgame.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
