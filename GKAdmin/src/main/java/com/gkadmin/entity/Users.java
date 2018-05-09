/**
 * 
 */
package com.gkadmin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gkadmin.entity.interfaces.TenantSupport;




/**
 * @author clovis
 *
 */
@Entity
public class Users implements TenantSupport{
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long id;
	
	@NotNull
	@Column(unique=true, length = 150)
    @Size(min = 4, max = 50)
	private String username;
	
	@NotNull
    @Size(min = 4, max = 100)
    private String password;
	
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;
    
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;
    
    @NotNull
    private Boolean enabled;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName="ID")},
            inverseJoinColumns= {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName="ID")})
    private List<Authority> authorities;

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

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the lastPasswordResetDate
	 */
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	/**
	 * @param lastPasswordResetDate the lastPasswordResetDate to set
	 */
	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	/**
	 * @return the authorities
	 */
	public List<Authority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getTenantId() {
		return this.tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		
	}
}
