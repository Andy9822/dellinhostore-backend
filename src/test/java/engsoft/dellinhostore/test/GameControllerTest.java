package engsoft.dellinhostore.test;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import engsoft.dellinhostore.controller.GameController;
import engsoft.dellinhostore.controller.GenreController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("GameControllerTest")
public class GameControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreController controller = new GenreController();
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(controller);
    }
    
    @Test
    public void insertTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
        this.mockMvc.perform(post("/game")
        		.param("name", "GameController Test")
        		.param("minimumAge", "18")
        		.param("genre_id", String.valueOf(createdGenreId)))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("GameController Test"))
                .andExpect(jsonPath("$.message.minimumAge").value(18))
                .andExpect(jsonPath("$.message.genre.name").value("Genre GameController Test"));
    }
    
    
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
        this.mockMvc.perform(post("/game")
        		.param("name", "GameController Test")
        		.param("minimumAge", "20")
        		.param("genre_id", String.valueOf(createdGenreId))
        		.param("foo", "foo"))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.name").value("GameController Test"))
                .andExpect(jsonPath("$.message.minimumAge").value(20))
                .andExpect(jsonPath("$.message.genre.name").value("Genre GameController Test"));
    }
    
    @Test
    public void insertWithEmptyParameterTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
        this.mockMvc.perform(post("/game")
        		.param("name", "")
        		.param("minimumAge", "30")
        		.param("genre_id", String.valueOf(createdGenreId)))
        		.andDo(print()).andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Invalid id, invalid minimumAge value or empty string"));
    }
    
    @Test
    public void insertWithMissingParameterTest() throws Exception {
        this.mockMvc.perform(post("/game")
        		.param("name", "GameController Test")
        		.param("minimumAge", "30"))
        		.andDo(print()).andExpect(status().is4xxClientError());
    }
    
    @Test
    public void insertWithNegativeAgeTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
        this.mockMvc.perform(post("/game")
        		.param("name", "GameController Test")
        		.param("minimumAge", "-2")
        		.param("genre_id", String.valueOf(createdGenreId)))
        		.andDo(print()).andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Invalid id, invalid minimumAge value or empty string"));
    }
    
    @Test
    public void insertWithZeroAgeTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
        this.mockMvc.perform(post("/game")
        		.param("name", "GameController Test")
        		.param("minimumAge", "0")
        		.param("genre_id", String.valueOf(createdGenreId)))
        		.andDo(print()).andExpect(status().isOk())
        		.andExpect(jsonPath("$.success").value(false))
        		.andExpect(jsonPath("$.message").value("Invalid id, invalid minimumAge value or empty string"));
    }

    
    @Test
    public void getAllTest() throws Exception {
    	long createdGenreId = controller.insertManually("Genre GameController Test");
    	 this.mockMvc.perform(post("/game")
         		.param("name", "GameController Test")
         		.param("minimumAge", "18")
         		.param("genre_id", String.valueOf(createdGenreId)))
                 .andExpect(status().isOk());
        this.mockMvc.perform(get("/game"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].name").isString())
                .andExpect(jsonPath("$.message[0].minimumAge").isNumber())
                .andExpect(jsonPath("$.message[0].genre.name").isString());
    }
    
    @AfterEach
    public void deleteTestedEntities() {
        GameController.deleteTestedGame("GameController Test");
        GenreController.deleteTestedGenre("Genre GameController Test");
    }
}

