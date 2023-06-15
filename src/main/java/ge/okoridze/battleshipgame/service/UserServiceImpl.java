package ge.okoridze.battleshipgame.service;

import ge.okoridze.battleshipgame.dto.UpdatePasswordRequest;
import ge.okoridze.battleshipgame.dto.UserDTO;
import ge.okoridze.battleshipgame.model.Role;
import ge.okoridze.battleshipgame.model.Roles;
import ge.okoridze.battleshipgame.model.User;
import ge.okoridze.battleshipgame.repositpry.RoleRepository;
import ge.okoridze.battleshipgame.repositpry.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements ge.okoridze.battleshipgame.service.UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        Role role = roleRepository.findByName(Roles.ROLE_USER.toString());

        if (role == null)
            role = roleRepository.save(new Role(Roles.ROLE_USER.toString()));

        User user = new User(userDto.getName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updatePassword(UpdatePasswordRequest request, String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        return user;
    }
}
