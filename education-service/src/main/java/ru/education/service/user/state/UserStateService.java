package ru.education.service.user.state;

import ru.education.repo.user.state.UserStateEntity;

public interface UserStateService {

    UserStateEntity findLast(long userId);
}
