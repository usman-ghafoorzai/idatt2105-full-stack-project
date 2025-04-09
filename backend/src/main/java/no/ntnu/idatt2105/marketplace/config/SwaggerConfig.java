package no.ntnu.idatt2105.marketplace.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI defineOpenApi() {
		Server server = new Server();
		server.setUrl("http://localhost:8888");
		server.setDescription("Development");

		Info information = new Info()
				.title("Marketplace System API")
				.description("This API exposes endpoints for the Marketplace System");

		return new OpenAPI()
				.info(information)
				.servers(List.of(server))
				.tags(List.of(
						new Tag().name("items").description("Operations about items"),
						new Tag().name("users").description("Operations about users"),
						new Tag().name("categories").description("Operations about categories")));
	}
}