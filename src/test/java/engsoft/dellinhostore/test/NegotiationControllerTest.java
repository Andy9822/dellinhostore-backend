package engsoft.dellinhostore.test;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import engsoft.dellinhostore.controller.AdvertController;
import engsoft.dellinhostore.controller.ClientController;
import engsoft.dellinhostore.controller.GameController;
import engsoft.dellinhostore.controller.GenreController;
import engsoft.dellinhostore.controller.NegotiationController;
import engsoft.dellinhostore.controller.PlatformController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("NegotiationControllerTest")
public class NegotiationControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreController gnrController = new GenreController();
    @Autowired
    private GameController gController = new GameController();
    @Autowired
    private ClientController cController = new ClientController();
    @Autowired
    private PlatformController pController = new PlatformController();
    @Autowired
    private AdvertController adController = new AdvertController();
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(gnrController);
    	assertNotNull(gController);
    	assertNotNull(cController);
    	assertNotNull(pController);
    }
    
    @Test
    public void insertTest() throws Exception {
    	long createdGenreId = gnrController.insertManually("Genre NegotiationController Test");
    	long createdGameId = gController.insertManually("Game NegotiationController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform NegotiationController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("Daenerys Johnson Test","894784","02/05/1996","NegotiationController@test.com","testpassword", "1723");
    	long createdAvertId = adController.insertManually(createdGameId, createdClientId, createdPlatformId, "Description NegotiationController Test");
    	this.mockMvc.perform(post("/negotiation")
        		.param("advert_id", String.valueOf(createdAvertId))
        		.param("offerer_id", String.valueOf(createdClientId))
        		.param("offer", String.valueOf("Offer NegotiationController Test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.status").value("WAITING_ANSWER"))
                .andExpect(jsonPath("$.message.advert.id").value(createdAvertId));
    }

    @Test
    public void getAllTest() throws Exception {
    	long createdGenreId = gnrController.insertManually("Genre NegotiationController Test");
    	long createdGameId = gController.insertManually("Game NegotiationController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform NegotiationController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("Daenerys Johnson Test","894784","02/05/1996","NegotiationController@test.com","testpassword"," 9123081208");
    	long createdAvertId = adController.insertManually(createdGameId, createdClientId, createdPlatformId, "Description NegotiationController Test");
    	this.mockMvc.perform(post("/negotiation")
        		.param("advert_id", String.valueOf(createdAvertId))
        		.param("offerer_id", String.valueOf(createdClientId))
        		.param("offer", String.valueOf("Offer NegotiationController Test")))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/negotiation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].status").isString())
                .andDo(MockMvcResultHandlers.print());
    }
    
    /*
    @Test
    public void insertWithAdditionalParametersTest() throws Exception {
    	long createdGenreId = gnrController.insertManually("Genre AdvertController Test");
    	long createdGameId = gController.insertManually("Game AdvertController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform AdvertController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("John Wick Test","1565458","02/05/1996","AdvertController@test.com","testpassword");	
    	this.mockMvc.perform(post("/advert")
        		.param("game_id", String.valueOf(createdGameId))
        		.param("advertiser_id", String.valueOf(createdClientId))
        		.param("platform_id", String.valueOf(createdPlatformId))
        		.param("foo", "foo")
        		.param("description", "Description AdvertController Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.description").value("Description AdvertController Test"))
                .andExpect(jsonPath("$.message.status").value("OPEN"));
    }
    
    @Test
    public void insertWithEmptyDescriptionTest() throws Exception {
        long createdGenreId = gnrController.insertManually("Genre AdvertController Test");
    	long createdGameId = gController.insertManually("Game AdvertController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform AdvertController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("John Wick Test","1565458","02/05/1996","AdvertController@test.com","testpassword");	
    	this.mockMvc.perform(post("/advert")
        		.param("game_id", String.valueOf(createdGameId))
        		.param("advertiser_id", String.valueOf(createdClientId))
        		.param("platform_id", String.valueOf(createdPlatformId))
        		.param("description", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid parameters"));
    }
    
    
    @Test
    public void insertWithEmptyIdTest() throws Exception {
        long createdGenreId = gnrController.insertManually("Genre AdvertController Test");
    	long createdGameId = gController.insertManually("Game AdvertController Test", 18, createdGenreId);
    	pController.insertManually("Platform AdvertController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("John Wick Test","1565458","02/05/1996","AdvertController@test.com","testpassword");	
    	this.mockMvc.perform(post("/advert")
        		.param("game_id", String.valueOf(createdGameId))
        		.param("advertiser_id", String.valueOf(createdClientId))
        		.param("platform_id", "")
        		.param("description", "Description AdvertController Test"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void insertWithMissingParameterTest() throws Exception {
        long createdGenreId = gnrController.insertManually("Genre AdvertController Test");
    	long createdGameId = gController.insertManually("Game AdvertController Test", 18, createdGenreId);
    	pController.insertManually("Platform AdvertController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("John Wick Test","1565458","02/05/1996","AdvertController@test.com","testpassword");	
    	this.mockMvc.perform(post("/advert")
        		.param("game_id", String.valueOf(createdGameId))
        		.param("advertiser_id", String.valueOf(createdClientId))
        		.param("description", "Description AdvertController Test"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getOpenTest() throws Exception {
    	long createdGenreId = gnrController.insertManually("Genre AdvertController Test");
    	long createdGameId = gController.insertManually("Game AdvertController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform AdvertController Test","Company AdvertController Test");
    	long createdClientId = cController.insertManually("John Wick Test","1565458","02/05/1996","AdvertController@test.com","testpassword");	
    	this.mockMvc.perform(post("/advert")
        		.param("game_id", String.valueOf(createdGameId))
        		.param("advertiser_id", String.valueOf(createdClientId))
        		.param("platform_id", String.valueOf(createdPlatformId))
        		.param("description", "Description AdvertController Test"));
        this.mockMvc.perform(get("/advert"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].status").value("OPEN"))
                .andExpect(jsonPath("$.message[0].description").isString());
    }
    */
    @AfterEach
    public void deleteTestedEntities() {
    	NegotiationController.deleteTestedNegotiation("Offer NegotiationController Test");
    	AdvertController.deleteTestedAdvert("Description NegotiationController Test");
    	GameController.deleteTestedGame("Game NegotiationController Test");
        GenreController.deleteTestedGenre("Genre NegotiationController Test");
        PlatformController.deleteTestedPlatform("Platform NegotiationController Test");
        ClientController.deleteTestedClient("NegotiationController@test.com");
    }
}

