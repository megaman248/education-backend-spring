package ru.education.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.repo.user.UserEntity;
import ru.education.repo.user.UserRepository;
import ru.education.repo.user.setting.UserSettingEntity;
import ru.education.repo.user.state.UserStateEntity;
import ru.education.rest.api.user.UserFull;
import ru.education.rest.api.user.setting.UserSettingFull;
import ru.education.rest.api.user.state.UserStateFull;
import ru.education.service.OrikaMapper;
import ru.education.service.user.state.UserStateService;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;
import static ru.education.rest.api.role.RoleCodeName.*;

@Service
public class UserServiceImpl implements UserService {

    private UserStateService userStateService;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserStateService userStateService) {
        this.userRepository = userRepository;
        this.userStateService = userStateService;
    }

    @Override
    @Secured({ROOT, USER_CREATE})
    @Transactional(propagation = REQUIRED)
    public long create(UserFull value) {
        UserEntity entity = new OrikaMapper().map(value, UserEntity.class);
        UserEntity user = userRepository.save(entity);
        return user.getId();
    }

    @Override
    @Secured({ROOT, USER_READ})
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Optional<UserFull> read(long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        UserFull user = null;
        if (userEntity.isPresent()) {
            OrikaMapper mapper = new OrikaMapper();
            mapper.getFactory().classMap(UserEntity.class, UserFull.class)
                    .exclude("password")
                    .exclude("groups")
                    .byDefault().register();
            mapper.getFactory().classMap(UserSettingEntity.class, UserSettingFull.class)
                    .exclude("user")
                    .byDefault().register();
            user = mapper.map(userEntity.get(), UserFull.class);

            UserStateEntity userState = userStateService.findLast(user.getId());
            if (userState != null) {
                mapper.getFactory().classMap(UserStateEntity.class, UserStateFull.class)
                        .field("code", "type")
                        .exclude("user")
                        .byDefault()
                        .register();
                user.setLastState(mapper.map(userState, UserStateFull.class));
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    @Secured({ROOT, USER_UPDATE})
    @Transactional(propagation = REQUIRED)
    public long update(UserFull value) {
        UserEntity entity = new OrikaMapper().map(value, UserEntity.class);
        UserEntity user = userRepository.save(entity);
        return user.getId();
    }

    @Override
    @Secured({ROOT, USER_DELETE})
    @Transactional(propagation = REQUIRED)
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Secured({ROOT, USER_READ_LIST})
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Page<UserFull> findUsers(Pageable pageable) {
        Page<UserEntity> all = userRepository.findAll(pageable);
        OrikaMapper mapper = new OrikaMapper();
        mapper.getFactory()
                .classMap(UserEntity.class, UserFull.class)
                .exclude("password")
                .exclude("groups")
                .byDefault().register();
        mapper.getFactory().classMap(UserSettingEntity.class, UserSettingFull.class)
                .mapNulls(false)
                .exclude("user")
                .byDefault().register();
        return mapper.mapAsPage(all, UserFull.class, pageable);
    }
}
