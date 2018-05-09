/**
 * 
 */
package com.gk.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * @author clovis
 *
 */

@Component
public class TenantIdentifierResolver  implements CurrentTenantIdentifierResolver {
	
	private static final String DEFAULT_TENANT_ID = "public";

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = TenantContext.getCurrentTenant();
	    if (tenantId != null) {
	      return tenantId;
	    }
	    return DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
