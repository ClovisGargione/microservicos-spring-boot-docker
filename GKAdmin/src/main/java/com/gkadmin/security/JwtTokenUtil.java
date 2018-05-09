/**
 * 
 */
package com.gkadmin.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

/**
 * @author clovis
 *
 */
@Component
public class JwtTokenUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 144698746483876546L;
	
	static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "iat";
    static final String CLAIM_KEY_TENANT = "tenantId";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

	private static final String AUTHORITIES = "authorities";
    
    @SuppressFBWarnings(value = "SE_BAD_FIELD", justification = "It's okay here")
    private Clock clock = DefaultClock.INSTANCE;
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Obtém o usuário gravado no token
     * @param token - token de autenticação
     * @return - usuário em formato String
     */
    public String getUsernameFromToken(String token) {
    	return getClaimFromToken(token, Claims::getSubject);
    }
    
    /**
     * Obtém data de criação do token
     * @param token
     * @return
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * Obtém a data de expiração do token
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Obtém o dispositivo autenticado
     * @param token
     * @return
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }
    
    public String getTenantFromToken(String token) {
    	Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); 
    	return (String) claims.get(CLAIM_KEY_TENANT);
    }
    
    /*
     * Retorna atributos do token
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    	
    }
    
    /**
     * Retorna todos os atributos do token
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    /**
     * Verifica se o token está expirado
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }
    
    /**
     * Verifica se foi autenticado antes da última alteração de senha
     * @param created
     * @param lastPasswordReset
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    
    /**
     * Define dispositivo que está autenticando
     * @param device
     * @return
     */
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }
    
    /**
     * Verifica se deve ignorar expiração do token caso seja algum dispositivo móvel
     * @param token
     * @return
     */
    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }
    
    /**
     * Gera token de autenticação
     * @param userDetails
     * @param device
     * @return
     */
    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_TENANT, ((JwtUser) userDetails).getTenantId());
        claims.put(AUTHORITIES, userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername(), generateAudience(device));
    }
    
    /**
     * Executa a geração do token
     * @param claims
     * @param subject
     * @param audience
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject, String audience) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        System.out.println("doGenerateToken " + createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    /**
     * Verifica se token pode ser renovado
     * @param token
     * @param lastPasswordReset
     * @return
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * Renova token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Verifica se token é valido
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
              username.equals(user.getUsername())
                    && !isTokenExpired(token)
                    && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }
    
    /**
     * Calcula expiração do token
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
    	return new Date(createdDate.getTime() + expiration * 1000);
    	
    }

}
