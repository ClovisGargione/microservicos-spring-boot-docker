/**
 * 
 */
package com.gk.tenant;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gk.repository.UserRepository;
import com.gk.security.JwtTokenUtil;

/**
 * @author clovis
 *
 */
@Component
public class TenantInterceptor extends HandlerInterceptorAdapter{
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		  String authToken = request.getHeader(this.tokenHeader);
		  if(authToken == null || authToken.isEmpty()) {
			  return true;
		  }
		  final String token = authToken.substring(7);
	      String tenantId = jwtTokenUtil.getTenantFromToken(token);
	      boolean tenantSet = false;
	    if(tenantId != null && !tenantId.isEmpty()) {
	      TenantContext.setCurrentTenant(tenantId);
	      tenantSet = true;
	    } else {
	      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	      response.getWriter().write("{\"error\": \"No tenant supplied\"}");
	      response.getWriter().flush();
	    }
	    return tenantSet;
	  }

	  @Override
	  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
	    TenantContext.clear();
	  }

}
