package application.backend.users.services;

import application.backend.users.models.SpecificUser;

import java.util.List;
import java.util.Optional;

public interface SpecificUserService<T extends SpecificUser<?>> {

    List<T> findAll();

    Optional<T> findById(long id);

    T save(T user);

    void delete(T user);

    List<T> search(String nameFilter, String schoolDataFilter);
}
