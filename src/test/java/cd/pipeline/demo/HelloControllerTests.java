package cd.pipeline.demo;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

// Spring Boot 4: @SpringBootTest stellt keinen TestRestTemplate mehr bereit,
// daher MockMvc (Starter spring-boot-starter-webmvc-test)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Greetings from")));
    }

    @Test
    public void testCreateLogs() throws Exception {
        mvc.perform(get("/something"))
                .andExpect(status().isOk())
                .andExpect(content().string("All Ok"));
    }
}
