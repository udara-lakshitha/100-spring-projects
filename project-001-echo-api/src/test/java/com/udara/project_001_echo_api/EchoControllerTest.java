package com.udara.project_001_echo_api;

import com.udara.project_001_echo_api.controller.EchoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EchoController.class)
public class EchoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testEchoWithDefaultMessage() throws Exception{
        mockMvc.perform(get("/echo"))
                .andExpect(status().isOk())
                .andExpect(content().string("Echo: Hello"));
    }

    @Test
    public void testEchoEndpointWithProvidedMessage() throws Exception {
        String testMessage = "TestMessage123";
        mockMvc.perform(get("/echo").param("message", testMessage))
                .andExpect(status().isOk())
                .andExpect(content().string("Echo: " + testMessage));
    }

    @Test
    public void testHomeEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Echo Chamber API is running"));
    }
}
