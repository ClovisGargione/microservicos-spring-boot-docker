/**
 * 
 */
package com.gk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gk.filter.JwtAuthenticationTokenFilter;
import com.google.common.collect.ImmutableList;

/**
 * @author clovis
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	 @Autowired
	    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder
	                .userDetailsService(this.userDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
	        return new JwtAuthenticationTokenFilter();
	    }
	    
	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(ImmutableList.of("*"));
	        configuration.setAllowedMethods(ImmutableList.of("HEAD",
	                "GET", "POST", "PUT", "DELETE", "PATCH"));
	        // setAllowCredentials(true) is important, otherwise:
	        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);
	        // setAllowedHeaders is important! Without it, OPTIONS preflight request
	        // will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	    
	    @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity
	                // não precisamos da CSRF porque o nosso token é invulnerável
	                .csrf().disable()
                	.cors().and()
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

	                // não cria sessão
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

	                .authorizeRequests()
	                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

	                //  permitir pedidos de recursos anônimos
	                .antMatchers(
	                        HttpMethod.GET,
	                        "/",
	                        "/*.html",
	                        "/favicon.ico",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js"
	                ).permitAll()
	                .antMatchers("/rest",
	                		"/auth", 
	                		"/actuator",
	               			 "/self",
	               			 "/env",
	               			 "/features",
	               			 "/beans",
	               			 "/auditevents",
	               			 "/configprops",
	               			 "/health",
	               			 "/loggers",
	               			 "/mappings",
	               			 "/service-registry",
	               			 "/archaius",
	               			 "/refresh",
	               			 "/info",
	               			 "/dump",
	               			 "/autoconfig",
	               			 "/metrics",
	               			 "/heapdump",
	               			 "/trace").permitAll()
	                .anyRequest().authenticated();

	        // Filtro de segurança baseado em JWT personalizado
	        httpSecurity
	                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

	        // desativar o cache de páginas
	        httpSecurity
	                .headers()
	                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
	                .cacheControl();
	    }

}
