/**
 * 
 */
package com.gkadmin.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gkadmin.entity.Tenant;
import com.gkadmin.entity.Users;

/**
 * @author clovis
 *
 */
@Repository
@Transactional
public class TenantCustomRepository {
	
	private static final String DEFAULT = "public";
	@PersistenceContext
	private EntityManager em;
		
	@Autowired
	private TenantRepository tenantRepository;
	
	public void saveTenant(String tenantId) {
		Tenant tenant = new Tenant();
		tenant.setTenantId(tenantId);
		em.createNativeQuery("set schema '" + DEFAULT +"'").executeUpdate();
		tenantRepository.save(tenant);
	}

}
