package sky.pro.pet_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sky.pro.pet_bot.controller.UserController;
import sky.pro.pet_bot.dao.UserRepository;
import sky.pro.pet_bot.model.User;
import sky.pro.pet_bot.service.impl.UserServiceInterfaceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserServiceInterfaceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;

    private User user1;
    private JSONObject jsonObject;
    private List<User> users;

    @BeforeEach
    void setUpData() throws JSONException {
        user1 = new User();
        user1.setChatId(1L);
        user1.setMessageId(1);
        user1.setName("TestName");
        user1.setLocation("Kiev");

        jsonObject = new JSONObject();
        jsonObject.put("chatId", user1.getChatId());
        jsonObject.put("messageId", user1.getMessageId());
        jsonObject.put("name", user1.getName());
        jsonObject.put("location", user1.getLocation());

        User user2 = new User();
        user2.setChatId(2L);
        user2.setMessageId(2);
        user2.setName("TestName2");
        user2.setLocation("Odessa");

        users = List.of(user1, user2);
    }

    @Test
    void createUser() throws Exception {

        when(userRepository.save(any(User.class))).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/add")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chatId").value(user1.getChatId()))
                .andExpect(jsonPath("$.messageId").value(user1.getMessageId()))
                .andExpect(jsonPath("$.name").value(user1.getName()))
                .andExpect(jsonPath("$.location").value(user1.getLocation()));
    }
}
