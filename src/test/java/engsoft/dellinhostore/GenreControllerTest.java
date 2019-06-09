package engsoft.dellinhostore;

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
import engsoft.dellinhostore.controller.GenreController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class GenreControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void insertTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", "Genre AdvertController Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("Genre AdvertController Test"));
    }
      
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", "Genre AdvertController Test")
        		.param("foo", "foo"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("Genre AdvertController Test"));
    }
    
    @Test
    public void insertWithEmptyParameterTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", ""))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Invalid id"));
    }
    
    @Test
    public void insertWithMissingParameterTest() throws Exception {
        this.mockMvc.perform(post("/genre"))
        		.andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getAllTest() throws Exception {
        this.mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].name").isString());
    }
    
    @After 
    public void deleteTestedEntities() {
        GenreController.deleteTestedGenre("Genre AdvertController Test");
    }
   
}

