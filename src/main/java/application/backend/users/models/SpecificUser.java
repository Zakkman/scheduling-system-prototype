package application.backend.users.models;

public interface SpecificUser<T extends SpecificUser<T>> {
    T getInstance();
}
