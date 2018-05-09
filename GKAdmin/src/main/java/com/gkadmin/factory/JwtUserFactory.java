/**
 * 
 */
package com.gkadmin.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gkadmin.entity.Authority;
import com.gkadmin.entity.Users;
import com.gkadmin.security.JwtUser;

/**
 * @author clovis
 *
 */
public final class JwtUserFactory {

	public static JwtUser create(Users user) {
		return new JwtUser(user.getId(), 
						user.getUsername(), 
						user.getFirstname(), 
						user.getLastname(),
						user.getPassword(),
						mapToGrantedAuthorities(user.getAuthorities()),
						user.getEnabled(),
						user.getLastPasswordResetDate(),
						user.getTenantId());
		
	}
	
	 private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
	        return authorities.stream()
	                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
	                .collect(Collectors.toList());
	    }
}
