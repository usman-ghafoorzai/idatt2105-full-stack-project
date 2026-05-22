package no.ntnu.idatt2105.marketplace.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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

		// Define the security scheme for a JWT Bearer token
		final String securitySchemeName = "bearer-key";
		Components components = new Components();
		components.addSecuritySchemes(securitySchemeName,
				new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT"));

		// Apply the defined security scheme globally
		SecurityRequirement securityRequirement = new SecurityRequirement();
		securityRequirement.addList(securitySchemeName);

		return new OpenAPI()
				.components(components)
				.addSecurityItem(securityRequirement)
				.info(information)
				.servers(List.of(server))
				.tags(List.of(
						new Tag().name("Items").description("Operations about items"),
						new Tag().name("Users").description("Operations about users"),
						new Tag().name("Categories").description("Operations about categories"),
						new Tag().name("Bookmarks").description("Operations related to managing bookmarks"),
						new Tag().name("Reservations").description("Operations related to managing reservations"),
						new Tag().name("Authentication").description("Operations related to authentication"),
						new Tag().name("Item Images").description("Operations related to managing item images"),
						new Tag().name("User Images").description("Operations related to managing user images")));
	}
}