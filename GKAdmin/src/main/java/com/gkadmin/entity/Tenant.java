/**
 * 
 */
package com.gkadmin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.gkadmin.entity.interfaces.TenantSupport;

/**
 * @author clovis
 *
 */
@Entity
public class Tenant implements TenantSupport{
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_seq")
    @SequenceGenerator(name = "tenant_seq", sequenceName = "tenant_seq", allocationSize = 1)
	private Long id;
		
	@NotNull
	private String tenantId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		
	}
	
	

}
