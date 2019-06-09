package engsoft.dellinhostore.test;

import static org.junit.Assert.assertNotNull;
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
import engsoft.dellinhostore.controller.PlatformController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class PlatformControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlatformController controller;
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(controller);
    }
    
    @Test
    public void insertTest() throws Exception {
        this.mockMvc.perform(post("/platform")
        		.param("name", "PlatformController Test")
        		.param("company", "Company Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("PlatformController Test"))
                .andExpect(jsonPath("$.message.company").value("Company Test"));
    }
    
    
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
        this.mockMvc.perform(post("/platform")
        		.param("name", "PlatformController")
        		.param("company", "Company Test")
        		.param("foo", "foo"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("PlatformController"))
                .andExpect(jsonPath("$.message.company").value("Company Test"));
    }
    
    @Test
    public void insertWithMissingParametersTest() throws Exception {
        this.mockMvc.perform(post("/platform"))
        		.andExpect(status().is4xxClientError());
    }
    
    @Test
    public void insertWithEmptyNameTest() throws Exception {
        this.mockMvc.perform(post("/platform")
        		.param("name", "")
        		.param("company", "Test"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Forbidden empty strings"));
    }
    
    @Test
    public void insertWithEmptyParameterTest() throws Exception {
        this.mockMvc.perform(post("/platform")
        		.param("name", "PlatformController Test")
        		.param("company", ""))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Forbidden empty strings"));
    }
    
    @Test
    public void getAllTest() throws Exception {
    	this.mockMvc.perform(post("/platform")
        		.param("name", "PlatformController Test")
        		.param("company", "Company Test"));
        this.mockMvc.perform(get("/platform"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].name").isString())
                .andExpect(jsonPath("$.message[0].company").isString());
    }
    
    @After 
    public void deleteTestedEntities() {
    	PlatformController.deleteTestedPlatform("Platform AdvertController Test");
    }

}

