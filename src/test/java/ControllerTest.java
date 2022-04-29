import com.microservice.template.SpringBootApp;
import com.microservice.template.Thing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = SpringBootApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void getAllThings() {
        ResponseEntity<Thing[]> RE = template.getForEntity(getRootUrl() + "/get", Thing[].class);
    }

    @Test
    public void getThingById() {
        Thing thingOne = new Thing();
        thingOne.setId("7");
        thingOne.setValue1("thingOne");

        template.put(getRootUrl() + "/post", thingOne);

        int id = 0;
        ResponseEntity<Thing> RE = template.getForEntity(getRootUrl() + "/get/" + id, Thing.class);
    }

    @Test
    public void updateThing() {
        Thing thing = new Thing();
        thing.setId("42");
        thing.setValue1("updatedThing");

        int id = 2;
        ResponseEntity<Thing> RE = template.postForEntity(getRootUrl() + "/put/" + id, thing, Thing.class);
    }

    @Test
    public void createThing() {
        Thing thingOne = new Thing();
        thingOne.setId("7");
        thingOne.setValue1("thingOne");

        template.put(getRootUrl() + "/post", thingOne);

        Thing thingTwo = new Thing();
        thingTwo.setId("2");
        thingTwo.setValue1("thingTwo");

        template.put(getRootUrl() + "/post", thingTwo);
    }

    @Test
    public void deleteThing() {
        int id = 2;
        template.delete(getRootUrl() + "/delete/" + id, Thing.class);
    }
}