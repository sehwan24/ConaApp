package Cona.App.service;

import Cona.App.domain.AppUser;
import Cona.App.repository.UserRepository;
import Cona.App.utility.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser create(String username, String email, String password) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public AppUser getUser(String username) {
        Optional<AppUser> appUser = this.userRepository.findByusername(username);
        if (appUser.isPresent()) {
            return appUser.get();
        } else {
            throw new DataNotFoundException("appuser not found");
        }
    }
}
