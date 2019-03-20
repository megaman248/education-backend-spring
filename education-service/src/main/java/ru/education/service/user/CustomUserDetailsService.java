package ru.education.service.user;

import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.repo.country.CountryEntity;
import ru.education.repo.group.GroupEntity;
import ru.education.repo.locale.LocaleEntity;
import ru.education.repo.role.RoleEntity;
import ru.education.repo.role.RoleRepository;
import ru.education.repo.timezone.TimeZoneEntity;
import ru.education.repo.user.UserEntity;
import ru.education.repo.user.UserRepository;
import ru.education.repo.user.setting.UserSettingEntity;
import ru.education.repo.user.state.UserStateEntity;
import ru.education.rest.api.user.UserAuthorized;
import ru.education.rest.api.user.setting.UserSettingFull;
import ru.education.rest.api.user.state.UserStateFull;
import ru.education.service.OrikaMapper;
import ru.education.service.user.state.UserStateService;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static ru.education.rest.api.role.RoleCodeName.ROOT;
import static ru.education.rest.api.user.state.UserStateType.ACTIVE;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = (Logger) getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserStateService userStateService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Environment environment;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true, propagation = NOT_SUPPORTED)
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = null;
        UserStateFull userState = null;

        if (username.equals(environment.getProperty("spring.security.user.name"))) {
            userEntity = new UserEntity(username);
            String password = passwordEncoder.encode(environment.getProperty("spring.security.user.password"));
            userEntity.setPassword(password);

            UserSettingEntity userSettingEntity = new UserSettingEntity();
            userSettingEntity.setLocale(new LocaleEntity(environment.getProperty("language"), new CountryEntity(environment.getProperty("country"))));
            userSettingEntity.setTimeZone(new TimeZoneEntity(environment.getProperty("timezone.code")));
            userEntity.setSetting(userSettingEntity);
            userEntity.setEnabled(true);

            RoleEntity roleEntity = new RoleEntity(ROOT);

            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setRoles(new ArrayList<>());
            groupEntity.getRoles().add(roleEntity);

            userEntity.setGroups(new ArrayList<>());
            userEntity.getGroups().add(groupEntity);

            userState = new UserStateFull(ACTIVE);
        }

        if (userEntity == null) {
            userEntity = userRepository.findByLogin(username);
            if (userEntity == null) {
                throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
            }
            for (GroupEntity item : userEntity.getGroups()) {
                List<RoleEntity> roles = roleRepository.getByGroupId(item.getId());
                item.setRoles(roles);
            }
        }

        OrikaMapper mapper = new OrikaMapper();
        mapper.getFactory()
                .classMap(UserSettingEntity.class, UserSettingFull.class)
                .mapNulls(false)
                .exclude("user")
                .byDefault().register();
        UserAuthorized user = mapper.map(userEntity, UserAuthorized.class);
        if (userState == null) {
            userState = findLastState(user.getId());
        }
        if (userState != null) {
            userState.setUser(null);
        }
        user.setLastState(userState);

        LOG.info("Authenticated: " + user.getLogin());
        return new CustomUserDetails(user, environment);
    }

    private UserStateFull findLastState(Long userId) {
        UserStateEntity userState = userStateService.findLast(userId);
        if (userState != null) {
            OrikaMapper mapper = new OrikaMapper();
            mapper.getFactory()
                    .classMap(UserStateEntity.class, UserStateFull.class)
                    .mapNulls(false)
                    .field("code", "type")
                    .byDefault()
                    .register();
            return mapper.map(userState, UserStateFull.class);
        }
        return null;
    }
}
