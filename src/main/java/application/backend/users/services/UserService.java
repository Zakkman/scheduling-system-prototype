package application.backend.users.services;

import application.backend.users.models.User;
import application.backend.users.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<User> findByUser(User user) {
        return repo.findById(user.getId());
    }

    public boolean isEmailTaken(String email) {
        return repo.existsByEmail(email);
    }
}
