package de.oderkerk.tools.filetransfermanager.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

//	@Value("${security.jwt.uri:/auth/**}")
//	private String Uri;

	@Value("${security.jwt.header:Authorization}")
	private String header;

	@Value("${security.jwt.prefix:Bearer }")
	private String prefix;
//
//	@Value("${security.jwt.expiration:#{24*60*60}}")
//	private int expiration;

	@Value("${security.jwt.secret:JwtSecretKey}")
	private String secret;

//	/**
//	 * @return the uri
//	 */
//	public String getUri() {
//		return Uri;
//	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

//	/**
//	 * @return the expiration
//	 */
//	public int getExpiration() {
//		return expiration;
//	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	public JwtConfig() {
		super();
	}

}
