/**
 * 
 */
package com.gk.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author clovis
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8303776027753465023L;

	/* (non-Javadoc)
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {
		
        // Isso é invocado quando o usuário tenta acessar um recurso REST seguro sem fornecer credenciais
        // Devemos apenas enviar uma resposta não autorizada 401 porque não há "página de login" para redirecionar para
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");

	}

}
