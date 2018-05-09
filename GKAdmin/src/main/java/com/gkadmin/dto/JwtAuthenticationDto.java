/**
 * 
 */
package com.gkadmin.dto;

import java.io.Serializable;

/**
 * @author clovis
 *
 */
public class JwtAuthenticationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6545197670554597999L;
	private String username;
	private String password;
	
	public JwtAuthenticationDto() {
		super();
	}
	
	public JwtAuthenticationDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}