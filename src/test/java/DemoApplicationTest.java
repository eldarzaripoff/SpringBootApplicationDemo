import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {
    @Autowired
    TestRestTemplate restTemplate;
    private GenericContainer<?> devapp;
    private GenericContainer<?> prodapp;

    @BeforeEach
    void setUp() {
        devapp = new GenericContainer<>("devapp").withExposedPorts(8080);
        prodapp = new GenericContainer<>("prodapp").withExposedPorts(8080);
        devapp.start();
        prodapp.start();
    }

    @Test
    public void devContextLoads() {
        int devappPort = devapp.getMappedPort(8080);
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + devappPort + "/profile", String.class);
        System.out.println(forEntityDev.getBody());
        Assertions.assertEquals("Current profile is dev", forEntityDev.getBody());

    }

    @Test
    public void prodContextLoads() {
        int prodappPort = prodapp.getMappedPort(8081);
        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + prodappPort + "/profile", String.class);
        System.out.println(forEntityProd.getBody());
        Assertions.assertEquals("Current profile is production", forEntityProd.getBody());
    }

}
