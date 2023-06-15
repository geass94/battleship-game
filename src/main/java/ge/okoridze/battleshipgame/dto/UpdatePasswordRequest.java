package ge.okoridze.battleshipgame.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePasswordRequest(
        @NotEmpty
        String password,
        @NotEmpty
        String confirmPassword
) {
}
