/**
 * 
 */
package com.gk.rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gk.dto.JwtAuthenticationDto;
import com.gk.entity.Users;
import com.gk.repository.UserCustomRepository;
import com.gk.security.JwtAuthenticationToken;
import com.gk.security.JwtTokenUtil;
import com.gk.security.JwtUser;
import com.gk.tenant.TenantContext;
import com.gk.tenant.TenantResolver;

/**
 * @author clovis
 *
 */
@RestController
public class AuthenticationRest {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
   
    TenantResolver tenantResolver = new TenantResolver();
    
    @PostMapping(path="${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationDto authenticationRequest, Device device) throws AuthenticationException {
    	
    	try {
            tenantResolver.setUsername(authenticationRequest.getUsername());
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<String> utrFuture = es.submit(tenantResolver);
            String tenantId = utrFuture.get();
            TenantContext.setCurrentTenant(tenantId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Execute a segurança
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Recarregar senha pós-segurança para que possamos gerar token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        // retorna o token
        return ResponseEntity.ok(new JwtAuthenticationToken(token));
    }

  
    @GetMapping(path="${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationToken(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
