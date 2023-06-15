package ge.okoridze.battleshipgame.controller;

import ge.okoridze.battleshipgame.dto.UpdatePasswordRequest;
import ge.okoridze.battleshipgame.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String registrationForm(Model model, Authentication authentication) {
        model.addAttribute("user", userService.findUserByEmail(authentication.getName()));
        model.addAttribute("request", new UpdatePasswordRequest("", ""));
        return "user";
    }

    @PostMapping("")
    public String updateProfile(
            @Valid @ModelAttribute("request") UpdatePasswordRequest request,
            Model model,
            Authentication authentication,
            BindingResult result
            ) {

        if (!request.password().equals(request.confirmPassword())) {
            result.rejectValue("confirmPassword", null,
                    "Passwords do not match !!!");
            result.rejectValue("password", null,
                    "Passwords do not match !!!");
        }

        model.addAttribute("request", request);

        if (result.hasErrors()) {
            model.addAttribute("request", request);
            return "user";
        }

        userService.updatePassword(request, authentication.getName());
        return "redirect:/user?success";
    }
}