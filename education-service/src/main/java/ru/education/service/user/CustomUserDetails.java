package ru.education.service.user;

import ch.qos.logback.classic.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.education.rest.api.user.UserAuthorized;
import ru.education.rest.api.user.state.UserStateFull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

public class CustomUserDetails implements UserDetails {

    private static final Logger LOG = (Logger) getLogger(CustomUserDetails.class);
    private UserAuthorized user;
    private transient Environment environment;
    private String password;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final Set<GrantedAuthority> authorities;

    CustomUserDetails(UserAuthorized user, Environment environment) {
        this.user = user;
        this.environment = environment;
        this.password = user.getPassword();
        this.user.setPassword(null);
        accountNonLocked = initAccountNonLocked();
        accountNonExpired = initAccountNonExpired();
        credentialsNonExpired = true;
        enabled = initEnabled();
        authorities = initAuthorities();
    }

    private boolean initAccountNonLocked() {
        boolean nonLockeed = false;
        String dateFormat = environment.getProperty("date.format.full");
        UserStateFull state = user.getLastState();
        if (state != null) {
            switch (state.getType()) {
                case ACTIVE:
                    nonLockeed = true;
                    break;
                case LOCKED_PERIOD:
                    if (state.getStateStart() != null && state.getStateEnd() != null) {
                        LOG.error(format(
                                "Пользователь '{0}' заблокирован на период с {1,date," + dateFormat + "} по {2,date," + dateFormat + "}.",
                                getUsername(), state.getStateStart(), state.getStateEnd()));
                    } else {
                        LOG.error("Пользователь заблокирован на период, который не указан.");
                    }
                    break;

                case LOCKED_FROM:
                    ZonedDateTime start = ZonedDateTime
                            .ofInstant(state.getStateStart().toInstant(), ZoneId.of(environment.getProperty("timezone.name")))
                            .truncatedTo(ChronoUnit.DAYS)
                            .withEarlierOffsetAtOverlap();
                    if (state.getStateStart() != null && start.isBefore(ZonedDateTime.now())) {
                        LOG.error(format("Пользователь '{0}' заблокирован с {1,date,\" + dateFormat + \"}.", getUsername(), state.getStateStart()));
                    } else {
                        LOG.error(format("Пользователь '%s' заблокирован, но не указано с какого времени.", getUsername()));
                    }
                    break;

                case LOCKED_IDLE:
                    LOG.error(format("Пользователь '%s' заблокирован, т.к. долго не было активности.", getUsername()));
                    break;

                default:
                    LOG.error(format("У пользователя '%s' неизвестный статус.", getUsername()));
                    break;
            }
        }
        return nonLockeed;
    }

    private boolean initAccountNonExpired() {
        if (user.getValidTill() == null) {
            return true;
        }
        ZonedDateTime validTill = ZonedDateTime
                .ofInstant(user.getValidTill().toInstant(), ZoneId.of(environment.getProperty("timezone.name")))
                .truncatedTo(ChronoUnit.DAYS)
                .withEarlierOffsetAtOverlap();
        if (validTill.isBefore(ZonedDateTime.now())) {
            LOG.error(format("Учётная запись пользователя '{0}' истекла {1,date," + environment.getProperty("date.format.full") + ".", getUsername(), user.getValidTill()));
            return false;
        }
        return true;
    }

    private boolean initEnabled() {
        if (user.getEnabled() != null) {
            return user.getEnabled();
        }
        return false;
    }

    private Set<GrantedAuthority> initAuthorities() {
        Set<GrantedAuthority> result = new HashSet<>();
        if (user.getGroups() != null && !user.getGroups().isEmpty()) {
            user.getGroups().forEach(group -> {
                if (group.getRoles() != null && !group.getRoles().isEmpty()) {
                    group.getRoles().forEach(role -> result.add(new SimpleGrantedAuthority(role.getCodeName())));
                }
            });
        }
        return result;
    }

    public UserAuthorized getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }
}
