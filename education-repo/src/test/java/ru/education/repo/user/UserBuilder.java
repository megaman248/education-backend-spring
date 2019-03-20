package ru.education.repo.user;

import ru.education.repo.person.PersonEntity;

public class UserBuilder {

    private UserEntity user;

    private UserBuilder() {
    }

    public static UserBuilder user() {
        UserBuilder builder = new UserBuilder();
        builder.user = new UserEntity();
        return builder;
    }

    public UserBuilder login(String login) {
        user.setLogin(login);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder person(PersonEntity person) {
        user.setPerson(person);
        return this;
    }

    public UserBuilder enabled(boolean enabled) {
        user.setEnabled(enabled);
        return this;
    }

    public UserEntity build() {
        return user;
    }

    public UserEntity buildIn(UserRepository repository) {
        if (repository != null) {
            return repository.save(user);
        }
        return build();
    }
}
