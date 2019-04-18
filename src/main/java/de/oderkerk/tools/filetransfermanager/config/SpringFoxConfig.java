/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import de.oderkerk.tools.filetransfermanager.security.JwtConfig;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Odin
 *
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	@Autowired
	private JwtConfig jwtConfig;

	@Bean
	public Docket apiDocket() {
		List<SecurityScheme> schemeList = new ArrayList<>();
		schemeList.add(new ApiKey("Bearer", "Authorization", "header"));
		List<SecurityContext> seccontext = new ArrayList<SecurityContext>();
		seccontext.add(securityContext());
		return new Docket(DocumentationType.SWAGGER_2).securitySchemes(schemeList).securityContexts(seccontext).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("FileTransferManager", "Rest api for Upload and Download", "1.0", "",
				new Contact("Marco Oderkerk", "https://marcooderkerk.de", ""),
				" Apache License Version 2.0, January 2004", " http://www.apache.org/licenses/", new ArrayList()

		);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("Bearer", authorizationScopes));
	}
}