import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.SpringBootDemoApplication;
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootDemoApplication.class)
public class DemoApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Container
    private final GenericContainer<?> devapp = new GenericContainer<>("devapp:latest").withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodapp = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);

//    @BeforeEach
//    void setUp() {
//        devapp.start();
//        prodapp.start();
//    }

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
