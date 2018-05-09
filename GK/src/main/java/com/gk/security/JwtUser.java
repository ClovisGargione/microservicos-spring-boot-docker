/**
 * 
 */
package com.gk.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author clovis
 *
 */
public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Long id;
	private final String username;
	private final String firstname;
	private final String lastname;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;
	private final Date lastPasswordResetDate;
	private final String tenantId;

	public JwtUser(Long id, 
			String username, 
			String firstname, 
			String lastname, 
			String password,
			Collection<? extends GrantedAuthority> authorities, 
			boolean enabled, 
			Date lastPasswordResetDate,
			String tenantId) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.tenantId = tenantId;

	}

	@JsonIgnore
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired
	 * ()
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked(
	 * )
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#
	 * isCredentialsNonExpired()
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@JsonIgnore
	@Override
	public String getPassword() {
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return this.lastPasswordResetDate;
	}
	
	@JsonIgnore
	public String getTenantId() {
		return tenantId;
	}

}
