package ru.education.service.user.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.repo.user.state.UserStateEntity;
import ru.education.repo.user.state.UserStateRepository;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Service
public class UserStateServiceImpl implements UserStateService {

    private final UserStateRepository userStateRepository;

    @Autowired
    public UserStateServiceImpl(UserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }

    @Override
    @Transactional(readOnly = true, propagation = NOT_SUPPORTED)
    public UserStateEntity findLast(long userId) {
        UserStateEntity userState = null;
        Page<UserStateEntity> userStatePage = userStateRepository.findByUser(userId, PageRequest.of(0, 1));
        if (!userStatePage.getContent().isEmpty()) {
            userState = userStatePage.getContent().get(0);
        }
        return userState;
    }
}
