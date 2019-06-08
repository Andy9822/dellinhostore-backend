package engsoft.dellinhostore.test;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class GenreControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreController cat; 
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(cat);
    }
    
    @Test
    public void insertTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", "MMORPG-test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("MMORPG-test"));
    }
      
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", "MMOTPS-test")
        		.param("foo", "foo"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("MMOTPS-test"));
    }
    
    @Test
    public void insertWithEmptyNameTest() throws Exception {
        this.mockMvc.perform(post("/genre")
        		.param("name", ""))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Invalid id"));
    }
    
    @Test
    public void getAllTest() throws Exception {
        this.mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray());
    }
   
}

