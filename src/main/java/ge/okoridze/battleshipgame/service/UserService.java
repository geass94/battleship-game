package ge.okoridze.battleshipgame.service;

import ge.okoridze.battleshipgame.dto.UpdatePasswordRequest;
import ge.okoridze.battleshipgame.dto.UserDTO;
import ge.okoridze.battleshipgame.model.User;

public interface UserService {

    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    User updatePassword(UpdatePasswordRequest request, String email);

}
