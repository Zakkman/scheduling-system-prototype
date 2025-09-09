package application.backend.users.services;

import application.backend.users.models.SpecificUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class SpecificUserServiceImpl<T extends SpecificUser<?>, R extends JpaRepository<T, Long>>
    implements SpecificUserService<T> {

    protected final R repo;

    public SpecificUserServiceImpl(R repository) {
        this.repo = repository;
    }

    @Override
    public List<T> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<T> findById(long id) {
        return repo.findById(id);
    }

    @Override
    public T save(T user) {
        return repo.save(user);
    }

    @Override
    public void delete(T user) {
        repo.delete(user);
    }
}
