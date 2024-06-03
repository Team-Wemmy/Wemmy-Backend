package wemmy.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Test
    void signUpByAdmin() {

        userService.signUpByAdmin("teamWemmy@gmail.com", "project2024");
    }
}