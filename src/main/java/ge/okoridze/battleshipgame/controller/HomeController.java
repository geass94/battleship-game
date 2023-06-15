package ge.okoridze.battleshipgame.controller;

import ge.okoridze.battleshipgame.dto.GameDTO;
import ge.okoridze.battleshipgame.repositpry.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final GameRepository gameRepository;

    public HomeController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public String home(Model model)
    {
        model.addAttribute("games", gameRepository.findAll().stream().map(GameDTO::new));
        return "index";
    }
}
