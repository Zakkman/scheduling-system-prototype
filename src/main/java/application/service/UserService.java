package application.service;

import application.models.users.User;
import application.repository.users.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        String plainPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(plainPassword);

        user.setPasswordHash(hashedPassword);
        user.setPassword(null);

        return repo.save(user);
    }
}
