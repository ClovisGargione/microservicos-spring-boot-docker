/**
 * 
 */
package com.gk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gk.entity.Users;
import com.gk.factory.JwtUserFactory;
import com.gk.repository.UserCustomRepository;

/**
 * @author clovis
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private UserCustomRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Usuário '%s' não encontrado.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }

}
