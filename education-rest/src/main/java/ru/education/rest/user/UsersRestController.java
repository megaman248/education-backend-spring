package ru.education.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.education.rest.AbstractRestController;
import ru.education.rest.api.user.UserAuthorized;
import ru.education.rest.api.user.UserFull;
import ru.education.service.user.CurrentUser;
import ru.education.service.user.CustomUserDetails;
import ru.education.service.user.UserService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersRestController extends AbstractRestController {

    private final UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth")
    public UserAuthorized auth(@CurrentUser CustomUserDetails user) {
        return user.getUser();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserFull value) {
        long id = userService.create(value);
        httpServletResponse.addHeader("Location", buildHeaderLocation("/users/" + id));
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{id:\\d+}")
    public UserFull read(@PathVariable long id) throws NoHandlerFoundException {
        Optional<UserFull> found = userService.read(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new NoHandlerFoundException(httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), new HttpHeaders());
    }

    @RequestMapping(method = {PUT, PATCH}, value = "/{id:\\d+}")
    public ResponseEntity update(@PathVariable long id, @RequestBody UserFull value) {
        long newId = userService.update(value);
        httpServletResponse.addHeader("Location", buildHeaderLocation("/users/" + newId));
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity delete(@PathVariable long id) {
        userService.delete(id);
        return noContent().build();
    }

    @GetMapping
    @ResponseStatus(PARTIAL_CONTENT)
    public Page<UserFull> list(Pageable pageable) {
        return userService.findUsers(pageable);
    }
}
