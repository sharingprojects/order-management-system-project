package br.com.icastell.restapioms.config.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final ResponseMessage m201 = customMessage1();
	private final ResponseMessage m204put = simpleMessage(204, "Successful update");
	private final ResponseMessage m204del = simpleMessage(204, "Successful deleted");
	private final ResponseMessage m403 = simpleMessage(403, "Not Authorized");
	private final ResponseMessage m404 = simpleMessage(404, "Not found");
	private final ResponseMessage m422 = simpleMessage(422, "Validation error");
	private final ResponseMessage m500 = simpleMessage(500, "Unexpected error");
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m403, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500))				
				
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.icastell.restapioms.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
		        .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
		        .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
		        .securityContexts(Arrays.asList(securityContext()));
	}
	
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope = new springfox.documentation.service.AuthorizationScope("ADMIN", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(
	        new SecurityReference("Token Access", authorizationScopes));
	}
	
	private List<ResponseMessage> responseMessageForGET() {
		return new ArrayList<ResponseMessage>() {

			private static final long serialVersionUID = 1L;

			{
				add(new ResponseMessageBuilder()
						.code(500)
						.message("500 message")
						.responseModel(new ModelRef("Error")).build());
				add(new ResponseMessageBuilder().code(403).message("Forbidden!").build());
			}
		};
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.ant("/api/**"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API Spring Boot course",
				"This API is used in Professor Nélio Alves' Spring Boot course", 
				"Versão 1.0",
				"https://www.udemy.com/terms",
				new Contact("Andreza T. Cascais", "https://github.com/sharingprojects/order-management-system-project", "infocastell@gmail.com"),
				"Allowed use for students", 
				"https://www.udemy.com/terms",
				Collections.emptyList() 
		);
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}
	
	private ResponseMessage customMessage1() {
		Map<String, Header> map = new HashMap<>();
		map.put("location", new Header("location", "URI do novo recurso", new ModelRef("string")));
		return new ResponseMessageBuilder()
		.code(201)
		.message("Recurso criado")
		.headersWithDescription(map)
		.build();
	}

}
