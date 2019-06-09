package engsoft.dellinhostore.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import engsoft.dellinhostore.application.Application;
import engsoft.dellinhostore.controller.ClientController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class ClientControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void insertTest() throws Exception {
        this.mockMvc.perform(post("/client")
        		.param("name", "John Wick Test")
        		.param("cpf", "805080")
        		.param("dateOfBirth", "02/05/1996")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("John Wick Test"))
                .andExpect(jsonPath("$.message.cpf").value("805080"))
                .andExpect(jsonPath("$.message.dateOfBirth").exists())
                .andExpect(jsonPath("$.message.email").value("ClientControllerTest@email.com"));
    }
    
    
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
        this.mockMvc.perform(post("/client")
        		.param("name", "Elton John Test")
        		.param("cpf", "89415689")
        		.param("dateOfBirth", "12/08/1976")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword")
        		.param("foo", "foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("Elton John Test"))
                .andExpect(jsonPath("$.message.cpf").value("89415689"))
                .andExpect(jsonPath("$.message.dateOfBirth").exists())
                .andExpect(jsonPath("$.message.email").value("ClientControllerTest@email.com"));
    }
    
    @Test
    public void insertWithEmptyParameterTest() throws Exception {
    	this.mockMvc.perform(post("/client")
        		.param("name", "Daenerys")
        		.param("cpf", "")
        		.param("dateOfBirth", "30/12/1956")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Empty cpf"));
    }
    
    @Test
    public void insertWithMissingParameterTest() throws Exception {
    	this.mockMvc.perform(post("/client")
        		.param("name", "Daenerys")
        		.param("cpf", "84651482")
        		.param("dateOfBirth", "30/12/1956")
        		.param("password", "testpassword"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void insertWithExistantEmail() throws Exception {
    	this.mockMvc.perform(post("/client")
        		.param("name", "John Wick Test")
        		.param("cpf", "805080")
        		.param("dateOfBirth", "02/05/1996")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword"));
    	
        this.mockMvc.perform(post("/client")
        		.param("name", "MonaLisa")
        		.param("cpf", "4698/76")
        		.param("dateOfBirth", "24/02/1986")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email already in use"));
    }
  
    @Test
    public void getAllTest() throws Exception {
    	this.mockMvc.perform(post("/client")
        		.param("name", "John Wick Test")
        		.param("cpf", "806580")
        		.param("dateOfBirth", "02/05/1996")
        		.param("email", "ClientControllerTest@email.com")
        		.param("password", "testpassword"));
    	
        this.mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].name").isString())
                .andExpect(jsonPath("$.message[0].cpf").isString())
                .andExpect(jsonPath("$.message[0].dateOfBirth").exists())
                .andExpect(jsonPath("$.message[0].email").isString());
    }
    
    @After
    public void deleteTestingClient() {
        ClientController.deleteTestedClient("ClientControllerTest@email.com");
    }

}

