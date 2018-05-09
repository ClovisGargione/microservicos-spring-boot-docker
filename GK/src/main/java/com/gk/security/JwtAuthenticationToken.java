/**
 * 
 */
package com.gk.security;

import java.io.Serializable;

/**
 * @author clovis
 *
 */
public class JwtAuthenticationToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3364020377301948635L;
	
	private final String token;
	
	public JwtAuthenticationToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
}
