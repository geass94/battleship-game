package ge.okoridze.battleshipgame.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grid_size")
    private Integer gridSize = 10;

    @OneToMany( mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ship> ships;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hits")
    private Integer hits = 50;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGridSize() {
        return gridSize;
    }

    public void setGridSize(Integer gridSize) {
        this.gridSize = gridSize;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}



