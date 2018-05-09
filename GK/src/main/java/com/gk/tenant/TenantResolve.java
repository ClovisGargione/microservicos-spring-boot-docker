/**
 * 
 */
package com.gk.tenant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

/**
 * @author clovis - Classe não utilizada
 *
 */

public abstract class TenantResolve {
	
	private static final String MASTER = "master";

	@PersistenceContext
	private EntityManager em;
	
	public void applyTenant() {
		if(!isMaster()) {
			Session session = em.unwrap(Session.class);
			session.enableFilter("tenantFilter").setParameter("tenantId", TenantContext.getCurrentTenant());	
		}
	}
	
	private static boolean isMaster() {
		return TenantContext.getCurrentTenant().equals(MASTER);
	}
}
