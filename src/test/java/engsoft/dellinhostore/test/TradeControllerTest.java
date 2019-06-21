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
import engsoft.dellinhostore.controller.TradeController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TradeControllerTest")
public class TradeControllerTest {
	
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
    @Autowired
    private NegotiationController nController = new NegotiationController();
    
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
    	long createdGenreId = gnrController.insertManually("Genre TradeController Test");
    	long createdGameId = gController.insertManually("Game TradeController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform TradeController Test","Company TradeController Test");
    	long createdAdvertiserId = cController.insertManually("Daenerys Johnson Test","894784","02/05/1996","TradeController@test.com","testpassword");
    	long createdOffererId = cController.insertManually("John Johnson Test","51482","02/05/1996","TradeController2@test.com","testpassword");
    	long createdAvertId = adController.insertManually(createdGameId, createdAdvertiserId, createdPlatformId, "Description TradeController Test");
    	long createdNegotiationId = nController.insertManually(createdAvertId, createdOffererId, "Offer TradeController Test");
    	this.mockMvc.perform(post("/trade")
        		.param("negotiation_id", String.valueOf(createdNegotiationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message.advertiser.id").value(createdAdvertiserId))
                .andExpect(jsonPath("$.message.offerer.id").value(createdOffererId))
                .andExpect(jsonPath("$.message.tradedGame.id").value(createdGameId))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllTest() throws Exception {
    	long createdGenreId = gnrController.insertManually("Genre TradeController Test");
    	long createdGameId = gController.insertManually("Game TradeController Test", 18, createdGenreId);
    	long createdPlatformId = pController.insertManually("Platform TradeController Test","Company TradeController Test");
    	long createdAdvertiserId = cController.insertManually("Daenerys Johnson Test","894784","02/05/1996","TradeController@test.com","testpassword");
    	long createdOffererId = cController.insertManually("John Johnson Test","51482","02/05/1996","TradeController2@test.com","testpassword");
    	long createdAvertId = adController.insertManually(createdGameId, createdAdvertiserId, createdPlatformId, "Description TradeController Test");
    	long createdNegotiationId = nController.insertManually(createdAvertId, createdOffererId, "Offer TradeController Test");
    	this.mockMvc.perform(post("/trade")
        		.param("negotiation_id", String.valueOf(createdNegotiationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(MockMvcResultHandlers.print());
        this.mockMvc.perform(get("/trade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message[0].advertiser.id").isNumber())
                .andExpect(jsonPath("$.message[0].offerer.id").isNumber())
                .andExpect(jsonPath("$.message[0].tradedGame.id").isNumber())
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
    	TradeController.deleteTestedTrade("Offer TradeController Test");
    	NegotiationController.deleteTestedNegotiation("Offer TradeController Test");
    	AdvertController.deleteTestedAdvert("Description TradeController Test");
    	GameController.deleteTestedGame("Game TradeController Test");
        GenreController.deleteTestedGenre("Genre TradeController Test");
        PlatformController.deleteTestedPlatform("Platform TradeController Test");
        ClientController.deleteTestedClient("TradeController@test.com");
        ClientController.deleteTestedClient("TradeController2@test.com");
    }
}

