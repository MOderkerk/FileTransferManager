/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Odin
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtConfig jwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
				// Add a filter to validate the tokens with every request
				.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
				// authorization requests config
				.authorizeRequests()
				// allow all who are accessing "auth" service
				// .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
				// must be an admin if trying to access admin area (authentication is also
				// required here)
				.antMatchers(HttpMethod.GET, "/").permitAll().antMatchers(HttpMethod.GET, "/csrf/**").permitAll()
				.antMatchers(HttpMethod.GET, "/sw**").permitAll().antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v2/api-docs/**").permitAll().antMatchers("/swagger-resources/**")
				.permitAll().antMatchers("/uploadFile").hasAuthority("fileupload")
				.antMatchers(HttpMethod.GET, "/downloadFile/**").hasAuthority("filedownload")
				// Any other request must be authenticated
				.anyRequest().authenticated()
		// .anyRequest().authenticated()
		;
	}

}
