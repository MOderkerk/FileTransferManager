/**
 * 
 */
package de.oderkerk.tools.filetransfermanager.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author Odin
 *
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger logger = LogManager.getLogger(JwtTokenAuthenticationFilter.class);
	private final JwtConfig jwtConfig;

	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
		if (logger.isDebugEnabled())
			logger.debug("Jwt Config: [}", jwtConfig);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(jwtConfig.getHeader());

		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			chain.doFilter(request, response);
			return;
		}

		String token = header.replace(jwtConfig.getPrefix(), "");
		if (logger.isDebugEnabled())
			logger.debug("token found : {}", token.substring(1, 10));
		try {
			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token)
					.getBody();
			String username = claims.getSubject();
			if (logger.isDebugEnabled())
				logger.debug("username found : {}", username);

			if (username != null) {
				String[] tempAuthorities = ((String) claims.get("authorities")).split(",");
				List<String> authorities = new ArrayList<String>();
				for (String tauth : tempAuthorities) {
					authorities.add(tauth);
				}
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
				if (logger.isDebugEnabled())
					logger.debug("UsernamePasswordAuthenticationToken found : {}", auth.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			logger.error("Exception occured Type={} , Message = {}", e.getClass().toGenericString(), e.getMessage());
			SecurityContextHolder.clearContext();
		}

		chain.doFilter(request, response);
	}

}
