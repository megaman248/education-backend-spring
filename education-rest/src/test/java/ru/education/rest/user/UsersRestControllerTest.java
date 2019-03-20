package ru.education.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import ru.education.repo.RepoConfig;
import ru.education.rest.AbstractRestControllerTest;
import ru.education.rest.RestTestConfig;
import ru.education.rest.api.person.PersonFull;
import ru.education.rest.api.user.UserFull;
import ru.education.service.ServiceConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServletContext.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RestTestConfig.class, ServiceConfig.class, RepoConfig.class})
public class UsersRestControllerTest extends AbstractRestControllerTest {

    private static final String CONTEXT_PATH = "/users";

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac)
                .addFilters(springSecurityFilterChain, slf4jMdcFilter)
                .build();
    }

    @Test
    public void getUsers() throws Exception {
        PersonFull newPerson = PersonFull.builder()
                .lastName("Иванов")
                .firstName("Иван")
                .build();
        String originalLogin = "ivanov" + System.currentTimeMillis();
        UserFull newUser = UserFull.builder()
                .person(newPerson)
                .login(originalLogin)
                .password(encodedBcryptPassword("changeit"))
                .enabled(true)
                .build();
        ObjectMapper mapper = new ObjectMapper();

        MvcResult mvcCreateResult = mockMvc.perform(
                post(CONTEXT_PATH)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        int createdId = (int) resourceIdFromLocationHeader(mvcCreateResult);

        mockMvc.perform(
                get("/users")
                        .session(session)
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,desc")
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .accept(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isPartialContent())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.size", is(20)))
                .andExpect(jsonPath("$.content[0].id", is(createdId)))
                .andExpect(jsonPath("$.content[0].login", is(newUser.getLogin())));
    }

    @Test
    public void createAndUpdateUser() throws Exception {
        PersonFull newPerson = PersonFull.builder()
                .lastName("Иванов")
                .firstName("Иван")
                .build();
        String originalLogin = "ivanov" + System.currentTimeMillis();
        UserFull newUser = UserFull.builder()
                .person(newPerson)
                .login(originalLogin)
                .password(encodedBcryptPassword("changeit"))
                .enabled(true)
                .build();
        ObjectMapper mapper = new ObjectMapper();

        MvcResult mvcCreateResult = mockMvc.perform(
                post(CONTEXT_PATH)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        long createdId = resourceIdFromLocationHeader(mvcCreateResult);
        newUser.setId(createdId);

        MvcResult mvcReadResult = mockMvc.perform(
                get(CONTEXT_PATH + "/" + createdId)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .accept(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(notNullValue()))
                .andReturn();

        String createdContent = mvcReadResult.getResponse().getContentAsString();
        UserFull createdUser = mapper.readValue(createdContent, UserFull.class);
        createdUser.setPassword(newUser.getPassword());
        newPerson.setId(createdUser.getPerson().getId());
        newPerson.setVersion(createdUser.getPerson().getVersion());

        assertThat(newUser.getLogin()).isEqualTo(createdUser.getLogin());
        assertThat(newUser.getPerson()).isEqualToComparingFieldByFieldRecursively(createdUser.getPerson());

        createdUser.setLogin("ivanov" + System.currentTimeMillis());

        MvcResult mvcUpdateResult = mockMvc.perform(
                put(CONTEXT_PATH + "/" + createdId)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createdUser)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(notNullValue()))
                .andReturn();

        long updatedId = resourceIdFromLocationHeader(mvcUpdateResult);

        MvcResult mvcSecondReadResult = mockMvc.perform(
                get(CONTEXT_PATH + "/" + updatedId)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .accept(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(notNullValue()))
                .andReturn();

        String updatedContent = mvcSecondReadResult.getResponse().getContentAsString();
        UserFull updatedUser = mapper.readValue(updatedContent, UserFull.class);

        assertThat(originalLogin).isNotEqualTo(updatedUser.getLogin());
        assertThat(createdUser.getPerson()).isEqualToComparingFieldByFieldRecursively(updatedUser.getPerson());
    }

    @Test
    public void createAndDeleteUser() throws Exception {
        PersonFull newPerson = PersonFull.builder()
                .lastName("Иванов")
                .firstName("Иван")
                .build();
        UserFull newUser = UserFull.builder()
                .person(newPerson)
                .login("ivanov" + System.currentTimeMillis())
                .password(encodedBcryptPassword("changeit"))
                .enabled(true)
                .build();
        ObjectMapper mapper = new ObjectMapper();

        MvcResult mvcCreateResult = mockMvc.perform(
                post(CONTEXT_PATH)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        long createdId = resourceIdFromLocationHeader(mvcCreateResult);

        mockMvc.perform(
                delete(CONTEXT_PATH + "/" + createdId)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(isEmptyOrNullString()))
                .andReturn();

        mockMvc.perform(
                get(CONTEXT_PATH + "/" + createdId)
                        .session(session)
                        .with(httpBasicAuthorization(getRootLogin(), getRootPassword()))
                        .with(csrf())
                        .with(headerOriginLocalhost())
                        .accept(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }
}
