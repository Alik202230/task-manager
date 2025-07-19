package am.itspace.task.manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {


  private static final String API_URL = System.getenv("API_URL");


  @Bean
  public OpenAPI openAPI() {
    Server server = new Server();
    server.setUrl(API_URL);
    server.setDescription("OpenAPI Documentation");

    Contact contact = new Contact();
    contact.setName("Task Manager API");
    contact.setName("something@gmai.com");

    Info info = new Info()
        .title("Task Manager API")
        .version("1.0")
        .contact(contact);

    return new OpenAPI()
        .info(info)
        .servers(List.of(server));
  }

}
