package ru.education.repo.person;

public class PersonBuilder {

    private PersonEntity user;

    private PersonBuilder() {
    }

    public static PersonBuilder person() {
        PersonBuilder builder = new PersonBuilder();
        builder.user = new PersonEntity();
        return builder;
    }

    public PersonBuilder lastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public PersonBuilder firstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public PersonEntity build() {
        return user;
    }

    public PersonEntity buildIn(PersonRepository repository) {
        if (repository != null) {
            return repository.save(user);
        }
        return build();
    }
}
